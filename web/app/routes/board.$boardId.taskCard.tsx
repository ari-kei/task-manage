import { ActionFunctionArgs, json, redirect } from "@remix-run/node";
import invariant from "tiny-invariant";
import { createTaskCard } from "~/app";
import { getSession } from "~/session";

export const action = async ({
  request,
  params
}: ActionFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  );
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return redirect("/login");
  }
  const formData = await request.formData();
  const taskStatus = String(formData.get("taskStatus"));
  const taskName = String(formData.get("taskName"));

  const errors: {
    taskName: string
  } = { taskName: "" };
  if (taskName.length <= 0) {
    errors.taskName = "タスク名は必須です"
  }
  if (taskName.length > 255) {
    errors.taskName = "タスク名は1文字以上255文字以内で入力してください"
  }
  if (errors.taskName.length > 0) {
    return json({ errors });
  }
  invariant(params.boardId, "");
  const boardId = params.boardId;

  const res = await createTaskCard(accessToken, boardId, taskName, taskStatus).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
  }
  );
  // TODO リターンするのは何が適切？
  return json({})
};

export default function Index() {
  return <></>;
}