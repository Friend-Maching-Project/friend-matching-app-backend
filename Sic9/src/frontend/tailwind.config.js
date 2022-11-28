/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      backgroundImage: {
        'google-logo': "url('../public/assets/google_logo.png')",
        'naver-logo': "url('../public/assets/naver_logo.png')",
        'kakao-logo': "url('../public/assets/kakao_logo.png')",
      },
      height: {
        100: 'calc(var(--vh, 1vh) * 100)',
      },
    },
    colors: {
      waniGreen: {
        DEFAULT: '#18580C',
      },
      waniGray: {
        DEFAULT: '#A6A6A6',
      },
      waniRed: {
        DEFAULT: '#FF5E5E',
      },
    },
  },
  plugins: [],
};
