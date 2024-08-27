import { Outlet, useLoaderData } from "@remix-run/react"

export const loader = async () => {
  return {};
}

export default function Index() {
  const data = useLoaderData<typeof loader>();
  return (
    <Outlet />
  );
}