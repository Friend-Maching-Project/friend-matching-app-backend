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
    },
  },
  plugins: [],
};
