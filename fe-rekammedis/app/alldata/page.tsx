'use client';
import axios from 'axios';
import Link from 'next/link';
import { useState } from 'react';
import AddDataForm from '../Components/AddDataForm';
import LoadingBar from '../Components/LoadingBar';
import { PROD_VM } from '../Data/URL';

export default function AllData() {
  const [addDataType, setAddDataType] = useState<string>('');
  const [isLoading, setIsLoading] = useState<boolean>(false);
  let isAuthenticaedUser: string | null = null;
  if (typeof window !== 'undefined') {
    isAuthenticaedUser = localStorage.getItem('jwt');
  }

  const addDoctorData = (e: any) => {
    setIsLoading(true);
    e.preventDefault();
    const form = e.target;
    const doctorName = e.target.doctorName.value;
    axios
      .post(
        `${PROD_VM}/doctor/add`,
        {
          doctorName: doctorName,
        },
        {
          headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
        },
      )
      .then(() => {
        setIsLoading(false);
        form.reset();
      })
      .catch((err) => {
        setIsLoading(false);
        console.error(err);
      });
  };

  const addDataForm = [
    {
      type: 'doctor',
      form: (
        <form
          action='submit'
          className='flex flex-col gap-[10px]'
          onSubmit={(e) => {
            addDoctorData(e);
          }}
        >
          <input
            id='doctorName'
            name='doctorName'
            type='text'
            placeholder='Doctor Name'
            className='p-[5px]'
          />
          <input
            className='bg-gray-200 hover:bg-green-200 rounded-[5px] p-[5px] w-[100px] hover:cursor-pointer'
            type='submit'
          />
        </form>
      ),
    },
    {
      type: 'medicine',
      form: (
        <form action='submit' className='flex flex-col gap-[10px] '>
          <input className='p-[5px]' type='text' placeholder='name' />
          <input className='p-[5px]' type='text' placeholder='price' />
          <input className='p-[5px]' type='text' placeholder='quantity' />
          <input className='p-[5px]' type='text' placeholder='description' />
          <input
            className='bg-gray-200 hover:bg-green-200 rounded-[5px] p-[5px] w-[100px] hover:cursor-pointer'
            type='submit'
          />
        </form>
      ),
    },
    {
      type: 'employee',
      form: (
        <form action='submit' className='flex flex-col gap-[10px] '>
          <input className='p-[5px]' type='text' placeholder='name' />
          <input className='p-[5px]' type='text' placeholder='phone' />
          <input className='p-[5px]' type='text' placeholder='email' />
          <input className='p-[5px]' type='text' placeholder='address' />
          <input className='p-[5px]' type='text' placeholder='position' />
          <input
            className='bg-gray-200 hover:bg-green-200 rounded-[5px] p-[5px] w-[100px] hover:cursor-pointer'
            type='submit'
          />
        </form>
      ),
    },
  ];

  return (
    <main className='flex flex-col items-center justify-between min-h-screen'>
      <nav className='bg-gray-200 p-[10px] w-full h-[60px] flex items-center justify-between'></nav>
      <div className='flex-1 w-full flex items-center justify-center'>
        <section className='bg-gray-300 w-[250px] h-screen '>
          <div className='flex flex-col gap-[10px]'>
            <Link
              href='/home'
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
            >
              Patient Data
            </Link>
            <Link
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
              href='/alldata'
            >
              All Data
            </Link>

            <Link
              href='/ICD'
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
            >
              ICD
            </Link>
          </div>
        </section>
        <section className='flex-1 h-screen p-[20px] flex flex-col gap-[20px]'>
          <div className='flex items-center gap-[20px] '>
            <div
              onClick={() => {
                setAddDataType('doctor');
              }}
              className='bg-gray-200 rounded-[5px] p-[5px] hover:cursor-pointer font-extralight'
            >
              Add Doctor Data
            </div>
            {/* <div
              onClick={() => {
                setAddDataType('medicine');
              }}
              className='bg-gray-200 rounded-[5px] p-[5px] hover:cursor-pointer font-extralight'
            >
              Add Medicine Data
            </div> */}
            {/* <div
              onClick={() => {
                setAddDataType('employee');
              }}
              className='bg-gray-200 rounded-[5px] p-[5px] hover:cursor-pointer font-extralight'
            >
              Add Employee Data
            </div> */}
          </div>

          <div className='h-[300px] w-[490px]'>
            <AddDataForm
              addDataForm={
                addDataType == 'doctor'
                  ? addDataForm[0]
                  : addDataType == 'medicine'
                  ? addDataForm[1]
                  : addDataType == 'employee'
                  ? addDataForm[2]
                  : null
              }
            />
          </div>
        </section>
      </div>
      {isLoading && <LoadingBar />}
    </main>
  );
}
