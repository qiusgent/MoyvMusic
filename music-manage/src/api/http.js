import router from "../router";
import axios from "axios";

axios.defaults.timeout = 5000; //超时时间5秒
axios.defaults.withCredentials = true; //允许跨域

// Content-Type 响应头(后台规定)
axios.defaults.headers.post["Content-Type"] =
  "application/x-www-form-urlencoded;charset=UTF-8";

// 基础url
axios.defaults.baseURL = "http://localhost:8888";

// 响应拦截器        响应
axios.interceptors.response.use(
  (response) => {
    //response里status = 200 成功
    if (response.status == 200) {
      // 异步执行 承诺
      return Promise.resolve(response);
    } else {
      // 不执行       拒绝
      return Promise.reject(response);
    }
  },
  (error) => {
    if (error.response.status) {
      switch (error.response.status) {
        case 401: //未登录
          router.replace({
            //跳到根路径
            path: "/",
            //查询
            query: {
              //获取当前页面的完整路径
              redirect: router.currentRoute.fullPath,
            },
          });
          break;
        case 404: //未找到
          break;
      }
      return Promise.reject(error.response);
    }
  }
);

// 封装get方法
export function get(url, pararms = {}) {
  return new Promise((resolve, reject) => {
    axios
      .get(url, { params: pararms })
      //处理 Promise 解决（resolve）后的结果。
      .then((response) => {
        resolve(response.data);
      })
      //处理 Promise 被拒绝（reject）时的情况。
      .catch((err) => {
        reject(err);
      });
  });
}

//封装post方法
export function post(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios
      .post(url, data)
      //处理 Promise 解决（resolve）后的结果。
      .then((response) => {
        resolve(response.data);
      })
      //处理 Promise 被拒绝（reject）时的情况。
      .catch((err) => {
        reject(err);
      });
  });
}
