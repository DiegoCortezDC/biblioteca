function setBaseURL() {
    const URL_BASE = 'http://localhost:8080/biblioteca/';
    return URL_BASE;
}

const BASE_URL = setBaseURL();

document.getElementById('searchBtn').addEventListener('click', function () {
    const query = document.getElementById('searchInput').value;
    fetchBooks(query);
});


document.getElementById('clearBtn').addEventListener('click', function () {
    clearResults();
});

function clearResults() {
    const resultsContainer = document.getElementById('resultsContainer');
    resultsContainer.innerHTML = '';
    document.getElementById('searchInput').value = '';
}

document.addEventListener('DOMContentLoaded', function () {
    const logoutBtn = document.getElementById('logoutBtn');

    if (logoutBtn) {
        logoutBtn.addEventListener('click', function (event) {
            event.preventDefault();

            Swal.fire({
                title: '¿Estás seguro de que deseas salir?',
                text: 'Tu sesión actual se cerrará',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, salir',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    cerrarSesion();
                }
            });
        });
    }
});

function verificarToken() {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = BASE_URL + 'index.html';
    }
}

function cerrarSesion() {
    const usuario = localStorage.getItem('usuario');
    const token = localStorage.getItem('token');

    if (!usuario || !token) {
        console.error('No se encontraron usuario o token en el localStorage');
        return;
    }

    fetch(BASE_URL + 'api/login/cerrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'usuario=' + encodeURIComponent(usuario) + '&token=' + encodeURIComponent(token)
    })
            .then(response => {
                if (response.ok) {
                    localStorage.removeItem('usuario');
                    localStorage.removeItem('token');
                    localStorage.removeItem('rol');
                    Swal.fire({
                        title: 'Sesión cerrada',
                        text: 'exitosamente',
                        icon: 'success'
                    }).then(() => {
                        window.location.href = BASE_URL + '/index.html';
                    });
                } else {
                    throw new Error('Error al cerrar sesión');
                }
            })
            .catch(error => {
                console.error(error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al cerrar sesión',
                    icon: 'error'
                });
            });
}

document.getElementById('viewAllBtn').addEventListener('click', function () {
    fetchAllBooks();
});

async function fetchAllBooks() {
    try {
        const response = await fetch(`${BASE_URL}api/libro/getLibrosTodos`);
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.status}`);
        }
        const data = await response.json();
        displayResults(data);
    } catch (error) {
        console.error('Error fetching all books:', error);
    }
}


async function fetchAllBooks() {
    try {
        const response = await fetch(`${BASE_URL}api/libro/getLibrosTodos`);
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.status}`);
        }
        const data = await response.json();
        displayResults(data);
    } catch (error) {
        console.error('Error fetching all books:', error);
        alert("Error al obtener todos los libros. Por favor, intenta nuevamente.");
    }
}
async function fetchBooks(query) {
    try {
        const response = await fetch(`${BASE_URL}api/libro/buscarLibroPorNombre/${encodeURIComponent(query)}`);
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.status}`);
        }
        const data = await response.json();
        displayResults(data);
    } catch (error) {
        console.error('Error fetching books:', error);
        alert("Error al buscar libros. Por favor, intenta nuevamente.");
    }
}


function displayResults(books) {
    const resultsContainer = document.getElementById('resultsContainer');
    resultsContainer.innerHTML = '';

    if (books.length === 0) {
        resultsContainer.innerHTML = '<p>No se encontraron resultados.</p>';
        return;
    }

    books.forEach(libro => {
        const resultItem = document.createElement('div');
        resultItem.className = 'result-item';

        const previewOnClick = `previewPDF('${libro.nombreL}', '${libro.pdf}')`;

        resultItem.innerHTML = `
            <h3>${libro.nombreL}</h3>
            <p>Autor: ${libro.autor}</p>
            <p>Género: ${libro.genero}</p>
            <p>Universidad: ${libro.universidad}</p>
            <button onclick="${previewOnClick}">Ver Documento</button>
        `;
        resultsContainer.appendChild(resultItem);
    });
}

function previewPDF(bookName, base64) {
    if (base64.startsWith("data:application/pdf;base64,")) {
        base64 = base64.replace("data:application/pdf;base64,", "");
    }

    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);

    const blob = new Blob([byteArray], { type: 'application/pdf' });
    const fileURL = URL.createObjectURL(blob);

    const modal = document.getElementById('pdfModal');
    const pdfPreviewModal = document.getElementById('pdfPreviewModal');
    const modalTitle = document.getElementById('pdfModalTitle');

    modalTitle.textContent = `Vista previa del Documento: ${bookName}`;
    pdfPreviewModal.src = fileURL;
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById('pdfModal');
    const pdfPreviewModal = document.getElementById('pdfPreviewModal');

    pdfPreviewModal.src = '';
    modal.style.display = "none";
}


window.onclick = function (event) {
    const modal = document.getElementById('pdfModal');
    if (event.target == modal) {
        closeModal();
    }

}



