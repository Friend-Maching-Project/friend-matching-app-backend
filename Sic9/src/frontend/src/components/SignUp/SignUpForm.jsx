import React from 'react';
import Layout from '../Layout';

const SignUpForm = ({ children, page }) => {
  return (
    <Layout height="h-100">
      <div className="h-full flex items-center flex-col justify-center ">
        <div className="w-full">
          <div className="text-waniGreen font-bold text-4xl mb-12">Register</div>
          {children}
          {/* <div>
            {page !== 0 && (
              <button className="mt-12 float-left" onClick={goPreviousPage}>
                <FontAwesomeIcon icon={faCircleLeft} className="text-waniGreen text-3xl" />
              </button>
            )}
            {page !== 5 && (
              <button className="mt-12 float-right" onClick={goNextPage}>
                <FontAwesomeIcon icon={faCircleRight} className="text-waniGreen text-3xl" />
              </button>
            )}
          </div> */}
        </div>
      </div>
      <div className="absolute bottom-10 left-1/2 translate-x-[-50%]">{page + 1}/6</div>
    </Layout>
  );
};

export default SignUpForm;
