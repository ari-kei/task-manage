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
    <div className="font-sans p-4">
      <h1 className="text-3xl">タスク管理</h1>
      <Form action="/board/new">
        <button type="submit">ボード新規作成</button>
      </Form>
    </div>
  );
}
