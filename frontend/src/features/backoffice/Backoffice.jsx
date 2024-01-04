import AtendeesBox from "./atendees/AtendeesBox";
import SessionBox from "./sessions/SessionBox";
import Button from "../../ui/Button";
import { useState } from "react";

function Backoffice() {
  const [conference, setConference] = useState({
    atendeesArr: null,
    sessionsArr: null,
  });

  const setAtendeesData = (newAtendeesArr) => {
    setConference((prevConference) => ({
      ...prevConference,
      atendeesArr: newAtendeesArr,
    }));
  };

  const setSessionsData = (newSessionsArr) => {
    setConference((prevConference) => ({
      ...prevConference,
      sessionsArr: newSessionsArr,
    }));
  };

  const handleSubmit = () => {
    console.log(conference);
  };

  return (
    <>
      <Button onClick={handleSubmit}>Submit Conference</Button>
      <main className="m-10 grid grid-cols-3 gap-x-5">
        <AtendeesBox setAtendeesData={setAtendeesData} />
        <div className=" col-span-2">
          <SessionBox setSessionsData={setSessionsData} />
        </div>
      </main>
    </>
  );
}

export default Backoffice;
