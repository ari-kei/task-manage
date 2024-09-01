import { LoaderFunctionArgs, redirect } from "@remix-run/node"
import { destroySession, getSession } from "~/session"

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  )
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return redirect("/login");
  }

  // TODO トークン破棄用のauthのエンドポイントを呼ぶ
  return redirect("/login", { headers: { "Set-Cookie": await destroySession(session) } });
}

export default function Index() {
  return (<></>);
}