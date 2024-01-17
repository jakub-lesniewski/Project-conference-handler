import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../../BackofficeContext";
import InputField from "../../../../ui/InputField";
import SelectField from "../../../../ui/SelectField";

function SessionEventForm({ currentSessionEvent }) {
  const { register, handleSubmit, watch } = useForm({
    defaultValues: {
      type: currentSessionEvent?.abstract ? "lecture" : "event",
    },
  });
  const { attendeesArr } = useBackofficeContext();
  console.log(attendeesArr);

  function onSubmit(data) {
    console.log(data);
  }

  const TYPE_OPTIONS = ["lecture", "event"];

  const isLecture = watch("type") === "lecture";

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2">
      <SelectField
        name="type"
        values={TYPE_OPTIONS}
        defaultValue={TYPE_OPTIONS[0]}
        register={register}
      />
      <InputField
        label="name:"
        name="name"
        defaultValue={currentSessionEvent?.name || "Coffee Break"}
        register={register}
        placeholder="Coffee Break"
        autoFocus={true}
        required={true}
      />
      {isLecture && (
        <>
          <InputField
            label="abstract:"
            name="abstract"
            defaultValue={currentSessionEvent?.abstract}
            register={register}
            placeholder="abstract.pdf"
            autoFocus={false}
            required={false}
          />
          <select
            {...register("headLead")}
            required
            className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
          >
            {attendeesArr.map((attendee) => (
              <option
                value={attendee.email}
                key={attendee.id}
              >{`${attendee.name} ${attendee.surname}`}</option>
            ))}
          </select>
        </>
      )}
      <InputField
        label="start time:"
        name="timeStart"
        type="time"
        defaultValue={currentSessionEvent?.dateStart || "10:00"}
        register={register}
        required={true}
      />
      <InputField
        label="end time:"
        name="timeEnd"
        type="time"
        defaultValue={currentSessionEvent?.dateEnd || "12:00"}
        register={register}
        required={true}
      />
    </form>
  );
}

export default SessionEventForm;
