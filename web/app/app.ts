export const postBoard = async (name: string) => {
  const url = "http://task-manage-app-1:8081/board";
  return fetch(url, {
    method: "POST",
    headers: {
      // TODO セッションのアクセストークンを利用する
      Authorization: "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJUYXNrTWFuYWdlckF1dGgiLCJzdWIiOiIxIiwiZXhwIjoxNzI0NzU5ODc3LCJpYXQiOjE3MjQ3NTYyNzcsImp0aSI6IjRhMDU1Yzc2LTFmZGMtNDc4Zi1hNTAwLWM1MWYzOGZmYjc4MSJ9.HyxPJIKA2jy4BcFLzzenciDKlQzOeHW9jKNUsQm9ADbk_Dlzs49tQZiiZE7ALQIHHM9lEUxvoGisS9VjeW3s4qKxs-TamQVrQlxKrNAWS9bxsMjIeYPEvfxAwzP3X-hMk0HDurw79j4ec7IhbLPOMynvTEqmz6gzKGqpK040iYL6KWQ71Om_vbiHG_9x5o9HKYtz5iX6x1bBjglsx0MjvxFvydIFuTdy51lL5aEsdt2F91AVm7O58DveBx6cs7x_e9N56Jc7bBZhCDs8rcS0PGsVfF20SIcWq9AFjvvXmUgVBVjDLb1zvTPTe-6CANVG-hcZEg5BVijNs9_oP2aLPA",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name
    })
  });
};