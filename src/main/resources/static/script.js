const renderData = (data) => {
    const dataContainer = document.getElementById('dataContainer');

    if (data && data.length > 0) {
        data.forEach(item => {
            const dataItem = document.createElement('div');
            dataItem.textContent = `${item.id} ${item.name}`;
            dataContainer.appendChild(dataItem);
        })
    } else {
        dataContainer.textContent = 'No data Available';
    }
}

    fetch('http://localhost:8080/api/sample').then(res => res.json())
        .then(data => renderData(data))
        .catch(error => {
            console.error('Error Fetching data', error);
            const dataContainer = document.getElementById('dataContainer');
            dataContainer.textContent = 'Error Fetching Data'
        })
