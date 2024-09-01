import { json, LoaderFunctionArgs, redirect } from "@remix-run/node";
import { Outlet, useLoaderData } from "@remix-run/react"
import { fetchBoards } from "~/app";
import { getSession } from "~/session";

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  console.log("ここにきているはず1")
  const session = await getSession(
    request.headers.get("Cookie")
  )
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return redirect("/login");
  }
  const res = await fetchBoards(accessToken).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`)
    }
    return res.json();
  }).catch((error) => {
    console.log(error);
    return;
  }
  )
  const boards = res.data;

  return json({ boards });
}

export default function Index() {
  const data = useLoaderData<typeof loader>();
  console.log(data.boards)
  return (
    <>
      <div className="grid grid-cols-4 gap-4">
        {data?.boards?.map((board: { id: string, name: string }) => {
          return (
            <a href="#" className="block max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
              <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{board.name}</h5>
            </a>
          )
        })}
      </div >
      <Outlet />
    </>
  );
}