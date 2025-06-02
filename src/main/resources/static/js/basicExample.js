import { valueForExample } from "./utils.js";
const data = await valueForExample();
document.getElementById('example').innerText = data.value;