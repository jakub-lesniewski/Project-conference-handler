import { useForm } from "react-hook-form";
import Button from "../../../ui/Button";

function EventForm() {
  const dummyData = [
    {
      id: 1,
      name: "Julek",
      surname: "Julianowy",
      affiliation: "Polibuda Mołdawska",
    },
    {
      id: 2,
      name: "Tomasz",
      surname: "Marszałek",
      affiliation: "Polibuda Londyńska",
    },
    {
      id: 3,
      name: "Jan",
      surname: "Pepik",
      affiliation: "Polibuda Praska",
    },
  ];

  const { register, handleSubmit, watch } = useForm();

  const selectedType = watch("type");

  function onSubmit(data) {
    console.log(data);
  }

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="flex items-center gap-5 border p-5"
    >
      <select
        {...register("type", { required: true })}
        name="type"
        defaultValue="event"
        className="rounded-md border-2 p-2 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
      >
        <option value="event">event</option>
        <option value="lecture">lecture</option>
      </select>
      <div className="flex items-center gap-2">
        <label>from:</label>
        <input
          {...register("from", { required: true })}
          type="time"
          defaultValue="10:00"
          className="border-2 p-1 text-center text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        ></input>
      </div>
      <div className="flex items-center gap-2">
        <label>to:</label>
        <input
          {...register("to", { required: true })}
          type="time"
          defaultValue="12:00"
          className="border-2 p-1 text-center text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        ></input>
      </div>
      {selectedType === "lecture" && (
        <>
          <input
            {...register("abstract", { required: true })}
            placeholder="abstract link"
            defaultValue="https://example.com/path/to/your/dummy.pdf"
            className="rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
          />

          <select
            {...register("headLead", { required: true })}
            name="headLead"
            className="rounded-md border-2 p-2 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
          >
            {dummyData.map((headLead) => (
              <option key={headLead.id} value={headLead.id}>
                {`${headLead.name} ${headLead.surname}, ${headLead.affiliation}`}
              </option>
            ))}
          </select>
        </>
      )}
      <Button type="submit">Submit changes</Button>
    </form>
  );
}

export default EventForm;
