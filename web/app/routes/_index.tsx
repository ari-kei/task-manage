import type { MetaFunction } from "@remix-run/node";
import { Form } from "@remix-run/react";

export const meta: MetaFunction = () => {
  return [
    { title: "[個人開発]タスク管理アプリケーション" },
    { name: "description", content: "タスク管理アプリケーション" },
  ];
};

export default function Index() {
  return (
    <header className="absolute inset-x-0 top-0 z-50">
      <nav className="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div className="font-sans p-4 flex lg:flex-1">
          <h1 className="text-3xl">タスク管理</h1>
        </div>
        <div className="hidden lg:flex lg:flex-1 lg:justify-end">
          <Form action="/board/new">
            <button type="submit" className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">ボード新規作成</button>
          </Form>
        </div>
      </nav>
    </header>
  );
}
