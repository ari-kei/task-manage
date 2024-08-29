export const login = async (userId: string, password: string) => {
  const url = "http://task-manage-auth-1:8080/login";
  return fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: userId,
      password: password
    })
  });
}