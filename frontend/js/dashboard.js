const API_BASE_URL = 'http://localhost:8080/api';
let contaLogada = null; // guarda a conta pra usar nos botões de saque e deposito

// essa função roda sozinha assim que a página html termina de carregar
window.onload = function() {
    carregarDadosDoBanco();
};

function carregarDadosDoBanco() {
    // puxa aquele usuário que a gente salvou no localStorage na hora do login
    const usuarioSalvo = localStorage.getItem('usuarioNeoBank');
    
    // se o cara tentar entrar na tela direto pelo link sem logar, a gente chuta ele pro login
    if (!usuarioSalvo) {
        window.location.href = 'index.html';
        return;
    }

    // transforma o texto do localStorage de volta num objeto javascript
    const usuario = JSON.parse(usuarioSalvo);
    
    // já mostra o nome dele na tela
    document.getElementById('nomeUsuario').innerText = usuario.cliente.nome;

    // agora a gente bate no java pra pegar a conta usando o documento do cliente
    const documentoDoCliente = usuario.cliente.documento;
    
    fetch(`${API_BASE_URL}/contas/documento/${documentoDoCliente}`)
        .then(response => {
            if (response.ok) return response.json();
            throw new Error('Não achamos a conta vinculada a este cliente.');
        })
        .then(conta => {
            // deu certo! salva a conta e atualiza a tela
            contaLogada = conta;
            
            // formata o saldo pra ficar bonitão com virgula
            const saldoFormatado = conta.saldo.toLocaleString('pt-BR', { minimumFractionDigits: 2 });
            document.getElementById('saldoConta').innerText = `R$ ${saldoFormatado}`;
            
            document.getElementById('infoConta').innerText = `Conta: ${conta.numero} | Agência: ${conta.agencia.numero}`;
            
            // depois de pegar a conta, já chama a função pra carregar o extrato
            carregarExtrato(conta.numero);
        })
        .catch(erro => {
            alert("Aviso: " + erro.message);
        });
}

function carregarExtrato(numeroDaConta) {
    // bate na api pra puxar a lista de transações
    fetch(`${API_BASE_URL}/transacoes/extrato/${numeroDaConta}`)
        .then(response => response.json())
        .then(transacoes => {
            const lista = document.getElementById('listaExtrato');
            lista.innerHTML = ''; // limpa a mensagem de "carregando"

            if (transacoes.length === 0) {
                lista.innerHTML = '<li>Nenhuma transação recente.</li>';
                return;
            }

            // pra cada transação que veio do java, cria uma linha (<li>) no html
            transacoes.forEach(t => {
                const item = document.createElement('li');
                item.innerHTML = `<span>${t.tipo}</span> <strong>R$ ${t.valor.toFixed(2)}</strong>`;
                lista.appendChild(item);
            });
        })
        .catch(erro => {
            document.getElementById('listaExtrato').innerHTML = '<li>Erro ao carregar extrato.</li>';
        });
}

function sair() {
    // limpa o acesso e volta pro login
    localStorage.removeItem('usuarioNeoBank');
    window.location.href = 'index.html';
}

function fazerDeposito() {
    alert("Vamos ligar a rota de depósito do Controller em seguida!");
}

function fazerSaque() {
    alert("Vamos ligar a rota de saque do Controller em seguida!");
}

function fazerPix() {
    alert("Vamos ligar a rota de transferência do Controller em seguida!");
}
// Funções pra controlar as janelinhas (modais) na tela
function abrirModal(idModal) {
    document.getElementById(idModal).style.display = 'flex';
}

function fecharModal(idModal) {
    document.getElementById(idModal).style.display = 'none';
}

function confirmarDeposito() {
    const valor = document.getElementById('valorDeposito').value;
    
    if(valor <= 0 || valor === "") {
        alert("Digite um valor válido para depositar.");
        return;
    }

    // AQUI vai entrar o nosso código pra avisar o Spring Boot!
    alert("Pronto pra enviar R$ " + valor + " pro Java!");
    fecharModal('modalDeposito');
    document.getElementById('valorDeposito').value = ""; 
}