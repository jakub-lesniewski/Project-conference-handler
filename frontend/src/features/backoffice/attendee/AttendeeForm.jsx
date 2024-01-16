import { useForm } from "react-hook-form";
import { useBackofficeContext } from "../BackofficeContext";
import { v4 as uuidv4 } from "uuid";
import InputField from "../../../ui/InputField";
import Button from "../../../ui/Button";

function AttendeeForm({ currentAttendee, handleToggleModal }) {
  const { register, handleSubmit } = useForm();
  const { addAttendee, modifyAttendee } = useBackofficeContext();

  function onSubmit(data) {
    if (currentAttendee) {
      modifyAttendee(currentAttendee.id, data);
    } else {
      const newAttendee = {
        id: uuidv4(),
        name: data.name,
        surname: data.surname,
        email: data.email,
        affiliation: data.affiliation,
      };
      addAttendee(newAttendee);
    }
    handleToggleModal();
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2">
      <InputField
        label="name:"
        name="name"
        defaultValue={currentAttendee?.name}
        register={register}
        placeholder="John"
        autoFocus={true}
        required={true}
      />

      <InputField
        label="surname:"
        name="surname"
        defaultValue={currentAttendee?.surname}
        register={register}
        placeholder="Doe"
        required={true}
      />

      <InputField
        label="email:"
        name="email"
        defaultValue={currentAttendee?.email}
        register={register}
        placeholder="johndoe@example.com"
        type="email"
        required={true}
      />

      <InputField
        label="affiliation:"
        name="affiliation"
        defaultValue={currentAttendee?.affiliation}
        register={register}
        placeholder="University of Foo"
        required={true}
      />

      <Button type="submit">submit</Button>
    </form>
  );
}

export default AttendeeForm;
