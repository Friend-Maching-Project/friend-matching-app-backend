import { faCircleLeft, faCircleRight, faUserLarge } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useCallback } from 'react';
import { useState } from 'react';
import SignUpForm from './SignUpForm';
import { useRef } from 'react';
import moment from 'moment/moment';
import WarnMessage from './WarnMessage';

const Birth = ({ page, setPage, goPreviousPage, goNextPage, signUpInfo, setSignUpInfo }) => {
  const [birth, setBirth] = useState('');
  const [birthMessage, setBirthMessage] = useState('');
  const [isBirth, setIsBirth] = useState(false);
  const birthRef = useRef();
  const nowYear = moment().format('YYYY');

  const handleBirth = useCallback((e) => {
    const value = birthRef.current.value.replace(/\D+/g, '');
    const birthLength = 8;
    let result = '';

    for (let i = 0; i < value.length && i < birthLength; i++) {
      switch (i) {
        case 4:
          result += '/';
          break;
        case 6:
          result += '/';
          break;
        default:
          break;
      }
      result += value[i];
    }
    birthRef.current.value = result;
    setBirth(e.target.value);

    const birthValue = e.target.value;
    if (birthValue.length !== 10) {
      setBirthMessage('양식에 맞춰 입력해주세요.');
      setIsBirth(false);
    } else {
      setBirthMessage('');
      setIsBirth(true);
    }
  }, []);

  const getBirthValidation = () => {
    const year = birth.substring(0, 4);
    const month = parseInt(birth.substring(5, 7));
    const day = birth.substring(8, 10);
    if (year < 1900 || year > nowYear) {
      setBirthMessage('올바른 연도를 입력해주세요.');
    } else if (month < 1 || month > 12) {
      setBirthMessage('올바른 월을 입력해주세요.');
    } else {
      if (
        month === 1 ||
        month === 3 ||
        month === 5 ||
        month === 7 ||
        month === 8 ||
        month === 10 ||
        month === 12
      ) {
        if (day < 1 || day > 31) {
          setBirthMessage('올바른 일을 입력해주세요.');
        } else {
          setSignUpInfo({ ...signUpInfo, birth: birth.replaceAll('/', '-') });
          goNextPage();
        }
      } else if (month === 4 || month === 6 || month === 9 || month === 11) {
        if (day < 1 || day > 30) {
          setBirthMessage('올바른 일을 입력해주세요.');
        } else {
          setSignUpInfo({ ...signUpInfo, birth: birth.replaceAll('/', '-') });
          goNextPage();
        }
      }
    }
  };
  return (
    <SignUpForm page={page} setPage={setPage}>
      <div className="border-b border-[#D9D9D9] flex pb-1">
        <FontAwesomeIcon icon={faUserLarge} className="text-waniGray text-2xl pr-3" />
        <input
          type="num"
          placeholder="YYYY/MM/DD"
          value={birth}
          ref={birthRef}
          onChange={handleBirth}
        />
      </div>
      <WarnMessage message={birthMessage} />
      <div>
        <button className="mt-12 float-left" onClick={goPreviousPage}>
          <FontAwesomeIcon icon={faCircleLeft} className="text-waniGreen text-3xl" />
        </button>
        {isBirth === true ? (
          <button className="mt-12 float-right" onClick={getBirthValidation}>
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

export default Birth;
