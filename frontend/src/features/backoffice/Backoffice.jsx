import SquareButton from "./ui/SquareButton";

function Backoffice() {
  return (
    <div className="mt-20 grid h-min w-5/6 grid-cols-2 border">
      <header className="flex flex-col justify-between gap-5 border border-t-0">
        <div className="p-10 text-2xl font-bold tracking-wider">
          <h1>Conference panel</h1>
          <h2>organisers_token123</h2>
        </div>

        <SquareButton>Create conference</SquareButton>
      </header>

      <section className="flex flex-col justify-between border border-l-0 border-t-0">
        <input
          placeholder="Conference name"
          className="border-b p-2 text-lg tracking-wider focus:outline-none focus:ring focus:ring-fmcsGreen"
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

      <section className="col-span-full flex flex-col border border-t-0">
        <p className="tacking-wider border-b p-3 text-lg font-semibold">
          Some section name
        </p>
        <p className="tacking-wider border-b p-3 text-lg font-semibold">
          Some section name
        </p>
        <p className="tacking-wider border-b p-3 text-lg font-semibold">
          Some section name
        </p>

        <SquareButton>Add session</SquareButton>
      </section>
    </div>
  );
}

export default Backoffice;
