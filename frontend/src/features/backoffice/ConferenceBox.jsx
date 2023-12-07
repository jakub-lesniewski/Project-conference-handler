import SquareButton from "../../ui/SquareButton";

function ConferenceBox() {
  return (
    <section className="flex flex-col justify-between border border-l-0 border-t-0">
      <input
        placeholder="Conference name"
        className="border-b p-2 text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
      ></input>

      <div className="flex items-center gap-5">
        <div className="flex flex-col p-2">
          <input
            type="date"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>

        <div className="flex flex-col p-4">
          <input
            type="time"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>
      </div>

      <div className="flex items-center gap-5">
        <div className="flex flex-col p-2">
          <input
            type="date"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>

        <div className="flex flex-col p-4">
          <input
            type="time"
            className="w-min border-b text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
          ></input>
        </div>
      </div>

      <SquareButton>Add participants</SquareButton>
    </section>
  );
}

export default ConferenceBox;
