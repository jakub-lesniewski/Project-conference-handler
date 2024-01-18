import axios from "axios";
// import CircularJSON from "circular-json";

export function createConference(attendeesArr, eventsArr, sessionsArr) {
  console.log("attendees:", attendeesArr);
  console.log("events:", eventsArr);
  console.log("sessions:", sessionsArr);

  let config = {
    method: "post",
    maxBodyLength: Infinity,
    url: "http://localhost:8080/admin/addAllConference",
    headers: {
      "Content-Type": "application/json",
    },
    data: JSON.stringify({
      attendees: attendeesArr,
      sessions: sessionsArr,
      events: eventsArr,
    }),
  };

  return axios
    .request(config)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      throw error;
    });
}

export function closeConference() {
  let config = {
    method: "delete",
    maxBodyLength: Infinity,
    url: "http://localhost:8080/admin/deleteAllConference",
    headers: {},
  };

  axios
    .request(config)
    .then((response) => {
      console.log(JSON.stringify(response.data));
    })
    .catch((error) => {
      console.log(error);
    });
}
