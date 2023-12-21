import AtendeesBox from "./atendees/AtendeesBox";
import SessionBox from "./sessions/SessionBox";

function Backoffice() {
  return (
    <main className="m-10 grid grid-cols-3 gap-x-5">
      <AtendeesBox />
      <div className=" col-span-2">
        <SessionBox />
      </div>
    </main>
  );
}

export default Backoffice;
