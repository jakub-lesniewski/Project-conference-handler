import SessionBox from "./session/SessionBox";
import HeaderBox from "./HeaderBox";
import ConferenceBox from "./ConferenceBox";

function Backoffice() {
  return (
    <div className="mt-20 grid h-min w-1/2 grid-cols-2 border">
      <HeaderBox />
      <ConferenceBox />
      <SessionBox />
    </div>
  );
}

export default Backoffice;
