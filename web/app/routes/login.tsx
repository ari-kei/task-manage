import { ActionFunctionArgs, LoaderFunctionArgs } from "@remix-run/node";
import { Form, json, redirect, useActionData } from "@remix-run/react";
import { login } from "~/auth";
import { commitSession, getSession } from "~/session";

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  );

  if (session.has("accessToken")) {
    return redirect("/");
  }

  const data = { error: session.get("error") };
  return json(data, {
    headers: {
      "Set-Cookie": await commitSession(session),
    }
  })
}

export const action = async ({
  request,
}: ActionFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  );
  const formData = await request.formData();
  const userId = String(formData.get("userId"));
  const password = String(formData.get("password"));

  const errors: {
    userId: string,
    password: string,
  } = { userId: "", password: "" };
  if (userId.length <= 0) {
    errors.userId = "ユーザIDを入力してください";
  }
  if (password.length <= 0) {
    errors.password = "パスワードを入力してください";
  }

  if (errors.userId.length > 0 || errors.password.length > 0) {
    return json({ errors });
  }
  const res = await login(userId, password).then(res => {
    if (!res.ok) {
      session.flash("error", "ユーザIDまたはパスワードが不正です");
      throw new Error(`レスポンスステータス: ${res.status}`);
    }
    return res.json();
  }).catch(error => {
    console.log(error);
    session.flash("error", "ログインに失敗しました。再度お試しください。")
    return;
  });
  session.set("accessToken", res.accessToken);
  return redirect("/board", {
    headers: {
      "Set-Cookie": await commitSession(session),
    }
  });
}

export default function Index() {
  const actionData = useActionData<typeof action>();
  return (
    <div className="isolate bg-white px-6 py-24 sm:py-32 lg:px-8">
      <div className="mx-auto max-w-2xl text-center">
        <h1 className="text-3xl ">ログイン</h1>
      </div>
      <Form method="post" className="mx-auto mt-16 max-w-xl sm:mt-20">
        <p>

          <label htmlFor="userId" className="block text-sm font-semibold leading-6 text-gray-900">ユーザID</label>
          <input type="text" name="userId" className="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          {actionData?.errors?.userId ? (
            <em>{actionData?.errors.userId}</em>
          ) : null}
        </p>
        <p>

          <label htmlFor="password" className="block text-sm font-semibold leading-6 text-gray-900">パスワード</label>
          <input type="password" name="password" className="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          {actionData?.errors?.password ? (
            <em>{actionData?.errors.password}</em>
          ) : null}
        </p>

        <div className="mt-10">
          <button type="submit" className="block w-full rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">ログイン</button>
        </div>
      </Form>
    </div>
  );
}