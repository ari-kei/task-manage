import { redirect, type LoaderFunctionArgs, type MetaFunction } from "@remix-run/node";
import { requireAuth } from "~/middleware/auth";

export const loader = async ({
  request
}: LoaderFunctionArgs) => {
  const [accessToken, authRedirect] = await requireAuth(request);
  if (accessToken === "") return authRedirect;

  return redirect("/board");
}

export const meta: MetaFunction = () => {
  return [
    { title: "[個人開発]タスク管理アプリケーション" },
    { name: "description", content: "タスク管理アプリケーション" },
  ];
};

export default function Index() {
  return (<></>);
}
