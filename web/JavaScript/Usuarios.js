
function setBaseURL() {
    const URL_BASE = 'http://localhost:8080/biblioteca/';
    return URL_BASE;
}

const BASE_URL = setBaseURL();

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

window.onload = function () {
    verificarToken();
    cargarUsuarios();
}

function cargarUsuarios() {
    fetch(BASE_URL + `api/usuario/getAllUsuarios`)
            .then(response => response.json())
            .then(data => {
                const tablaUsuarios = document.getElementById('tablaUsuarios');
                tablaUsuarios.innerHTML = ''; // Limpiar tabla

                data.forEach(usuario => {
                    const row = `
                <tr>
                    <td>${usuario.IdUsuario}</td>
                    <td>${usuario.usuario}</td>
                    <td>${usuario.password}</td>
                    <td>${usuario.Apaterno}</td>
                    <td>${usuario.Amaterno}</td>
                    <td>${usuario.nombre}</td>
                    <td>${usuario.rol}</td>
                    <td>
                        <button type="button" class="btn btn-info" onclick="seleccionarUsuario('${usuario.usuario}', '${usuario.password}', ${usuario.IdUsuario}, '${usuario.Apaterno}', '${usuario.Amaterno}', '${usuario.nombre}', '${usuario.rol}')" data-id="${usuario.IdUsuario}"><i class='bx bxs-select-multiple'></i></button>
                    </td>
                </tr>
            `;
                    tablaUsuarios.innerHTML += row;
                });
            })
            .catch(error => {
                console.error('Error al cargar usuarios:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al cargar usuarios',
                    icon: 'error'
                });
            });
}

function buscarUsuario() {
    const searchInput = document.getElementById('search-input').value.trim().toLowerCase();
    fetch(BASE_URL + `api/usuario/buscarUsuario?query=${searchInput}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const tablaUsuarios = document.getElementById('tablaUsuarios');
                tablaUsuarios.innerHTML = ''; 

                data.forEach(usuario => {
                    const row = `
                    <tr>
                        <td>${usuario.IdUsuario}</td>
                        <td>${usuario.usuario}</td>
                        <td>${usuario.password}</td>
                        <td>${usuario.Apaterno}</td>
                        <td>${usuario.Amaterno}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.rol}</td>
                        <td>
                            <button type="button" class="btn btn-info" onclick="seleccionarUsuario('${usuario.usuario}', '${usuario.password}', ${usuario.IdUsuario}, '${usuario.Apaterno}', '${usuario.Amaterno}', '${usuario.nombre}', '${usuario.rol}')" data-id="${usuario.IdUsuario}"><i class='bx bxs-select-multiple'></i></button>
                        </td>
                    </tr>
                `;
                    tablaUsuarios.innerHTML += row;
                });
            })
            .catch(error => {
                console.error('Error al buscar usuarios:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al buscar usuarios',
                    icon: 'error'
                });
            });
}


function agregarUsuario() {
    if (!validarFormulario()) {
        return;
    }

    const usuario = document.getElementById('usuario').value;
    const password = document.getElementById('password').value;
    const Apaterno = document.getElementById('Apaterno').value;
    const Amaterno = document.getElementById('Amaterno').value;
    const nombre = document.getElementById('nombre').value;
    const rol = document.getElementById('rol').value;

    fetch(BASE_URL + 'api/usuario/agregarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `usuario=${encodeURIComponent(usuario)}&password=${encodeURIComponent(password)}&Apaterno=${encodeURIComponent(Apaterno)}&Amaterno=${encodeURIComponent(Amaterno)}&nombre=${encodeURIComponent(nombre)}&rol=${encodeURIComponent(rol)}`
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    Swal.fire({
                        title: 'Éxito',
                        text: 'Usuario agregado correctamente',
                        icon: 'success'
                    }).then(() => {
                        cargarUsuarios();
                        limpiarFormulario();
                    });
                } else {
                    Swal.fire({
                        title: 'Error',
                        text: 'No se pudo agregar el usuario',
                        icon: 'error'
                    });
                }
            })
            .catch(error => {
                console.error('Error al agregar usuario:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al agregar el usuario',
                    icon: 'error'
                });
            });
}

function editarUsuario() {
    const idUsuario = obtenerIdUsuarioSeleccionado();
    if (!idUsuario) {
        console.error('No se ha seleccionado ningún usuario para editar');
        return;
    }

    if (!validarFormulario()) {
        return;
    }

    const usuario = document.getElementById('usuario').value;
    const password = document.getElementById('password').value;
    const Apaterno = document.getElementById('Apaterno').value;
    const Amaterno = document.getElementById('Amaterno').value;
    const nombre = document.getElementById('nombre').value;
    const rol = document.getElementById('rol').value;

    fetch(BASE_URL + 'api/usuario/editarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `IdUsuario=${encodeURIComponent(idUsuario)}&usuario=${encodeURIComponent(usuario)}&password=${encodeURIComponent(password)}&Apaterno=${encodeURIComponent(Apaterno)}&Amaterno=${encodeURIComponent(Amaterno)}&nombre=${encodeURIComponent(nombre)}&rol=${encodeURIComponent(rol)}`
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    Swal.fire({
                        title: 'Éxito',
                        text: 'Usuario editado correctamente',
                        icon: 'success'
                    }).then(() => {
                        cargarUsuarios();
                        limpiarFormulario();
                        localStorage.removeItem('selectedUserId');
                    });
                } else {
                    Swal.fire({
                        title: 'Error',
                        text: 'No se pudo editar el usuario',
                        icon: 'error'
                    });
                }
            })
            .catch(error => {
                console.error('Error al editar usuario:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al editar el usuario',
                    icon: 'error'
                });
            });
}

function seleccionarUsuario(usuario, password, idUsuario, Apaterno, Amaterno, nombre, rol) {
    document.getElementById('usuario').value = usuario;
    document.getElementById('password').value = password;
    document.getElementById('Apaterno').value = Apaterno;
    document.getElementById('Amaterno').value = Amaterno;
    document.getElementById('nombre').value = nombre;
    document.getElementById('rol').value = rol;
    localStorage.setItem('selectedUserId', idUsuario);
}

function obtenerIdUsuarioSeleccionado() {
    return localStorage.getItem('selectedUserId');
}

function limpiarFormulario() {
    document.getElementById('usuario').value = '';
    document.getElementById('password').value = '';
    document.getElementById('Apaterno').value = '';
    document.getElementById('Amaterno').value = '';
    document.getElementById('nombre').value = '';
    document.getElementById('rol').value = '';
}

function validarFormulario() {
    const Apaterno = document.getElementById('Apaterno').value;
    const Amaterno = document.getElementById('Amaterno').value;
    const nombre = document.getElementById('nombre').value;
    const rol = document.getElementById('rol').value;


    if (
            Apaterno.trim() === '' ||
            Amaterno.trim() === '' ||
            nombre.trim() === '' ||
            rol.trim() === ''
            ) {
        Swal.fire({
            title: 'Advertencia',
            text: 'Faltan Datos, la extensión no es numérica o el email no es válido',
            icon: 'warning'
        });
        return false;
    }

    return true;
}