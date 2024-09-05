import type { MetaFunction } from "@remix-run/node";

export const meta: MetaFunction = () => {
  return [
    { title: "[個人開発]タスク管理アプリケーション" },
    { name: "description", content: "タスク管理アプリケーション" },
  ];
};

export default function Index() {
  return (<></>);
}
