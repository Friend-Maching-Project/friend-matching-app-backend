import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faLock } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import Layout from './Layout';
import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { login } from '../redux/modules/auth';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const onLogin = (data) => {
    dispatch(login(data)).then((res) => {
      if (res.payload) {
        // 로그인 성공
        alert('로그인 완료');
        console.log('로그인 완료');
      } else {
        // 로그인 실패
        alert('이메일 혹은 비밀번호를 확인해주세요.');
      }
    });
  };
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, errors },
  } = useForm();
  const onSignUp = () => {
    navigate('/signup');
  };
  return (
    <Layout height="h-100">
      <div className="flex justify-center pt-16">
        <img src="/logo.png" alt="logo" className="w-36" />
      </div>
      <form onSubmit={handleSubmit(onLogin)}>
        <div className="border-b mt-16">
          <span>
            <FontAwesomeIcon icon={faEnvelope} className="text-waniGray text-2xl pr-3" />
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
            <FontAwesomeIcon icon={faLock} className="text-waniGray text-2xl pr-3" />
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
            <span className="text-waniGreen ">Forgot?</span>
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
            className="bg-waniGreen text-white w-full h-12 rounded-lg text-2xl font-bold mt-10"
            disabled={isSubmitting}
          >
            Login
          </button>
        </div>
      </form>
      <div className="text-center mt-6">
        <p className="text-waniGray">OR</p>
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
        <span className="text-[#18580C]" onClick={onSignUp}>
          Register
        </span>
      </div>
    </Layout>
  );
};

export default Login;
