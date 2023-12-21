import { useForm, FormProvider } from "react-hook-form";
import InputField from "../../../ui/InputField";
import EventBox from "./EventBox";
import { useState } from "react";

function ModalSession({ session, toggleModal, addSession }) {
  const { register, handleSubmit } = useForm();
  const [eventArr, setEventArr] = useState();

  function handleEventArrChange(newArr) {
    setEventArr(newArr);
  }

  function onSubmit(data) {
    const sessionData = data;

    const newSession = {
      name: sessionData.name,
      room: sessionData.room,
      atendeeLimit: sessionData.atendeeLimit,
      startingDate: sessionData.startingDate,
      endingDate: sessionData.endingDate,
      city: sessionData.city,
      street: sessionData.street,
      building: sessionData.building,
      eventArr: sessionData.eventArr,
    };

    addSession(newSession);
    console.log(newSession);
  }

  return (
    <FormProvider>
      <form onSubmit={handleSubmit(onSubmit)} className="h-[500px]">
        <section className="grid grid-cols-3 gap-4">
          <div className="flex flex-col gap-2">
            <InputField
              defaultValue={session?.name}
              label="Name"
              placeholder="De revolutionibus orbium coelestium"
              name="name"
              register={register}
            />

            <InputField
              defaultValue={session?.room}
              label="Room"
              placeholder="C201"
              name="room"
              register={register}
            />

            <InputField
              label="Atendee limit"
              type="number"
              name="atendeeLimit"
              register={register}
            />
          </div>

          <div className="flex flex-col gap-2">
            <InputField
              defaultValue={session?.city}
              label="City"
              placeholder="Łódź"
              name="city"
              register={register}
            />

            <InputField
              defaultValue={session?.street}
              label="Street"
              placeholder="Stefana Banacha 22"
              name="street"
              register={register}
            />

            <InputField
              defaultValue={session?.building}
              label="Building"
              placeholder="Faculty of Mathematics and Computer Science, University of Lodz"
              name="building"
              register={register}
            />
          </div>

          <div className="flex flex-col gap-2">
            <InputField
              defaultValue={session?.startingDate}
              label="Starting date"
              name="startingDate"
              type="datetime-local"
              register={register}
            />

            <InputField
              defaultValue={session?.endingDate}
              label="Ending date"
              name="endingDate"
              type="datetime-local"
              register={register}
            />
          </div>
        </section>

        <section className="overflox-auto-y mt-12 border border-fmcsBlack p-2">
          <EventBox handleEventArrChange={handleEventArrChange} />
        </section>

        <button className="mt-8 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite">
          submit changes
        </button>
      </form>
    </FormProvider>
  );
}

export default ModalSession;
