'use client';
import axios from 'axios';
import Link from 'next/link';
import { useEffect, useState } from 'react';
import ICDComponent from '../Components/ICDComponent';
import LoadingBar from '../Components/LoadingBar';
import { ICD_URL } from '../Data/URL';
// @ts-ignore
import qs from 'qs';
import { CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE } from '../Data/CONST';

export default function ICD() {
  const [icdResult, setIcdResult] = useState([]);
  const [showDetail, setShowDetail] = useState<IcdResult>({
    show: false,
    additionalInformation: '',
    definition: '',
    exclusions: [],
  });
  const [isLoading, setIsLoading] = useState<boolean>(false);

  useEffect(() => {
    axios
      .post(
        'http://localhost:3001/getToken',
        qs.stringify({
          client_id: CLIENT_ID,
          client_secret: CLIENT_SECRET,
          scope: SCOPE,
          grant_type: GRANT_TYPE,
        }),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        }
      )
      .then((res) => {
        localStorage.setItem('token', res.data.access_token);
      });
  }, []);

  const searchICD = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const form = e.target as HTMLFormElement;
    const ICDSearch = e.currentTarget.ICDSearch.value;
    axios
      .get(`${ICD_URL}${ICDSearch}`, {
        headers: {
          'Content-Type': 'application/json',
          'Accept-Language': 'en',
          'api-version': 'v2',
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      })
      .then((res) => {
        setIsLoading(false);
        setIcdResult(res.data.destinationEntities);
        form.reset();
      })
      .catch(() => {
        setIsLoading(false);
      });
  };

  const getDetail = (value: string) => {
    setIsLoading(true);
    axios
      .get(`http://localhost:3001/getdetails/${value}`, {
        headers: {
          'Content-Type': 'application/json',
          'Accept-Language': 'en',
          'api-version': 'v2',
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      })
      .then((res) => {
        setShowDetail((prev) => ({
          ...prev,
          show: true,
          additionalInformation: res.data.longDefinition
            ? res.data.longDefinition['@value']
            : '',
          definition: res.data.definition ? res.data.definition['@value'] : '',
          exclusions: res.data.exclusion
            ? res.data.exclusion.map((item: any) => item.label['@value'])
            : [],
        }));
        setIsLoading(false);
      });
  };

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
        <section className='flex-1 h-screen p-[20px] flex flex-col gap-[10px]'>
          <form
            action='submit'
            className='flex flex-col gap-[10px] justify-center'
            onSubmit={(e) => searchICD(e)}
          >
            <input
              type='text'
              name='ICDSearch'
              id='ICDSearch'
              placeholder='Search'
              className='p-[5px] w-full rounded-[5px] bg-gray-200 font-extralight'
            />
            <input
              type='submit'
              className='p-[5px] hover:cursor-pointer bg-gray-200 hover:bg-green-200 w-[100px] rounded-[5px] '
            />
          </form>

          {icdResult.length > 0 && (
            <div className='bg-gray-200 p-[10px] rounded-[5px] overflow-y-scroll mb-[60px] flex flex-col gap-[10px] relative'>
              {(icdResult || []).map((icd: any, id) => {
                return (
                  <div
                    key={id}
                    className='p-[5px] rounded-[5px] hover:cursor-pointer bg-gray-300 hover:bg-blue-200'
                    onClick={() => {
                      getDetail(icd.id.split('/mms/')[1]);
                    }}
                  >
                    <h1
                      dangerouslySetInnerHTML={{
                        __html: `${icd.title} (${icd?.matchingPVs[0]?.label})`,
                      }}
                    />
                  </div>
                );
              })}
            </div>
          )}

          <ICDComponent setShowDetail={setShowDetail} showDetail={showDetail} />
        </section>
      </div>
      {isLoading && <LoadingBar />}
    </main>
  );
}
