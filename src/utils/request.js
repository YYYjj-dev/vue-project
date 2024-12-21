import axios from 'axios';

const request = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
});

request.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        console.error('响应错误:', error);
        return Promise.reject(error);
    }
);

export default request;