import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleLeft, faCircleRight, faLock } from '@fortawesome/free-solid-svg-icons';
import React, { useCallback } from 'react';
import SignUpForm from './SignUpForm';
import { useState } from 'react';
import WarnMessage from './WarnMessage';

const Password = ({ page, setPage, goPreviousPage, goNextPage, signUpInfo, setSignUpInfo }) => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [confirmPasswordMessage, setConfirmPasswordMessage] = useState('');
  const [isPassword, setIsPassword] = useState(false);
  const [isConfirmPassword, setIsConfirmPassword] = useState(false);

  const onChangePassword = useCallback((e) => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);

    if (!passwordRegex.test(passwordCurrent)) {
      setPasswordMessage('숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요.');
      setIsPassword(false);
    } else {
      setPasswordMessage('');
      setIsPassword(true);
    }
  }, []);

  const onChangeConfirmPassword = useCallback(
    (e) => {
      const confirmPasswordCurrent = e.target.value;
      setConfirmPassword(confirmPasswordCurrent);
      if (password !== confirmPasswordCurrent) {
        setConfirmPasswordMessage('비밀번호가 일치하지 않습니다.');
        setIsConfirmPassword(false);
      } else {
        setConfirmPasswordMessage('');
        setIsConfirmPassword(true);
      }
    },
    [password],
  );

  const getValidationPassword = () => {
    setSignUpInfo({ ...signUpInfo, password });
    goNextPage();
  };
  return (
    <SignUpForm page={page} setPage={setPage}>
      <div className="border-b border-[#D9D9D9] flex pb-1">
        <FontAwesomeIcon icon={faLock} className="text-waniGray text-2xl pr-3" />
        <input
          type="password"
          placeholder="Password"
          onChange={onChangePassword}
          value={password}
        />
      </div>
      <WarnMessage message={passwordMessage} />
      <div className="border-b border-[#D9D9D9] flex pb-1 mt-12">
        <FontAwesomeIcon icon={faLock} className="text-waniGray text-2xl pr-3" />
        <input
          type="password"
          placeholder="Confirm Password"
          onChange={onChangeConfirmPassword}
          value={confirmPassword}
        />
      </div>
      <WarnMessage message={confirmPasswordMessage} />
      <div>
        <button className="mt-12 float-left" onClick={goPreviousPage}>
          <FontAwesomeIcon icon={faCircleLeft} className="text-waniGreen text-3xl" />
        </button>

        {isPassword === true && isConfirmPassword === true ? (
          <button className="mt-12 float-right" onClick={getValidationPassword}>
            <FontAwesomeIcon icon={faCircleRight} className="text-waniGreen text-3xl" />
          </button>
        ) : (
          <button className="mt-12 float-right">
            <FontAwesomeIcon icon={faCircleRight} className="text-waniGray text-3xl" />
          </button>
        )}
      </div>
    </SignUpForm>
  );
};

export default Password;
