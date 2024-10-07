import { task } from "~/routes/board.$boardId";

interface TaskFormProps {
  task: task | null;
  onClose: () => void;
}

export default function TaskForm(props: TaskFormProps) {
  if (props.task === null) {
    return (
      <div>
        <h1>タスクが取得できませんでした。一度ページをリロードしてください。</h1>
      </div>
    )
  }

  function padZero(num: string | number, length: number) {
    return String(num).padStart(length, '0');
  }

  const year: string = props.task.dueDate[0];
  const month: string = padZero(props.task.dueDate[1], 2);
  const day: string = padZero(props.task.dueDate[2], 2);
  const hour: string = padZero(props.task.dueDate[3], 2);
  const minute: string = padZero(props.task.dueDate[4], 2);


  // TODO タスクの編集フォームを作成する
  return (
    <div className="flex flex-col items-center">
      <div className="m-4 w-3/4">
        <label htmlFor="name" className="block text-sm font-medium text-gray-700">タスク名</label>
        <input
          type="text"
          name="name"
          defaultValue={props.task.name}
          className="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 p-2"
        />
      </div>
      <div className="mb-4 w-3/4">
        <label htmlFor="description" className="block text-sm font-medium text-gray-700">タスク説明</label>
        <textarea
          name="description"
          defaultValue={props.task.description}
          rows={4}
          className="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 p-2"
        />
      </div>
      <div className="mb-4 w-3/4">
        <label htmlFor="dueDate" className="block text-sm font-medium text-gray-700">締め切り日</label>
        <input
          type="datetime-local"
          name="dueDate"
          defaultValue={
            props.task.dueDate.length > 0
              ? new Date(`${year}-${month}-${day}T${hour}:${minute}`).toISOString().substring(0, 16)
              : ''
          }
          className="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 p-2"
        />
      </div>
      <div className="grid grid-cols-2 gap-4">
        <div className="m-10">
          <button
            type="submit"
            className="block w-full rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">保存</button>
        </div>
        <div className="m-10">
          <button
            type="submit"
            onClick={props.onClose}
            className="block w-full rounded-md bg-gray-500 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-gray-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-500">キャンセル</button>
        </div>
      </div>
    </div>
  )
}