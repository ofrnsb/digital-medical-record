'use client';

import axios from 'axios';
import Link from 'next/link';

import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import { PROD_URL } from '../Data/URL';

export default function Register() {
  const { push } = useRouter();
  const [showLoading, setShowLoading] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  useEffect(() => {
    localStorage.clear();
  }, []);

  const registerUser = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const form = e.target as HTMLFormElement;
    const username = (form.elements.namedItem('username') as HTMLInputElement)
      .value;
    const role = (form.elements.namedItem('role') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLSelectElement)
      .value;

    try {
      axios
        .post(`${PROD_URL}/register`, {
          username,
          password,
          role: role,
        })
        .then(() => {
          setIsLoading(true);
          setShowLoading(false);
          push('/login');
        })
        .catch(() => {
          setIsLoading(true);
        });
    } catch (error) {
      console.error('Error during registration:', error);
    }

    form.reset();
  };
  return (
    <div className='w-screen h-screen bg-gray-200 flex items-center justify-center'>
      {showLoading ? (
        <>
          <strong className='text-white'>Loading</strong>
        </>
      ) : (
        <div className='flex gap-[5px]'>
          <form
            action='submit'
            className='flex flex-col gap-[10px]'
            onSubmit={(e) => {
              registerUser(e);
            }}
          >
            <input
              id='username'
              name='username'
              className='p-[5px] rounded-[5px] font-extralight active:border-0'
              type='text'
              placeholder='username'
            />
            <input
              id='role'
              name='role'
              className='p-[5px] rounded-[5px] font-extralight active:border-0'
              type='text'
              placeholder='role'
            />
            <input
              id='password'
              name='password'
              className='p-[5px] rounded-[5px] font-extralight active:border-0'
              type='password'
              placeholder='password'
            />
            <input
              type='submit'
              className='bg-sky-200 hover:bg-green-200 hover:cursor-pointer bg-red-200 p-[5px] rounded-[5px]'
            />
          </form>
          <Link
            href='/login'
            className='bg-green-300 p-[10px] rounded-r-xl hover:cursor-pointer hover:bg-green-400'
          />
        </div>
      )}
    </div>
  );
}
