import { getData, valueForExample } from "./utils.js";

const data = await valueForExample();
document.getElementById('example').innerText = data.value;
//passwords
const pwdNormal = document.getElementById('pwd-normal');
const result = document.getElementById('pwd-result');
pwdNormal.addEventListener('click', () => {
    getData("passwords", result);
});

//password/length
const pwdLongitud = document.getElementById('pwd-normal-l');
const lengthPwdNormal = document.getElementById('pwdNormalLen');
pwdLongitud.addEventListener('click', () => {
    getData(`passwords/password/${lengthPwdNormal.value}`, result);
})

//password/alphaNum
const pwdAlpha = document.getElementById('pwd-alphanum');
pwdAlpha.addEventListener('click', () => {
    getData("passwords/password/alphaNum", result);
})

//password/alphaNum/length
const pwdAlphaL = document.getElementById('pwd-alphanum-l');
const lengthAlpha = document.getElementById('alphaLength');
pwdAlphaL.addEventListener('click', () => {
    getData(`passwords/password/alphaNum/${lengthAlpha.value}`, result);
})

//passwords/num
const pwds = document.getElementById('pwds-n');
const numPwd = document.getElementById('num');
pwds.addEventListener('click', () => {
    getData(`passwords/${numPwd.value}`, result);
})

//passwords/num/length
const pwdsNL = document.getElementById('pwds-n-l');
const numsPwd = document.getElementById('nums');
const lengthPwd = document.getElementById('length');
pwdsNL.addEventListener('click', () => {
    getData(`passwords/${numsPwd.value}/${lengthPwd.value}`, result);
})