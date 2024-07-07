'use client';

import axios from 'axios';
import { useState } from 'react';
import { PROD_URL } from '../Data/URL';

export default function Modals({
  setIsShowmodal,
  setRefetch,
  isShowmodal,
  selectedPatientId,
  doctorData,
  isAuthenticaedUser,
}: ModalsProps) {
  const [showLoading, setShowLoading] = useState<boolean>(false);

  const closeModal = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setIsShowmodal('close');
  };

  const registerPatient = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setShowLoading(true);

    const form = e.target as HTMLFormElement;
    const patientName = (
      form.elements.namedItem('patientName') as HTMLInputElement
    ).value;
    const patientAge = (
      form.elements.namedItem('patientAge') as HTMLInputElement
    ).value;
    const patientGender = (
      document.getElementById('patientGender') as HTMLSelectElement
    ).value;
    const patientAddress = (
      form.elements.namedItem('patientAddress') as HTMLInputElement
    ).value;
    const patientPhoneNumber = (
      form.elements.namedItem('patientPhoneNumber') as HTMLInputElement
    ).value;

    axios
      .post(
        `${PROD_URL}/patient/register`,
        {
          patientName: patientName,
          patientAge: patientAge,
          patientGender: patientGender,
          patientAddress: patientAddress,
          patientPhoneNumber: patientPhoneNumber,
        },
        {
          headers: {
            Authorization: `Bearer ${isAuthenticaedUser}`,
          },
        },
      )
      .then(() => {
        setShowLoading(false);
        setIsShowmodal('close');
        form.reset();
        setRefetch((prev) => !prev);
      });
  };

  const addPatientData = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const doctorName = (
      form.elements.namedItem('doctorName') as HTMLInputElement
    ).value;
    const bloodpressure = (
      form.elements.namedItem('bloodpressure') as HTMLInputElement
    ).value;
    const heartRate = (form.elements.namedItem('heartRate') as HTMLInputElement)
      .value;
    const respirationRate = (
      form.elements.namedItem('respirationRate') as HTMLInputElement
    ).value;
    const temperature = (
      form.elements.namedItem('temperature') as HTMLInputElement
    ).value;
    const height = (form.elements.namedItem('height') as HTMLInputElement)
      .value;
    const weight = (form.elements.namedItem('weight') as HTMLInputElement)
      .value;
    const sicknessName = 'null';

    axios
      .post(
        `${PROD_URL}/patient/addPatientData/${selectedPatientId}`,
        {
          doctorName: doctorName,
          bloodpressure: bloodpressure,
          heartRate: heartRate,
          respirationRate: respirationRate,
          temparature: temperature,
          height: height,
          weight: weight,
          sicknessName: sicknessName,
        },
        {
          headers: {
            Authorization: `Bearer ${isAuthenticaedUser}`,
          },
        },
      )
      .then(() => {
        setIsShowmodal('close');
        form.reset();
        setRefetch((prev) => !prev);
      });
  };

  return (
    <div className='absolute w-screen h-screen flex justify-center items-center'>
      <div className='absolute w-screen h-screen bg-gray-500 opacity-[0.5]' />
      <div className='bg-white h-min w-[500px] opacity-[1] z-10 rounded-[20px] flex justify-center items-center p-[20px]'>
        <form
          action='submit'
          className='h-full w-full flex flex-col gap-[10px] justify-center'
          onSubmit={(e) => {
            {
              isShowmodal === 'addPatient'
                ? registerPatient(e)
                : addPatientData(e);
            }
          }}
        >
          <button
            onClick={(e) => {
              closeModal(e);
            }}
            className='h-[20px] w-[20px] leading-[20px] place-self-end text-red-400 hover:font-bold hover:font-bold text-lg'
          >
            x
          </button>

          {showLoading ? (
            <strong className='text-white'>Loading...</strong>
          ) : (
            <>
              {isShowmodal === 'addPatient' ? (
                <>
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='patientName'
                    id='patientName'
                    placeholder='Name'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='patientAge'
                    id='patientAge'
                    placeholder='Age'
                  />

                  <select
                    className='p-[5px] border-[1px] rounded-[5px]'
                    id='patientGender'
                    name='patientGender'
                  >
                    <option value='male'>Male</option>
                    <option value='female'>Female</option>
                  </select>
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='patientAddress'
                    id='patientAddress'
                    placeholder='Address'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='patientPhoneNumber'
                    id='patientPhoneNumber'
                    placeholder='Phone Number'
                  />
                </>
              ) : isShowmodal === 'processPatient' ? (
                <>
                  <select
                    name='doctorName'
                    id='doctorName'
                    className='p-[5px] border-[1px] rounded-[5px]'
                  >
                    <option value='' disabled>
                      Select Doctor
                    </option>
                    {doctorData.map((doctor, id) => (
                      <option value={doctor.doctorName} key={id}>
                        {doctor.doctorName}
                      </option>
                    ))}
                  </select>
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='bloodpressure'
                    id='bloodpressure'
                    placeholder='Blood Pressure'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='heartRate'
                    id='heartRate'
                    placeholder='Heart Rate'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='respirationRate'
                    id='respirationRate'
                    placeholder='Respiration Rate'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='temperature'
                    id='temperature'
                    placeholder='Temperature'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='height'
                    id='height'
                    placeholder='Height'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='weight'
                    id='weight'
                    placeholder='Weight'
                  />
                  <input
                    className='p-[5px] border-[1px] rounded-[5px]'
                    type='text'
                    name='sicknessName'
                    id='sicknessName'
                    placeholder='Sickness Name'
                    disabled
                  />
                </>
              ) : null}
              <input
                className='p-[5px] bg-green-200 hover:cursor-pointer'
                type='submit'
              />
            </>
          )}
        </form>
      </div>
    </div>
  );
}
