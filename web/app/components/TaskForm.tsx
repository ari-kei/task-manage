import { task } from "~/routes/board.$boardId";

interface TaskFormProps {
  task: task | null;
}

export default function TaskForm(props: TaskFormProps) {
  if (props.task === null) {
    return (
      <div>
        <h1>タスクが取得できませんでした。一度ページをリロードしてください。</h1>
      </div>
    )
  }

  // TODO タスクの編集フォームを作成する
  return (
    <div>
      <h1>TaskForm</h1>
    </div>
  )
}