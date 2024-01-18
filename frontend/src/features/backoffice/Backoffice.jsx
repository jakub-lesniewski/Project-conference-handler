import { useState, useEffect } from "react";
import { useBackofficeContext } from "./BackofficeContext";
import { closeConference, createConference } from "./services";
import AttendeesBox from "./attendee/AttendeesBox";
import TimelineBox from "./timeline/TimelineBox";
import Button from "../../ui/Button";

function Backoffice() {
  const { attendeesArr, timelineArr } = useBackofficeContext();
  const [eventsArr, setEventsArr] = useState([]);
  const [sessionsArr, setSessionsArr] = useState([]);

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

    setEventsArr(tempeventsArr);
    setSessionsArr(tempSessions);
  }, [timelineArr, attendeesArr]);

  console.log(eventsArr);

  return (
    <>
      <div className="mx-24 mt-5 flex gap-5 rounded-lg border-4 p-3">
        <Button
          onClick={() => createConference(attendeesArr, eventsArr, sessionsArr)}
          style="alt"
        >
          create conference
        </Button>
        <Button onClick={closeConference} style="alt">
          close conference
        </Button>
      </div>
      <main className="grid grid-cols-1 lg:grid-cols-5 2xl:mx-20">
        <div className="col-span-2">
          <AttendeesBox />
        </div>
        <div className="col-span-3">
          <TimelineBox />
        </div>
      </main>
    </>
  );
}

export default Backoffice;
