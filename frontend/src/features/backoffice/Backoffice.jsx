import SessionBox from "./SessionBox";
import HeaderBox from "./HeaderBox";
import ConferenceBox from "./ConferenceBox";

function Backoffice() {
  return (
    <div className="mt-20 grid h-min w-5/6 grid-cols-2 border">
      <HeaderBox />
      <ConferenceBox />
      <SessionBox />
    </div>
  );
}

export default Backoffice;
