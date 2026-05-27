// URL do nosso servidor local do Spring Boot
const API_BASE_URL = 'http://localhost:8080/api';

function efetuarCadastro(event) {
    // não deixa a página recarregar quando clica no botão do formulário
    event.preventDefault(); 

    // pegando os componentes de aviso da tela
    const errorBanner = document.getElementById('errorBanner');
    const successBanner = document.getElementById('successBanner');
    const botaoSubmit = document.querySelector('.btn-login');

    // limpando os avisos antigos antes de fazer uma nova tentativa
    errorBanner.style.display = 'none';
    successBanner.style.display = 'none';
    
    // trava o botão e muda o texto pro usuário saber que tá enviando
    botaoSubmit.innerText = "Processando no Servidor...";
    botaoSubmit.disabled = true;

    // montando o objeto com todos os dados digitados pra mandar pro controller
    const novoCliente = {
        nome: document.getElementById('nome').value.trim(),
        tipoCliente: document.getElementById('tipoCliente').value,
        documento: document.getElementById('documento').value.trim(),
        email: document.getElementById('email').value.trim(),
        login: document.getElementById('login').value.trim(),
        senha: document.getElementById('senha').value
    };

    // envia os dados pro nosso endpoint lá no java
    fetch(`${API_BASE_URL}/clientes/registrar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoCliente)
    })
    .then(async response => {
        // se o java aceitar o cadastro e der tudo certo
        if (response.ok) {
            successBanner.innerText = 'Conta criada com sucesso! Redirecionando para o login...';
            successBanner.style.display = 'block';
            
            // espera 2 segundos e joga o usuário pra tela de login automaticamente
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 2000);
        } 
        // se o java barrar por causa de alguma regra do service (tipo senha curta ou cpf repetido)
        else {
            // pega o texto exato do erro que o java jogou na exceção
            const erroDoJava = await response.text();
            throw new Error(erroDoJava || 'Erro interno ao tentar cadastrar o cliente.');
        }
    })
    .catch(error => {
        // se der erro, joga a mensagem no banner vermelho pra avisar o usuário
        errorBanner.innerText = error.message;
        errorBanner.style.display = 'block';
        
        // destrava o botão pro usuário poder corrigir os dados e tentar de novo
        botaoSubmit.innerText = "Finalizar Cadastro";
        botaoSubmit.disabled = false;
    });
}