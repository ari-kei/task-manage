import { json, LoaderFunctionArgs } from "@remix-run/node"
import { useFetcher, useLoaderData, useNavigate, useParams } from "@remix-run/react";
import { FormEventHandler, useEffect, useState } from "react";
import invariant from 'tiny-invariant';
import { DragDropContext, DraggableLocation, Droppable, DropResult } from "@hello-pangea/dnd";
import { fetchBoard, fetchTasks } from "~/app";

import Tasklist from "~/components/Tasklist";
import Modal from "~/components/Modal";
import TaskForm from "~/components/TaskForm";
import { requireAuth } from "~/middleware/auth";

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
  dueDate: Array<string>,
  order: number,
}

export const loader = async ({
  request,
  params
}: LoaderFunctionArgs) => {
  const [accessToken, authRedirect] = await requireAuth(request);
  if (accessToken === "") return authRedirect;

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
  const data = useLoaderData<typeof loader>();
  const boardDetail = data?.boardDetail ?? {
    board: { id: '', name: '' },
    taskStatus: []
  };
  const tasks = data?.tasks ?? [];
  const [modalType, setModalType] = useState<"create" | "update" | null>(null);
  const [newTaskStatus, setNewTaskStatus] = useState<string | null>(null);
  const [updateTask, setUpdateTask] = useState<task | null>(null);
  const colLength = boardDetail.taskStatus.length;
  const fetcher = useFetcher();

  const handleNewTaskCard: FormEventHandler<HTMLFormElement> = async (e) => {
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
    closeCreateModal();
  };

  const handleUpdateTask: FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    // TODO タスクの更新フォームを作成する
    const target = e.target as typeof e.target & {
      taskName: { value: string },
    };
    const formData = new FormData();
    formData.append("taskName", target.taskName.value);
  };

  const openCreateModal = (statusId: string) => {
    setModalType("create");
    setNewTaskStatus(statusId);
  };

  const openUpdateModal = (taskId: string) => {
    setModalType("update");
    const foundTask = tasks.find(task => task.taskId === taskId);
    setUpdateTask(foundTask || null);
  };

  const closeCreateModal = () => {
    setModalType(null);
    setNewTaskStatus(null);
  };

  const navigate = useNavigate();
  const closeUpdateModal = () => {
    setModalType(null);
    navigate(`/board/${boardDetail.board.id}`);
  };

  const { _, taskId } = useParams();
  useEffect(() => {
    if (taskId) {
      openUpdateModal(taskId);
    }
  }, [taskId]);

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
                      <Tasklist taskStatus={taskStatus} tasks={tasks} openCreateModal={openCreateModal}></Tasklist>
                      {provided.placeholder}
                    </div>
                  )}
                </Droppable>
              ))
            }
          </DragDropContext>
        </div>
        <Modal isOpen={modalType === "create"} onClose={closeCreateModal} title="新規タスク作成">
          <fetcher.Form method={'post'} onSubmit={handleNewTaskCard}>
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
                <button data-modal-hide="default-modal" type="button" onClick={closeCreateModal}
                  className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100">キャンセル
                </button>
              </div>
            </div>
          </fetcher.Form>
        </Modal>
        <Modal isOpen={modalType === "update"} onClose={closeUpdateModal} title="タスク更新">
          <fetcher.Form method={'post'} onSubmit={handleUpdateTask}>
            <TaskForm task={updateTask ?? null} onClose={closeUpdateModal}></TaskForm>
          </fetcher.Form>
        </Modal>
      </div >
    </>
  )
}