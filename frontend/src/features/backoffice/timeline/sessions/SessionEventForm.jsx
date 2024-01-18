import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../../BackofficeContext";
import { v4 as uuidv4 } from "uuid";
import InputField from "../../../../ui/InputField";
import SelectField from "../../../../ui/SelectField";
import Button from "../../../../ui/Button";

function SessionEventForm({
  currentSessionEvent,
  addSessionEvent,
  modifySessionEvent,
  handleToggleModal,
}) {
  const { register, handleSubmit, watch } = useForm({
    defaultValues: {
      type: currentSessionEvent?._abstract ? "lecture" : "event",
    },
  });
  const { attendeesArr } = useBackofficeContext();

  function onSubmit(data) {
    if (currentSessionEvent) {
      modifySessionEvent(currentSessionEvent.id, data);
    } else {
      const newEvent = {
        id: uuidv4(),
        type: data.type,
        name: data.name,
        timeStart: data.timeStart,
        timeEnd: data.timeEnd,
      };

      if (data.type === "lecture") {
        newEvent._abstract = data._abstract;
        newEvent.headLead = data.headLead;
      }
      addSessionEvent(newEvent);
    }
    handleToggleModal();
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
            name="_abstract"
            defaultValue={currentSessionEvent?._abstract}
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
        defaultValue={currentSessionEvent?.timeStart}
        register={register}
        required={true}
      />
      <InputField
        label="end time:"
        name="timeEnd"
        type="time"
        defaultValue={currentSessionEvent?.timeEnd}
        register={register}
        required={true}
      />

      <Button type="submit">submit</Button>
    </form>
  );
}

export default SessionEventForm;
