import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../BackofficeContext";
import { v4 as uuidv4 } from "uuid";
import InputField from "../../../ui/InputField";
import Button from "../../../ui/Button";

function SessionForm({ currentTimelineElement, handleToggleSessionModal }) {
  const { register, handleSubmit } = useForm();
  const { addTimelineElement, modifyTimelineElement } = useBackofficeContext();

  function onSubmit(data) {
    if (currentTimelineElement) {
      modifyTimelineElement(currentTimelineElement.id, data);
    } else {
      const newSession = {
        id: uuidv4(),
        name: data.name,
        room: data.room,
        attendeeLimit: data.attendeeLimit,
        building: data.building,
        street: data.street,
        dateStart: data.dateStart,
        dateEnd: data.dateEnd,
      };
      addTimelineElement(newSession);
    }
    handleToggleSessionModal();
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2">
      <InputField
        label="name:"
        name="name"
        defaultValue={currentTimelineElement?.name}
        register={register}
        placeholder="De revolutionibus"
        autoFocus={true}
        required={true}
      />
      <InputField
        label="room:"
        name="room"
        defaultValue={currentTimelineElement?.room}
        register={register}
        placeholder="C201"
        autoFocus={true}
        required={true}
      />
      <InputField
        label="attendee limit:"
        name="attendeeLimit"
        type="number"
        defaultValue={currentTimelineElement?.attendeeLimit}
        register={register}
        autoFocus={true}
        required={true}
      />
      <InputField
        label="building:"
        name="building"
        defaultValue={currentTimelineElement?.building}
        register={register}
        placeholder="FMCS UŁ"
        autoFocus={true}
        required={true}
      />
      <InputField
        label="street:"
        name="street"
        defaultValue={currentTimelineElement?.street}
        register={register}
        placeholder="Stefena Banacha 22"
        autoFocus={true}
        required={true}
      />
      <InputField
        label="start date:"
        name="dateStart"
        type="datetime-local"
        defaultValue={currentTimelineElement?.dateStart || "2024-01-16T10:00"}
        register={register}
        required={true}
      />
      <InputField
        label="end date:"
        name="dateEnd"
        type="datetime-local"
        defaultValue={currentTimelineElement?.dateEnd || "2024-01-16T12:00"}
        register={register}
        required={true}
      />

      <Button type="submit">Submit</Button>
    </form>
  );
}

export default SessionForm;
