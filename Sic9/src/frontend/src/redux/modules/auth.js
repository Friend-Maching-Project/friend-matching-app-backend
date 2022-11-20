import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

export const signUp = createAsyncThunk(
  'POST/SIGNUP',
  async ({ email, password, nickname, department, age, sex }) => {
    return axios
      .post('/auth/signup', {
        email,
        password,
        nickname,
        department,
        age,
        sex,
      })
      .then((res) => res.data);
  },
);
export const login = createAsyncThunk('POST/LOGIN', async ({ email, password }) => {
  return axios
    .post('/auth/login', {
      email,
      password,
    })
    .then((res) => res.data);
});

export const getUser = createAsyncThunk('GET/MEMBER/ME', async (token) => {
  return axios
    .get('/user/me', {
      headers: {
        Authorization: 'Bearer ' + token,
      },
    })
    .then((res) => res.data);
});

export const changeNickname = createAsyncThunk(
  '/POST/MEMBER/NICKNAME',
  async ({ email, nickname, token }) => {
    return axios
      .post(
        '/member/nickname',
        {
          email,
          nickname,
        },
        {
          headers: {
            Authorization: 'Bearer ' + token,
          },
        },
      )
      .then((res) => res.data);
  },
);

export const changePassword = createAsyncThunk(
  '/POST/MEMBER/PASSWORD',
  async ({ exPassword, newPassword, token }) => {
    return axios
      .post(
        '/member/password',
        {
          exPassword,
          newPassword,
        },
        {
          headers: {
            Authorization: 'Bearer ' + token,
          },
        },
      )
      .then((res) => res.data);
  },
);
const calculateRemainingTime = (expirationTime) => {
  const currentTime = new Date().getTime();
  const adjExpirationTime = new Date(expirationTime).getTime();
  const remainingDuration = adjExpirationTime - currentTime;
  return remainingDuration;
};

export const logoutActionHandler = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('expirationTime');
};
export const loginTokenHandler = (token, expirationTime) => {
  localStorage.setItem('token', token);
  localStorage.setItem('expirationTime', expirationTime);

  const remainingTime = calculateRemainingTime(expirationTime);
  return remainingTime;
};

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    token: '',
  },
  reducers: {
    removeToken: (state) => {
      state.token = '';
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.fulfilled, (state, action) => {
        state.token = action.payload.accessToken;
        state.expirationTime = action.payload.tokenExpiresIn;
      })
      .addCase(getUser.fulfilled, (state, action) => {
        state.user = action.payload;
      })
      .addCase(changeNickname.fulfilled, (state, action) => {
        state.user = action.payload;
      });
  },
});

export default authSlice;
