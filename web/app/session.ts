// app/sessions.ts
import { createCookieSessionStorage } from "@remix-run/node"; // またはcloudflare/deno

type SessionData = {
  accessToken: string;
};

type SessionFlashData = {
  error: string;
};

const { getSession, commitSession, destroySession } =
  createCookieSessionStorage<SessionData, SessionFlashData>(
    {
      // `createCookie`からのCookie、またはCookieを作成するためのCookieOptions
      cookie: {
        name: "__session",
      },
    }
  );

export { getSession, commitSession, destroySession };