import TaskCard from "./TaskCard";
import { task, taskStatus } from "~/routes/board.$boardId";

export default function Index(props: {
  taskStatus: taskStatus,
  tasks: task[],
  opneModal: (statusId: string) => void,
}) {
  return (
    <>
      <div className="max-w-xl flex-col items-start justify-between p-3 bg-gray-100 rounded-lg shadow-lg">
        <p>{props.taskStatus.statusName}</p>
      </div>
      <div className="grow max-h-[75%] overflow-y-auto h-fit bg-gray-100 rounded-lg shadow-lg p-6" key={props.taskStatus.statusId}>
        {
          props.tasks
            .filter((task: task) => task.taskStatusId === props.taskStatus.statusId)
            .sort((a: task, b: task) => { return a.order - b.order })
            .map((task: task, index: number) => {
              return (
                <TaskCard task={task} index={index} key={task.boardId + task.taskStatusId + task.order}></TaskCard>
              )
            })
        }
        <div className="max-w-full">
          <button type="button" onClick={() => props.opneModal(props.taskStatus.statusId)} className="flex-col w-full bg-white rounded-lg shadow-lg justify-center">
            <p className="p-4">+</p>
          </button>
        </div>
      </div>
    </>
  )
}