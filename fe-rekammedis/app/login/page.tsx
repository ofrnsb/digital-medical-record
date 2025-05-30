'use client';

import axios from 'axios';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import { PROD_VM } from '../Data/URL';

export default function Login() {
  const { push } = useRouter();
  const [showLoading, setShowLoading] = useState<boolean>(false);

  useEffect(() => {
    localStorage.clear();
  }, []);

  const registerUser = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setShowLoading(true);
    const form = e.target as HTMLFormElement;
    const username = (form.elements.namedItem('username') as HTMLInputElement)
      .value;
    const password = (document.getElementById('password') as HTMLSelectElement)
      .value;

    axios
      .post(`${PROD_VM}/login`, {
        username: username,
        password: password,
      })
      .then((res) => {
        localStorage.setItem('jwt', res.data.payload.jwt);
        push('/home');
      });
    form.reset();
  };

  return (
    <div className='w-screen h-screen bg-gray-400 flex items-center justify-center'>
      {showLoading ? (
        <>
          <strong className='text-white'>Loading</strong>
        </>
      ) : (
        <div className='flex gap-[5px]'>
          <Link
            href='/register'
            className='bg-green-300 p-[10px] rounded-l-xl hover:cursor-pointer hover:bg-green-400'
          />
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
              id='password'
              name='password'
              className='p-[5px] rounded-[5px] font-extralight active:border-0'
              type='password'
              placeholder='password'
            />
            <input
              type='submit'
              className='bg-sky-100 hover:bg-green-100 hover:cursor-pointer bg-red-200 p-[5px] rounded-[5px]'
            />
          </form>
        </div>
      )}
    </div>
  );
}
