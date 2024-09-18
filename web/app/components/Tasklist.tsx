import TaskCard from "./TaskCard";

export default function Index(props: {
  taskStatus: { boardId: string, statusId: string, statusName: string, order: number },
  tasks: any,
  openDialog: (statusId: string) => void,
}) {
  return (
    <>
      <div className="max-w-xl flex-col items-start justify-between p-3 bg-gray-100 rounded-lg shadow-lg">
        <p>{props.taskStatus.statusName}</p>
      </div>
      <div className="grow max-h-[75%] overflow-y-auto h-fit bg-gray-100 rounded-lg shadow-lg p-6" key={props.taskStatus.statusId}>
        {
          props.tasks.sort((a: { boardId: string, taskStatusId: string, name: string, description: string, dueDate: Date, order: number }, b: { boardId: string, taskStatusId: string, name: string, description: string, dueDate: Date, order: number }) => { return a.order - b.order }).map((task: { boardId: string, taskStatusId: string, name: string, description: string, dueDate: Date, order: number }) => {
            if (props.taskStatus.statusId !== task.taskStatusId) {
              return;
            }
            return (
              <TaskCard task={task} key={task.boardId + task.taskStatusId + task.order}></TaskCard>
            )
          })
        }
        <div className="max-w-full">
          <button type="button" onClick={() => props.openDialog(props.taskStatus.statusId)} className="flex-col w-full bg-white rounded-lg shadow-lg justify-center">
            <p className="p-4">+</p>
          </button>
        </div>
      </div>
    </>
  )
}