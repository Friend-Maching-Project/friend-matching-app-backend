import React from 'react';

const Layout = ({ children, height }) => {
  return (
    <div
      className={`container mx-auto px-10 ${height} sm:max-w-[425px] sm:min-w-[425px] sm:max-h-[896px] sm:border-2 `}
    >
      {children}
    </div>
  );
};

export default Layout;
