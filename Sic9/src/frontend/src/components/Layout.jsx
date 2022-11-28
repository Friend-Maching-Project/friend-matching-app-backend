import React from 'react';

const Layout = ({ children, height }) => {
  return <div className={`container mx-auto px-10 ${height}`}>{children}</div>;
};

export default Layout;
