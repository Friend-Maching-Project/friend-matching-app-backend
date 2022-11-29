import axios from 'axios';
import React from 'react';
import { useState } from 'react';
import Birth from '../components/SignUp/Birth';
import Email from '../components/SignUp/Email';
import Major from '../components/SignUp/Major';
import Nickname from '../components/SignUp/Nickname';
import Password from '../components/SignUp/Password';
import Sex from '../components/SignUp/Sex';

const SignUpPage = () => {
  const [page, setPage] = useState(0);
  const [signUpInfo, setSignUpInfo] = useState({
    email: '',
    password: '',
    nickname: '',
    sex: 'male',
    birth: '',
    major: '',
  });

  const goNextPage = () => {
    setPage(page + 1);
  };
  const goPreviousPage = () => {
    setPage(page - 1);
  };
  const signUp = async ({ email, password, nickname, sex, birth, major }) => {
    try {
      const response = await axios.post('/auth/signup', {
        email,
        password,
        nickname,
        sex,
        birth,
        major,
      });
      return response.status;
    } catch (err) {
      return err.response.status;
    }
  };
  const pages = [
    <Email
      page={page}
      setPage={setPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
    />,
    <Password
      page={page}
      setPage={setPage}
      goPreviousPage={goPreviousPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
    />,
    <Nickname
      page={page}
      setPage={setPage}
      goPreviousPage={goPreviousPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
    />,
    <Sex
      page={page}
      setPage={setPage}
      goPreviousPage={goPreviousPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
    />,
    <Birth
      page={page}
      setPage={setPage}
      goPreviousPage={goPreviousPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
    />,
    <Major
      page={page}
      setPage={setPage}
      goPreviousPage={goPreviousPage}
      goNextPage={goNextPage}
      signUpInfo={signUpInfo}
      setSignUpInfo={setSignUpInfo}
      signUp={signUp}
    />,
  ];
  return <>{pages[page]}</>;
};

export default SignUpPage;
