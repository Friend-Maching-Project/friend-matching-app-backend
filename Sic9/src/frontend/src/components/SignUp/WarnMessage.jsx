import React from 'react';

const WarnMessage = ({ message }) => {
  return (
    <div>
      {message === '' ? (
        <div className="invisible">O</div>
      ) : (
        <div className="text-waniRed">{message}</div>
      )}
    </div>
  );
};

export default WarnMessage;
