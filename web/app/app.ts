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

export const createTaskCard = async (accessToken: string, boardId: string, taskName: string, taskStatus: string) => {
  const url = baseUrl + '/task';
  return fetch(url, {
    method: "POST",
      headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      boardId: boardId,
      name: taskName,
      status: taskStatus,
    })
  })
}

export const fetchTasks = async (accessToken: string, boardId: string) => {
  const param = {'boardId': boardId};
  const query = new URLSearchParams(param)
  const url = baseUrl + `/tasks?${query}`;
  return fetch(url, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`
    }
  })
}