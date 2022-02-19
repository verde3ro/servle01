/**
 * 
 */
console.log('Hola mundo');

fetch('/servlet01/ciudades')
	.then((response) => response.json()) //Lambda
	.then((data) => {
		if (data.estatus === 'success') {
			console.log(data);
			const ciudades = data.datos;
			const tblCiudades = document.getElementById('tblCiudades').getElementsByTagName('tbody')[0];

			ciudades.forEach(city => {
				const newRow = tblCiudades.insertRow(tblCiudades.rows.length);

				newRow.innerHTML = '<tr><td>' + city.id + '</td><td>' + city.city + '</td></tr>';
			});
		} else {
			const spanError = document.getElementById('spanError');
			spanError.innerHTML = data.mensaje;
		}
	})
	.catch((error) => console.log(error));