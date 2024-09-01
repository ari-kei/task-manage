export const postBoard = async (accessToken: string, name: string) => {
  const url = "http://task-manage-app-1:8081/board";
  return fetch(url, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name
    })
  });
};

export const fetchBoards = async (accessToken: string) => {
  const url = "http://task-manage-app-1:8081/boards"
  return fetch(url, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`,
    }
  })
}