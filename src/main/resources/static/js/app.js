const API_BASE_URL = '/api';
let postsList = document.getElementById('postsList');
let usersList = document.getElementById('usersList');
let usuarioSelect = document.getElementById('usuarioSelect');
let postContent = document.getElementById('postContent');
let charCount = document.getElementById('charCount');
let btnPost = document.getElementById('btnPost');

let users = [];
let posts = [];
let selectedUser = null;

document.addEventListener('DOMContentLoaded', function() {
    console.log('Página cargada - inicializando...');
    loadUsers();
    loadPosts();
    postContent.addEventListener('input', updateCounter);

    usuarioSelect.addEventListener('change', function() {
        selectedUser = this.value ? parseInt(this.value) : null;
        console.log('Usuario seleccionado desde dropdown:', selectedUser);
        updateCounter();
    });
});

async function loadUsers() {
    try {
        console.log('Cargando usuarios...');
        const response = await fetch(`${API_BASE_URL}/usuarios`);
        users = await response.json();
        console.log('Usuarios cargados:', users);
        showUsers();
        fillUserSelect();
    } catch (error) {
        console.error('Error cargando usuarios:', error);
        usersList.innerHTML = '<div class="error">Error cargando usuarios</div>';
    }
}

async function loadPosts() {
    try {
        const response = await fetch(`${API_BASE_URL}/posts`);
        posts = await response.json();
        console.log('Posts cargados:', posts);
        showPosts();
    } catch (error) {
        console.error('Error cargando posts:', error);
        postsList.innerHTML = '<div class="error">Error cargando posts</div>';
    }
}

function showUsers() {
    if (users.length === 0) {
        usersList.innerHTML = '<p>No hay usuarios</p>';
        return;
    }

    usersList.innerHTML = users.map(user => `
        <div class="user-item ${selectedUser === user.id ? 'selected' : ''}" 
             onclick="selectUser(${user.id})">
            <strong>${user.nombre}</strong>
            <br>
            <small>${user.email}</small>
        </div>
    `).join('');
}

function showPosts() {
    if (posts.length === 0) {
        postsList.innerHTML = '<p>No hay posts todavía. ¡Sé el primero en publicar!</p>';
        return;
    }

    postsList.innerHTML = posts.map(post => `
        <div class="post">
            <div class="post-header">
                <div class="user-avatar">
                    ${post.usuario.nombre.charAt(0).toUpperCase()}
                </div>
                <div class="user-info">
                    <div class="user-name">${post.usuario.nombre}</div>
                    <div class="user-email">${post.usuario.email}</div>
                </div>
                <div class="post-date">
                    ${formatDate(post.fechaCreacion)}
                </div>
            </div>
            <div class="post-content">
                ${post.contenido}
            </div>
        </div>
    `).join('');
}

function fillUserSelect() {
    if (users.length === 0) {
        usuarioSelect.innerHTML = '<option value="">No hay usuarios</option>';
        return;
    }

    usuarioSelect.innerHTML = '<option value="">Selecciona un usuario</option>' +
        users.map(user =>
            `<option value="${user.id}">${user.nombre} (${user.email})</option>`
        ).join('');
}

function selectUser(userId) {
    selectedUser = userId;
    usuarioSelect.value = userId;
    console.log('Usuario seleccionado desde panel:', selectedUser);
    showUsers();
    updateCounter();
}

function updateCounter() {
    const length = postContent.value.length;
    charCount.textContent = `${length}/140`;

    if (length > 130) {
        charCount.style.color = '#e0245e';
    } else if (length > 120) {
        charCount.style.color = '#ffad1f';
    } else {
        charCount.style.color = '#657786';
    }

    const isDisabled = length === 0 || length > 140 || !selectedUser;
    console.log('Estado del botón - Longitud:', length, 'Usuario:', selectedUser, 'Deshabilitado:', isDisabled);

    btnPost.disabled = isDisabled;

    if (btnPost.disabled) {
        btnPost.style.opacity = '0.6';
        btnPost.style.cursor = 'not-allowed';
    } else {
        btnPost.style.opacity = '1';
        btnPost.style.cursor = 'pointer';
    }
}

async function createPost() {
    const content = postContent.value.trim();
    const userId = selectedUser;

    console.log('Intentando crear post:', {
        contenido: content,
        usuarioId: userId,
        contenidoLength: content.length
    });

    if (!content || !userId) {
        alert('Por favor, selecciona un usuario y escribe un mensaje');
        return;
    }

    if (content.length > 140) {
        alert('El mensaje no puede tener más de 140 caracteres');
        return;
    }

    try {
        const postData = {
            contenido: content,
            usuarioId: userId
        };

        console.log('Enviando datos:', JSON.stringify(postData));

        const response = await fetch(`${API_BASE_URL}/posts`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postData)
        });

        console.log('Respuesta del servidor - Status:', response.status);

        if (response.ok) {
            const newPost = await response.json();
            console.log('Post creado exitosamente:', newPost);
            postContent.value = '';
            updateCounter();
            loadPosts();
            alert('¡Post publicado exitosamente!');
        } else {
            const errorText = await response.text();
            console.error('Error del servidor:', errorText);
            alert('Error al crear el post. Código: ' + response.status);
        }
    } catch (error) {
        console.error('Error de conexión:', error);
        alert('Error de conexión: ' + error.message);
    }
}

function createUser() {
    document.getElementById('userModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('userModal').style.display = 'none';
    document.getElementById('userName').value = '';
    document.getElementById('userEmail').value = '';
}

async function saveUser() {
    const name = document.getElementById('userName').value.trim();
    const email = document.getElementById('userEmail').value.trim();

    if (!name || !email) {
        alert('Por favor, completa todos los campos');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/usuarios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: name,
                email: email
            })
        });

        if (response.ok) {
            closeModal();
            loadUsers();
        } else {
            alert('Error al crear el usuario');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error de conexión');
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);

    if (diffMins < 1) return 'Ahora mismo';
    if (diffMins < 60) return `Hace ${diffMins} min`;
    if (diffHours < 24) return `Hace ${diffHours} h`;

    return date.toLocaleDateString('es-ES', {
        day: 'numeric',
        month: 'short',
        year: 'numeric'
    });
}

window.onclick = function(event) {
    const modal = document.getElementById('userModal');
    if (event.target === modal) {
        closeModal();
    }
}

window.crearPost = createPost;
window.crearUsuario = createUser;
window.cerrarModal = closeModal;
window.guardarUsuario = saveUser;
window.seleccionarUsuario = selectUser;