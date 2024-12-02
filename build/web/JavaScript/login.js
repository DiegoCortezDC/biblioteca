//URL BASE
function setBaseURL() {
    const URL_BASE = 'http://localhost:8080/';
    return URL_BASE;
}

const BASE_URL = setBaseURL();

function loginSucces() {
    if (!validarFormulario()) {
        return;
    }
    var usuario = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    const formData = new URLSearchParams();
    formData.append("usuario", usuario);
    formData.append("password", password);

    fetch(BASE_URL + 'biblioteca/api/login/ingresar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la solicitud: ' + response.status);
        }
        return response.json();
    })
    .then(data => {
        if (data.token) {
            localStorage.setItem('usuario', usuario);
            localStorage.setItem('token', data.token);
            localStorage.setItem('rol', data.rol);

            document.body.classList.add('slide-out-left');
            setTimeout(() => {
                switch (data.rol) {
                    case 'alumno':
                        window.location.href = BASE_URL + 'biblioteca/HTML/Libros.html';
                        break;
                    case 'admin':
                        window.location.href = BASE_URL + 'biblioteca/HTML/Usuarios.html';
                        break;
                    case 'bibliotecario':
                        window.location.href = BASE_URL + 'biblioteca/HTML/AgregarLibros.html';
                        break;
                    default:
                        console.error('Rol no reconocido:', data.rol);
                        break;
                }
            }, 1000);
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Usuario o contraseña incorrectos',
                confirmButtonText: 'OK'
            });
        }
    })
    .catch(error => {
        console.error('Error al procesar la solicitud:', error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al procesar la solicitud',
            confirmButtonText: 'OK'
        });
    });
}

function validarFormulario() {
    const usuarioLogin = document.getElementById('username').value;
    const passwordLogin = document.getElementById('password').value;

    if (usuarioLogin.trim() === '') {
        Swal.fire({
            title: 'Advertencia',
            text: 'El campo Usuario no puede estar vacío',
            icon: 'warning'
        });
        return false;
    }

    if (passwordLogin.trim() === '') {
        Swal.fire({
            title: 'Advertencia',
            text: 'El campo Password no puede estar vacío',
            icon: 'warning'
        });
        return false;
    }

    return true;
}

document.getElementById('password').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault(); 
        loginSucces(); 
    }
});
