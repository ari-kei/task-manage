import { defer, LoaderFunctionArgs } from "@remix-run/node";
import { Await, Outlet, useLoaderData } from "@remix-run/react"
import { Suspense } from "react";
import { fetchBoards } from "~/app";
import { requireAuth } from "~/middleware/auth";

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  const [accessToken, authRedirect] = await requireAuth(request);
  if (accessToken === "") return authRedirect;

  const boardPromise = fetchBoards(accessToken).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`)
    }
    return res.json();
  }).catch((error) => {
    console.log(error);
    return;
  }
  );
  return defer({ boardsPromise: boardPromise });
}

export default function Index() {
  const data = useLoaderData<typeof loader>();
  const boardsPromise = data?.boardsPromise;
  return (
    <>
      <div className="grid grid-cols-4 gap-4">

        <Suspense fallback={
          <h5 className="mb-2 text-2xl text-center font-bold tracking-tight text-gray-900 dark:text-white">Loading...</h5>
        }>
          <Await resolve={boardsPromise}>
            {(res) => {
              return res.data.map((board: { id: string, name: string }) => {
                return (
                  <a key={board.id} href={"/board/" + board.id} className="block max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
                    <h5 className="mb-2 text-2xl text-center font-bold tracking-tight text-gray-900 dark:text-white">{board.name}</h5>
                  </a>
                )
              })
            }}
          </Await>
          <a href="/board/new" className="block max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700" >
            <h5 className="mb-2 text-4xl text-center font-bold tracking-tight text-gray-500 dark:text-white">+</h5>
          </a>
        </Suspense >
      </div >
      <Outlet />
    </>
  );
}