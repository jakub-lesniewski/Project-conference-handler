import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../BackofficeContext";
import { v4 as uuidv4 } from "uuid";
import InputField from "../../../ui/InputField";
import Button from "../../../ui/Button";

function EventForm({ currentTimelineElement, handleToggleEventModal }) {
  const { register, handleSubmit } = useForm();
  const { addTimelineElement, modifyTimelineElement } = useBackofficeContext();

  function onSubmit(data) {
    if (currentTimelineElement) {
      modifyTimelineElement(currentTimelineElement.id, data);
    } else {
      const newEvent = {
        id: uuidv4(),
        name: data.name,
        dateStart: data.dateStart,
        dateEnd: data.dateEnd,
      };
      addTimelineElement(newEvent);
    }
    handleToggleEventModal();
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2">
      <InputField
        label="name:"
        name="name"
        defaultValue={currentTimelineElement?.name || "Coffee Break"}
        register={register}
        placeholder="Coffee Break"
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

export default EventForm;
