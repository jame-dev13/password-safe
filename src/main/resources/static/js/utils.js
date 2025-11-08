const BASE_URL = "/api/v1";
export const valueForExample = async () => {
    const response = await fetch(`${BASE_URL}/passwords`)
    const data = await response.json();
    return await data;
};
export const getData = async (url, dataContainer) => {
    try {
        const response = await fetch(`${BASE_URL}/${url}`);
        if (!response.ok)
            throw new Error("Fail in fetch data. Status: " + response.status);

        const data = await response.json();
        const list = document.getElementById('list');
        dataContainer.innerHTML = ''; // Usamos innerHTML porque queremos incluir <br>
        list.innerHTML = '';
        if (Array.isArray(data)) {
            list.classList.remove('d-none'); 
            list.classList.add('d-flex');
            data.forEach(d => {
                const li = document.createElement('li');
                li.className = 'list-group-item bg-dark border-secondary'; 
                const code = document.createElement('code'); 
                code.className = 'text wrap user-select-all text-decoration-underline';
                code.innerText = d.value;
                li.appendChild(code); 
                list.appendChild(li);
            });
        } else {
            list.style.display = 'none';
            dataContainer.innerText = data.value;
        }
    } catch (error) {
        alert('Error requesting data');
    }
};