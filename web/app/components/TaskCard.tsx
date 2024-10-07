import { Draggable } from "@hello-pangea/dnd";
import { useNavigate } from "@remix-run/react";
import { task } from "~/routes/board.$boardId";

interface TaskCardProps {
  task: task;
  index: number;
}

export default function Index(props: TaskCardProps) {
  const navigate = useNavigate();
  return (
    <Draggable key={props.task.boardId + props.task.taskStatusId + props.task.order} draggableId={props.task.boardId + props.task.taskStatusId + props.task.order} index={props.index}>
      {(provided) => (
        <div className="max-w-full pb-4" key={props.task.name + props.task.order} ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}>
          <p onClick={() => navigate(`/board/${props.task.boardId}/task/${props.task.taskId}`)} className="flex-col w-full bg-white rounded-lg shadow-lg justify-center p-4">{props.task.name}</p>
        </div>
      )}
    </Draggable>
  )
}