import { ActionFunctionArgs } from "@remix-run/node";
import { Form, json, redirect, useActionData } from "@remix-run/react";
import { postBoard } from "~/app";

export const action = async ({
  request,
}: ActionFunctionArgs) => {
  const formData = await request.formData();
  const boardName = String(formData.get("name"));

  const errors: {
    name: string,
  } = { name: "" };
  if (boardName.length <= 0) {
    errors.name = "ボード名称は必須です";
  }
  if (boardName.length > 255) {
    errors.name = "ボード名は1字以上255字以下で入力してください";
  }
  if (errors.name.length > 0) {
    return json({ errors });
  }

  const res = await postBoard(boardName).then(res => {
    if (!res.ok) {
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
    return res.json();
  }).catch(error => {
    console.log(error);
    return;
  });
  return redirect("/board");
}

export default function Index() {
  const actionData = useActionData<typeof action>();
  return (
    <div>
      <h1 className="text-3xl">タスクボード新規作成</h1>
      <Form method="post">
        <p>

          <label htmlFor="name">ボード名称</label>
          <input type="text" name="name" />
          {actionData?.errors?.name ? (
            <em>{actionData?.errors.name}</em>
          ) : null}
        </p>
        <button type="submit">保存</button>

      </Form>
    </div>
  );
}