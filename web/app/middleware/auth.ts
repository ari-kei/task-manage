import { redirect, TypedResponse } from "@remix-run/node";
import { getSession } from "~/session";

export const requireAuth = async (request: Request): Promise<[string, TypedResponse<never> | null]> => {
  const session = await getSession(
    request.headers.get("Cookie")
  )
  const accessToken = session.get("accessToken");
  if (accessToken == undefined || accessToken.length <= 0) {
    return ["", redirect("/login")];
  }
  return [accessToken, null];
} 