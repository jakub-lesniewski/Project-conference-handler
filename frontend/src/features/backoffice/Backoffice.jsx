import AtendeesBox from "./AtendeesBox";
import SessionBox from "./sessions/SessionBox";

function Backoffice() {
  return (
    <main className="gap- m-10 grid grid-cols-2 gap-x-5">
      <SessionBox />
      <AtendeesBox />
    </main>
  );
}

export default Backoffice;
