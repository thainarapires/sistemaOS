Login de aplicativo para desktop (Windows, Linux ou MAC) de repositório de informações para acesso com o Usuário.

![TelaLogin](https://github.com/polianacaroline/assistenciatv/assets/104094484/b45e448f-f457-45f9-8d00-3270b57ad535)
# Instruções para instalação e uso do aplicativo
## Pré requisitos 

 1. Ter o Java versão 17 ou superior instalado. Na instalação selecione todos os recursos conforme indicado na imagem.
[Download openJDK](https://adoptium.net/).

![openjdk](https://github.com/polianacaroline/assistenciatv/assets/104094484/e2f2dfda-ade7-4dda-87e6-4ff3591707a5)

 2. Ter um banco de dados local baseado no MySQL 8 ou MariaDB compatível, no exemplo usei o XAMPP que pode ser obtido no link indicado.
[Download XAMPP](https://www.apachefriends.org/)

# Instalação do banco

 1. Iniciar os serviços Apache e MySQL no XAMPP, conforme indicado na imagem.
    
![xampp1](https://github.com/polianacaroline/assistenciatv/assets/104094484/6515bd96-871a-4e74-884d-61dd40a3fe01)

 2. No navegador de internet digite: localhost/dashboard e selecione no menu: phpMyAdmin conforme indicado na imagem.
    
![xampp2](https://github.com/polianacaroline/assistenciatv/assets/104094484/d3dec7f1-b919-47a0-a602-75581af70aff)

 3. Crie um novo banco de dados de nome dbcarometro (sem usar acentuação) conforme indicado na imagem.
  
(https://github.com/polianacaroline/assistenciatv/assets/104094484/877b46ff-bb18-4e0c-b8e2-504ef6a763d1)
![phpmyadmin1](https://github.com/thainarapires/sistemaOS/assets/104094316/776fe9c9-a4a4-449d-8a01-8ae852da243c)

 4. Na aba SQL, copie e cole o código abaixo e execute. (Passos 1,2 e 3 indicados na imagem)
    
``` CREATE TABLE alunos (ra int PRIMARY KEY AUTO_INCREMENT,nome varchar(30) NOT NULL,foto LONGBLOB NOT NULL);```
    
![xampp4](https://github.com/polianacaroline/assistenciatv/assets/104094484/ead02bda-f5b3-4487-a6fa-04d9c1e2408b)

# Instalação do aplicativo
 1. Em Releases faça o download do arquivo Login.jar

 2. Execute e verifique no rodapé o ícone que representa o banco de dados conectado. Se estiver com erro (conforme indicado na figura) verifique o XAMPP e revise novamente os passos 1 a 4 da instalação do banco.

![desligado](https://github.com/polianacaroline/assistenciatv/assets/104094484/9da121ec-c8c7-43c1-9d1a-9d2101765403)

 3. Se tudo estiver OK você pode iniciar gerando uma busca pelo Usuário padrão.
    
    Login: ("admin")
    
    Senha: ("admin")


# Sistema OS

Tela Login:

Essa Tela acessa a tela principal, ultilizando seu usuário.

![TelaLogin](https://github.com/polianacaroline/assistenciatv/assets/104094484/b45e448f-f457-45f9-8d00-3270b57ad535)


                
Tela Principal:

Essa Tela acessa os Usuário, Clientes, Fornecedores, Serviços, Relatório, Produtos.

![TelaPrincipal](https://github.com/polianacaroline/assistenciatv/assets/104094484/99a8cb0c-52c6-446b-a5c3-b55d5231b598)

Tela Fornecedores:

Essa Tela para adicionar cadastro dos Fornecedores.

![TelaFornecedores ](https://github.com/polianacaroline/assistenciatv/assets/104094484/c48c8575-47a8-4e6a-83f1-b00f9b0a7f92)

Tela Clientes:

Essa Tela para adicionar cadastro dos Clientes.

![TelaClientes](https://github.com/polianacaroline/assistenciatv/assets/104094484/bd285d6b-3009-4c92-aa83-ce8269bff729)

Tela Usuário:

Essa Tela para adicionar cadastro  do Usuário.

![TelaUsuarios](https://github.com/polianacaroline/assistenciatv/assets/104094484/8c22a39d-69cd-42fa-9ea3-e0ff44dcead2)

Tela Sobre:

Essa Tela contem informações sobre licença MIT e link do GitHub. 

![TelaSobres](https://github.com/polianacaroline/assistenciatv/assets/104094484/62ecc091-7456-4b00-8c80-b1225d9d1fae)

Tela Serviço:

Essa Tela para adicionar cadastro de Ordem de Serviço.

![TelaServicos](https://github.com/polianacaroline/assistenciatv/assets/104094484/4d8c6a77-2d28-4127-84ba-bd9cb1d82ad7)

Tela Relatório:

Essa Tela contem Relatorio de Produtos, Clientes, Serviços e Usuários.

![TelaRelatorios](https://github.com/polianacaroline/assistenciatv/assets/104094484/1c288aff-68a9-4019-8b5d-8db6e46cd724)

Tela Produto: 

Essa Tela contem um método de inserção de imagem, para adição de um Produto e leitura de Código de Barras. 

![TelaProdutos](https://github.com/polianacaroline/assistenciatv/assets/104094484/83eda0d4-3758-4065-8bfc-c089a1ae5360)

