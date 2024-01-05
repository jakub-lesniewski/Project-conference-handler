import { useForm } from "react-hook-form";
import { generateRandomID } from "../helpers";
import InputField from "../../../ui/InputField";

function ModalAtendee({ toggleModal, atendee, setAtendeesArr }) {
  const { register, handleSubmit } = useForm();

  function onSubmit(data) {
    const newAtendee = {
      id: generateRandomID(),
      name: data.name,
      surname: data.surname,
      email: data.email,
      affiliation: data.affiliation,
    };

    if (atendee) {
      setAtendeesArr((prevAtendees) => {
        const updatedAtendees = prevAtendees.map((attendee) => {
          if (attendee.id === atendee.id) {
            return newAtendee;
          }
          return attendee;
        });
        return updatedAtendees;
      });
    } else {
      setAtendeesArr((prevAtendees) => [...prevAtendees, newAtendee]);
    }

    toggleModal();
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="grid-cols2 grid gap-5">
        <InputField
          defaultValue={atendee?.name}
          label="Name"
          name="name"
          placeholder="John"
          register={register}
        />

        <InputField
            defaultValue={atendee?.surname}
            label="Surname"
            name="surname"
            placeholder="Doe"
            register={register}
        />

        <InputField
          defaultValue={atendee?.email}
          label="Email"
          name="email"
          type="email"
          placeholder="johndoe@example.com"
          register={register}
        />

        <InputField
          defaultValue={atendee?.affiliation}
          label="Afilliation"
          name="affiliation"
          placeholder="University of Lodz"
          register={register}
        />
      </div>
      <button className="mt-5 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite">
        submit changes
      </button>
    </form>
  );
}

export default ModalAtendee;
