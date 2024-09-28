import { Draggable } from "@hello-pangea/dnd";
import { task } from "~/routes/board.$boardId";

export default function Index(props: { task: task, index: number }) {
  return (
    <Draggable key={props.task.boardId + props.task.taskStatusId + props.task.order} draggableId={props.task.boardId + props.task.taskStatusId + props.task.order} index={props.index}>
      {(provided) => (
        <div className="max-w-full pb-4" key={props.task.name + props.task.order} ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}>
          <p onClick={() => console.log("TODO")} className="flex-col w-full bg-white rounded-lg shadow-lg justify-center p-4">{props.task.name}</p>
        </div>
      )}
    </Draggable>
  )
}