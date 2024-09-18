export default function Index(props: { task: any }) {
  return (
    <div className="max-w-full pb-4" key={props.task.name + props.task.order}>
      <button type="button" onClick={() => console.log("TODO")} className="flex-col w-full bg-white rounded-lg shadow-lg justify-center">
        <p className="p-4">{props.task.name}</p>
      </button>
    </div>
  )
}