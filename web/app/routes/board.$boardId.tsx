import { json, LoaderFunctionArgs, redirect } from "@remix-run/node"
import { Outlet, useFetcher, useLoaderData } from "@remix-run/react";
import { FormEventHandler, useState } from "react";
import invariant from 'tiny-invariant';
import { DragDropContext, DraggableLocation, Droppable, DropResult } from "@hello-pangea/dnd";
import { fetchBoard, fetchTasks } from "~/app";

import { getSession } from "~/session"
import Tasklist from "~/components/Tasklist";
import Modal from "~/components/Modal";

type boardDetail = {
  board: {
    id: string,
    name: string,
  },
  taskStatus: [taskStatus]
}

export type taskStatus = {
  boardId: string,
  statusId: string,
  statusName: string,
  order: number,
}

export type task = {
  boardId: string,
  taskId: string,
  taskStatusId: string,
  name: string,
  description: string,
  dueDate: string,
  order: number,
}

export const loader = async ({
  request,
  params
}: LoaderFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  )
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return redirect("/login");
  }

  invariant(params.boardId, "");
  const boardId = params.boardId;

  // FIXME ボード取得+タスク取得で非効率になっている部分の改善
  const boardDetails: boardDetail = await fetchBoard(accessToken, boardId).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
    return res.json();
  }).catch(error => {
    console.log(error);
    return;
  });

  const tasks: task[] = await fetchTasks(accessToken, boardId).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
    return res.json();
  }).catch(error => {
    console.log(error);
    return;
  });

  return json({ boardDetail: boardDetails, tasks: tasks });
}

export default function Index() {
  const { boardDetail, tasks } = useLoaderData<typeof loader>();
  const [modalType, setModalType] = useState<"create" | "detail" | null>(null);
  const [newTaskStatus, setNewTaskStatus] = useState<string | null>(null);
  const colLength = boardDetail.taskStatus.length;
  const fetcher = useFetcher();

  const handleTaskSubmit: FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    const target = e.target as typeof e.target & {
      taskStatus: { value: string },
      taskName: { value: string },
    };
    const formData = new FormData();
    formData.append("status", target.taskStatus.value);
    formData.append("name", target.taskName.value);
    fetcher.submit(formData, {
      action: "taskCard",
      method: "POST",
    });
    closeModal();
  };

  const openCreateModal = (statusId: string) => {
    setModalType("create");
    setNewTaskStatus(statusId);
  };

  const openDetailModal = () => {
    setModalType("detail");
  };

  const closeModal = () => {
    setModalType(null);
    setNewTaskStatus(null);
  };

  const [t, updateTasks] = useState(tasks);
  const onDragEnd = (result: DropResult) => {
    const { destination, source, draggableId } = result;
    if (!destination) {
      return;
    }

    if (destination.droppableId === source.droppableId
      && destination.index === source.index) {
      return;
    }
    const items = reorder(
      t as task[],
      source,
      destination
    );

    updateTasks(items);
  };

  const reorder = (
    list: task[],
    source: DraggableLocation,
    destination: DraggableLocation<string>
  ): task[] => {
    const sourceStatusId: string = `${boardDetail.board.id}-${Number(source.droppableId) + 1}`;
    const destinationStatusId: string = `${boardDetail.board.id}-${Number(destination.droppableId) + 1}`;

    const updateOrder = (item: task, newOrder: number) => {
      item.order = newOrder;
    };

    if (sourceStatusId === destinationStatusId) {
      list.forEach((item: task) => {
        if (item.taskStatusId === sourceStatusId) {
          // 移動先の対象
          if (item.order === source.index + 1) {
            updateOrder(item, destination.index + 1);
          } else if (source.index < destination.index && item.order > source.index + 1 && item.order <= destination.index + 1) {
            updateOrder(item, item.order - 1);
          } else if (source.index > destination.index && item.order < source.index + 1 && item.order >= destination.index + 1) {
            updateOrder(item, item.order + 1);
          }
        }
      });
      return list;
    }

    list.forEach((item: task) => {
      if (item.taskStatusId === destinationStatusId && item.order >= destination.index + 1) {
        updateOrder(item, item.order + 1);
      }
    });

    list.forEach((item: task) => {
      if (item.taskStatusId === sourceStatusId) {
        if (item.order === source.index + 1) {
          item.taskStatusId = destinationStatusId;
          updateOrder(item, destination.index + 1);
        } else if (item.order > source.index + 1) {
          updateOrder(item, item.order - 1);
        }
      }
    });

    return list;
  };

  return (
    <>
      <div className="mx-auto">
        <h1 className="text-3xl">{boardDetail.board.name}</h1>
      </div>
      <div className="overflow-x-auto h-full flex flex-col">
        <div className={"mb-10 grid max-w-2xl grid-cols-" + colLength + " gap-x-8 gap-y-16 border-t border-gray-200 sm:mt-16 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-" + colLength}>
          <DragDropContext onDragEnd={onDragEnd}>
            {
              boardDetail.taskStatus.map((taskStatus: { boardId: string, statusId: string, statusName: string, order: number }, index: number) => (
                <Droppable droppableId={`${index}`} key={taskStatus.statusId}>
                  {provided => (
                    <div ref={provided.innerRef} {...provided.droppableProps}>
                      <Tasklist taskStatus={taskStatus} tasks={tasks} opneModal={openCreateModal}></Tasklist>
                      {provided.placeholder}
                    </div>
                  )}
                </Droppable>
              ))
            }
          </DragDropContext>
        </div>
        <Modal isOpen={modalType === "create"} onClose={closeModal} title="新規タスク作成">
          <fetcher.Form method={'post'} onSubmit={handleTaskSubmit}>
            <div className="p-4 md:p-5 space-y-4">
              <div>
                <input type="hidden" id="new-taskcard-status" name={'taskStatus'} value={newTaskStatus ?? ''} />
                <label htmlFor="taskName"
                  className="block mb-2 text-sm font-medium text-gray-900">タスク名</label>
                <input type="text" name={'taskName'}
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                  placeholder="タスク名" required />
              </div>
            </div>
            <div className={'flex justify-between items-center'}>
              <div className="flex items-center p-4 md:p-5 rounded-b">
                <button data-modal-hide="default-modal" type="submit"
                  className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">保存
                </button>
                <button data-modal-hide="default-modal" type="button" onClick={closeModal}
                  className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100">キャンセル
                </button>
              </div>
            </div>
          </fetcher.Form>
        </Modal>
      </div >
      <Outlet />
    </>
  )
}