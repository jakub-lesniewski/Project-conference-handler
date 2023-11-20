import SquareButton from "./ui/SquareButton";
import { useBackoffice } from "./BackofficeContext";
import SessionBox from "./ui/SessionBox";
import HeaderBox from "./ui/HeaderBox";

function Backoffice() {
  const { conferenceName, setConferenceName } = useBackoffice();

  function handleConferenceNameChange(event) {
    setConferenceName(event.target.value);
  }

  return (
    <div className="mt-20 grid h-min w-5/6 grid-cols-2 border">
      <HeaderBox />

      <section className="flex flex-col justify-between border border-l-0 border-t-0">
        <input
          placeholder="Conference name"
          className="border-b p-2 text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          value={conferenceName}
          onChange={handleConferenceNameChange}
        ></input>
        <div className="flex flex-col p-2">
          <label>From</label>
          <input
            type="date"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>
        <div className="flex flex-col p-2">
          <label>To</label>
          <input
            type="date"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>
        <SquareButton>Add participants</SquareButton>
      </section>

      <SessionBox />
    </div>
  );
}

export default Backoffice;
