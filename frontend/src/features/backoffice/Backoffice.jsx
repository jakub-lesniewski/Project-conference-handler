import { useState, useEffect } from "react";
import AttendeesBox from "./attendee/AttendeesBox";
import TimelineBox from "./timeline/TimelineBox";
import { useBackofficeContext } from "./BackofficeContext";

function Backoffice() {
  const { attendeesArr, timelineArr } = useBackofficeContext();
  const [eventsArr, setEventsArr] = useState([]);
  const [sessionsArr, setSessionsArr] = useState([]);
  const [sessionData, setSessionData] = useState({});

  useEffect(() => {
    const tempeventsArr = [];
    const tempSessions = [];

    timelineArr.forEach((item) => {
      if ("attendeeLimit" in item) {
        tempSessions.push(item);
      } else {
        tempeventsArr.push(item);
      }
    });

    setSessionData({
      attendees: attendeesArr,
      events: tempeventsArr,
      sessions: tempSessions,
    });

    setEventsArr(tempeventsArr);
    setSessionsArr(tempSessions);
  }, [timelineArr, attendeesArr]);

  console.log(sessionData);

  return (
    <main className="grid grid-cols-5 sm:grid-cols-1 lg:grid-cols-5 2xl:mx-20">
      <div className="col-span-2">
        <AttendeesBox />
      </div>
      <div className="col-span-3">
        <TimelineBox />
      </div>
    </main>
  );
}

export default Backoffice;
