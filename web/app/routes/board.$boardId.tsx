import { json, LoaderFunctionArgs, redirect } from "@remix-run/node"
import { useLoaderData } from "@remix-run/react";
import invariant from 'tiny-invariant';
import { fetchBoard, fetchBoards } from "~/app";

import { getSession } from "~/session"

export const loader = async ({
  request,
  params
}: LoaderFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  )
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return redirect("/login");
  }

  invariant(params.boardId, "");
  const boardId = params.boardId;

  // FIXME ボード取得+タスク取得で非効率になっている部分の改善
  const res = await fetchBoard(accessToken, boardId).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
    return res.json();
  }).catch(error => {
    console.log(error);
    return;
  });

  // TODO タスク一覧取得

  const board = res;

  return json({ board });
}

export default function Index() {
  const data = useLoaderData<typeof loader>();
  return (
    <>
      <h1>{data.board.name}</h1>
      {/* TODO タスクをステータスごとに画面表示 */}
      <div className="mx-auto mt-10 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 border-t border-gray-200 pt-10 sm:mt-16 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-4">
        <div className="flex max-w-xl flex-col items-start justify-between">a</div>
        <div className="flex max-w-xl flex-col items-start justify-between">b</div>
        <div className="flex max-w-xl flex-col items-start justify-between">c</div>
        <div className="flex max-w-xl flex-col items-start justify-between">d</div>
      </div>
    </>
  )
}