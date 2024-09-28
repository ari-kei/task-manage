import {
  json,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  useLoaderData,
} from "@remix-run/react";
import "./tailwind.css";
import Header from "~/components/Header";
import { LoaderFunctionArgs } from "@remix-run/node";
import { getSession } from "./session";

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ja">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export const loader = async ({
  request,
}: LoaderFunctionArgs) => {
  const session = await getSession(
    request.headers.get("Cookie")
  );
  let accessToken = session.get("accessToken");
  if (accessToken == undefined) {
    accessToken = "";
  }
  console.log(accessToken);

  return json({ accessToken });
}

export default function App() {
  const { accessToken } = useLoaderData<typeof loader>();
  return (
    <>
      <Header accessToken={accessToken} />
      <div className="p-10">
        <Outlet />
      </div>
    </>
  );
}
