const API_BASE_URL = 'http://localhost:8080/api';

function efetuarLogin(event) {
    event.preventDefault(); // Impede a página de recarregar

    // Capturando os elementos da tela
    const errorBanner = document.getElementById('errorBanner');
    const botaoSubmit = document.querySelector('.btn-login');

    errorBanner.style.display = 'none';
    botaoSubmit.innerText = "Autenticando no Servidor...";
    botaoSubmit.disabled = true;

    // empacota dados
    const dadosLogin = {
        login: document.getElementById('login').value.trim(),
        senha: document.getElementById('senha').value
    };

    fetch(`${API_BASE_URL}/usuarios/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosLogin)
    })
    .then(async response => {
        if (response.ok) {
            const usuarioLogado = await response.json();
            
            // Salva a sessão no navegador e vai para a tela principal
            localStorage.setItem('usuarioNeoBank', JSON.stringify(usuarioLogado));
            window.location.href = 'dashboard.html';
        } 
        // Se o Java barrar o login (Senha errada, usuário não existe, etc.)
        else {
            // Captura o texto exato da exceção que o seu Spring Boot devolveu
            const erroDoJava = await response.text();
            throw new Error(erroDoJava || 'Credenciais inválidas. Verifique os dados e tente novamente.');
        }
    })
    .catch(error => {
        
        errorBanner.innerText = error.message;
        errorBanner.style.display = 'block';
        
        // Libera o botão para o usuário tentar novamente
        botaoSubmit.innerText = "Acessar Conta";
        botaoSubmit.disabled = false;
    });
}

// Configura os botões de mostrar/ocultar senha
function setupPasswordToggles() {
    document.querySelectorAll('.toggle-password').forEach(btn => {
        btn.addEventListener('click', () => {
            const input = btn.parentElement.querySelector('input');
            if (!input) return;
            if (input.type === 'password') {
                input.type = 'text';
                btn.setAttribute('aria-label', 'Ocultar senha');
                btn.classList.add('visible');
            } else {
                input.type = 'password';
                btn.setAttribute('aria-label', 'Mostrar senha');
                btn.classList.remove('visible');
            }
        });
    });
}

// Inicializa comportamentos quando o DOM estiver pronto
document.addEventListener('DOMContentLoaded', setupPasswordToggles);