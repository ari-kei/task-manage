export const postBoard = async (accessToken: string, name: string) => {
  const url = "http://task-manage-app-1:8081/board";
  return fetch(url, {
    method: "POST",
    headers: {
      // TODO セッションのアクセストークンを利用する
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name
    })
  });
};