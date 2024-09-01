import { Form } from "@remix-run/react"

export default function Index(props: { accessToken: string }) {
  return (
    <div>
      <header className="sticky inset-x-0 top-0 z-50">
        <nav className="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
          <div className="font-sans p-4 flex lg:flex-1">
            <a href="/board" className="text-3xl">タスク管理</a>
          </div>
          <div className="mt-10 flex items-center justify-center gap-x-6">
            {props.accessToken ?
              <Form action="/logout">
                <button type="submit" className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">ログアウト</button>
              </Form>
              :
              <Form action="/login">
                <button type="submit" className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">ログイン</button>
              </Form>
            }
            <Form action="/board/new">
              <button type="submit" className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">ボード新規作成</button>
            </Form>
          </div>
        </nav>
      </header>
    </div>
  )
}