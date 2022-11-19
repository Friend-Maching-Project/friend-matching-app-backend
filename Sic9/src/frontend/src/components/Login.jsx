import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faLock } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import Layout from './Layout';

const Login = () => {
  return (
    <Layout>
      <div className="flex justify-center mt-16">
        <img src="/logo.png" alt="logo" className="w-36" />
      </div>
      <form>
        <div className="border-b mt-16">
          <span>
            <FontAwesomeIcon icon={faEnvelope} className="text-[#A6A6A6] text-2xl pr-3" />
          </span>
          <input
            type={'email'}
            name={'Email ID'}
            placeholder={'Email ID'}
            className="bg-transparent  text-2xl"
          />
        </div>
        <span className="text-[#FF5E5E]">Email format is not appropirate.</span>
        <div className="border-b mt-10 flex items-center">
          <span>
            <FontAwesomeIcon icon={faLock} className="text-[#A6A6A6] text-2xl pr-3" />
          </span>
          <div className="flex">
            <input
              type={'password'}
              name={'Password'}
              placeholder={'Password'}
              className="text-2xl w-full mr-2"
            />
            <span className="text-[#18580C] ">Forgot?</span>
          </div>
        </div>
        <span className="text-[#FF5E5E]">Email format is not appropirate.</span>
        <div>
          <input
            type={'submit'}
            name={'Login'}
            value={'Login'}
            className="bg-[#18580C] text-white w-full h-12 rounded-lg text-2xl font-bold mt-10"
          />
        </div>
      </form>
      <div className="text-center mt-6">
        <p className="text-[#A6A6A6]">OR</p>
      </div>
      <div className="flex justify-between mx-10 mt-6">
        <div className="bg-[#F2F2F2] rounded-full w-10 h-10">
          <div className="bg-google-logo bg-contain bg-no-repeat w-10 h-10"></div>
        </div>
        <div className=" bg-[#5FC53A] rounded-full w-10 h-10">
          <div className="bg-naver-logo bg-cover bg-no-repeat h-10 w-10 rounded-full"></div>
        </div>
        <div className="bg-[#F6E24B] w-10 h-10 rounded-full">
          <div className="bg-kakao-logo bg-contain bg-no-repeat h-10 w-10 rounded-full"></div>
        </div>
      </div>
      <div className="border-t mt-6 pt-6 flex justify-around px-14">
        <span className="text-[#A6A6A6]">New member?</span>
        <span className="text-[#18580C]">Register</span>
      </div>
    </Layout>
  );
};

export default Login;
