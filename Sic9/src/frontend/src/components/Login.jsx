import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faLock } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import Layout from './Layout';
import { useForm } from 'react-hook-form';

const Login = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, errors },
  } = useForm();
  return (
    <Layout>
      <div className="flex justify-center mt-16">
        <img src="/logo.png" alt="logo" className="w-36" />
      </div>
      <form onSubmit={handleSubmit()}>
        <div className="border-b mt-16">
          <span>
            <FontAwesomeIcon icon={faEnvelope} className="text-[#A6A6A6] text-2xl pr-3" />
          </span>
          <input
            type="text"
            name="Email ID"
            id="email"
            placeholder="Email ID"
            className="bg-transparent  text-2xl"
            {...register('email', {
              required: '이메일은 필수 입력입니다.',
              pattern: {
                value: /\S+@\S+\.\S+/,
                message: '이메일 형식에 맞지 않습니다.',
              },
            })}
          />
        </div>
        {errors.email ? (
          <span className="text-[#FF5E5E]">{errors.email.message}</span>
        ) : (
          <span className="invisible h-4">email</span>
        )}
        <div className="border-b mt-10 flex items-center">
          <span>
            <FontAwesomeIcon icon={faLock} className="text-[#A6A6A6] text-2xl pr-3" />
          </span>
          <div className="flex">
            <input
              type="password"
              name="Password"
              placeholder="Password"
              className="text-2xl w-full mr-2"
              {...register('password', {
                required: '비밀번호는 필수 입력입니다.',
                // FIXME 테스트 끝나면 고치기
                // minLength: {
                //   value: 8,
                //   message: '8자리 이상 비밀번호를 사용하세요.',
                // },
              })}
            />
            <span className="text-[#18580C] ">Forgot?</span>
          </div>
        </div>
        {errors.password ? (
          <span className="text-[#FF5E5E]">{errors.password.message}</span>
        ) : (
          <span className="invisible h-4">password</span>
        )}
        <div>
          <button
            type="submit"
            className="bg-[#18580C] text-white w-full h-12 rounded-lg text-2xl font-bold mt-10"
            disabled={isSubmitting}
          >
            Login
          </button>
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
