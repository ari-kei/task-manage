const baseUrl = "http://task-manage-app-1:8081";

export const postBoard = async (accessToken: string, name: string) => {
  const url = baseUrl + "/board";
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
  const url = baseUrl + "/boards"
  return fetch(url, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`,
    }
  })
}

export const fetchBoard = async (accessToken: string, boardId: string) => {
  const url = baseUrl + `/board/${boardId}`;
  return fetch(url,{
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`,
    }
  });
}