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
    </>
  )
}