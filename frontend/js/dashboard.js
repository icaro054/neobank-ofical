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
    
    // já mostra o nome dele na tela e no cartão
    document.getElementById('nomeUsuario').innerText = usuario.cliente.nome;
    document.getElementById('nomeCartao').innerText = usuario.cliente.nome.toUpperCase();

    // agora a gente bate no java pra pegar a conta usando o documento do cliente
    const documentoDoCliente = usuario.cliente.documento.trim();
    
    fetch(`${API_BASE_URL}/contas/documento/${documentoDoCliente}`)
        .then(async response => {
            if (response.ok) return response.json();
            const serverError = await response.text();
            throw new Error(`Não achamos a conta vinculada a este cliente. Servidor disse: ${serverError}`);
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
                item.innerHTML = `<span>[ ${t.tipo} ]</span> <strong>R$ ${t.valor.toFixed(2)}</strong>`;
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
    abrirModal('modalDeposito');
}

function fazerSaque() {
    abrirModal('modalSaque');
}

function fazerPix() {
    abrirModal('modalPix');
}

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

    fetch(`${API_BASE_URL}/contas/depositar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ numeroConta: contaLogada.numero, valor: valor })
    })
    .then(async response => {
        if(response.ok) {
            fecharModal('modalDeposito');
            document.getElementById('valorDeposito').value = "";
            carregarDadosDoBanco(); // Atualiza saldo e extrato
        } else {
            const erro = await response.text();
            alert("Erro: " + erro);
        }
    })
    .catch(e => alert("Erro ao conectar com servidor"));
}

function confirmarSaque() {
    const valor = document.getElementById('valorSaque').value;
    
    if(valor <= 0 || valor === "") {
        alert("Digite um valor válido para sacar.");
        return;
    }

    fetch(`${API_BASE_URL}/contas/sacar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ numeroConta: contaLogada.numero, valor: valor })
    })
    .then(async response => {
        if(response.ok) {
            fecharModal('modalSaque');
            document.getElementById('valorSaque').value = "";
            carregarDadosDoBanco();
        } else {
            const erro = await response.text();
            alert("Erro: " + erro);
        }
    })
    .catch(e => alert("Erro ao conectar com servidor"));
}

function confirmarPix() {
    const valor = document.getElementById('valorPix').value;
    const contaDestino = document.getElementById('contaDestino').value.trim();
    
    if(valor <= 0 || valor === "" || contaDestino === "") {
        alert("Preencha todos os campos corretamente.");
        return;
    }

    fetch(`${API_BASE_URL}/contas/transferir`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ contaOrigem: contaLogada.numero, contaDestino: contaDestino, valor: valor })
    })
    .then(async response => {
        if(response.ok) {
            fecharModal('modalPix');
            document.getElementById('valorPix').value = "";
            document.getElementById('contaDestino').value = "";
            carregarDadosDoBanco();
        } else {
            const erro = await response.text();
            alert("Erro: " + erro);
        }
    })
    .catch(e => alert("Erro ao conectar com servidor"));
}

function abrirPerfil() {
    const usuarioSalvo = localStorage.getItem('usuarioNeoBank');
    if(usuarioSalvo) {
        const usuario = JSON.parse(usuarioSalvo);
        document.getElementById('perfilNome').innerText = usuario.cliente.nome;
        document.getElementById('perfilDoc').innerText = usuario.cliente.documento;
        document.getElementById('perfilEmail').innerText = usuario.email;
        
        const end = usuario.cliente.endereco;
        if (end) {
            document.getElementById('perfilEndereco').innerText = `${end.rua}, ${end.numero} - ${end.bairro}\n${end.cidade} / ${end.estado}\nCEP: ${end.cep}`;
        } else {
            document.getElementById('perfilEndereco').innerText = "Não informado.";
        }
        
        abrirModal('modalPerfil');
    }
}

function excluirConta() {
    if(confirm("ATENÇÃO: Tem certeza que deseja excluir sua conta permanentemente? Esta ação não pode ser desfeita e todo o seu saldo será perdido.")) {
        const usuarioSalvo = localStorage.getItem('usuarioNeoBank');
        const usuario = JSON.parse(usuarioSalvo);
        const documento = usuario.cliente.documento;
        
        fetch(`${API_BASE_URL}/clientes/excluir/${documento}`, {
            method: 'DELETE'
        })
        .then(async response => {
            if(response.ok) {
                alert("Sua conta foi excluída com sucesso.");
                sair(); // Limpa o local storage e volta pro login
            } else {
                const erro = await response.text();
                alert("Erro ao excluir conta: " + erro);
            }
        })
        .catch(e => alert("Erro de conexão com o servidor."));
    }
}