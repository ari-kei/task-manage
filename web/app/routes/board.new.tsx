import { ActionFunctionArgs, LoaderFunctionArgs } from "@remix-run/node";
import { Form, json, redirect, useActionData, useNavigate } from "@remix-run/react";
import { postBoard } from "~/app";
import { requireAuth } from "~/middleware/auth";

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  const [accessToken, authRedirect] = await requireAuth(request);
  if (accessToken === "") return authRedirect;

  return json({});
}

export const action = async ({
  request,
}: ActionFunctionArgs) => {
  const [accessToken, authRedirect] = await requireAuth(request);
  if (accessToken === "") return authRedirect;

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

  const res = await postBoard(accessToken, boardName).then(res => {
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
  const navigate = useNavigate();
  return (
    <div className="isolate bg-white px-6 py-24 sm:py-32 lg:px-8">
      <div className="mx-auto max-w-2xl text-center">
        <h1 className="text-3xl ">タスクボード新規作成</h1>
      </div>
      <Form method="post" className="mx-auto mt-16 max-w-xl sm:mt-20">
        <p>

          <label htmlFor="name" className="block text-sm font-semibold leading-6 text-gray-900">ボード名称</label>
          <input type="text" name="name" className="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          {actionData?.errors?.name ? (
            <em>{actionData?.errors.name}</em>
          ) : null}
        </p>

        <div className="grid grid-cols-2 gap-4">
          <div className="mt-10">
            <button type="submit" className="block w-full rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">保存</button>
          </div>
          <div className="mt-10">
            <button type="submit" onClick={() => { navigate(-1) }} className="block w-full rounded-md bg-gray-500 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-gray-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-500">キャンセル</button>
          </div>
        </div>
      </Form>
    </div>
  );
}