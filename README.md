# Sistema de Assistência TV

## Instruções para instalação e uso do aplicativo

### Pré-requisitos

1. Ter o Java versão 17 ou superior instalado. Na instalação, selecione todos os recursos conforme indicado na imagem.
   [Download openJDK](https://adoptium.net/).
   
   ![openjdk](https://github.com/polianacaroline/assistenciatv/assets/104094484/e2f2dfda-ade7-4dda-87e6-4ff3591707a5)

2. Ter um banco de dados local baseado no MySQL 8 ou MariaDB compatível. No exemplo, usei o XAMPP, que pode ser obtido no link indicado.
   [Download XAMPP](https://www.apachefriends.org/)

### Instalação do banco

1. Iniciar os serviços Apache e MySQL no XAMPP, conforme indicado na imagem.
   ![xampp1](https://github.com/polianacaroline/assistenciatv/assets/104094484/6515bd96-871a-4e74-884d-61dd40a3fe01)

2. No navegador de internet, digite: `localhost/dashboard` e selecione no menu: `phpMyAdmin` conforme indicado na imagem.
   ![xampp2](https://github.com/polianacaroline/assistenciatv/assets/104094484/d3dec7f1-b919-47a0-a602-75581af70aff)

3. Crie um novo banco de dados de nome `dbsistema` (sem usar acentuação) conforme indicado na imagem.
   ![phpmyadmin1](https://github.com/thainarapires/sistemaOS/assets/104094316/776fe9c9-a4a4-449d-8a01-8ae852da243c)

4. Na aba SQL, copie e cole o código abaixo e execute. (Passos 1, 2 e 3 indicados na imagem)
   ![php2](https://github.com/thainarapires/sistemaOS/assets/104094316/823f1ef9-1df4-4bce-a6e0-ece1f3fc9aa3)

5. Confira clicando no seu banco criado (`dbsistema`) se todas as tabelas criadas estão lá.
   ![php3](https://github.com/thainarapires/sistemaOS/assets/104094316/aeaec200-1787-41ca-8db0-19d6ea2b1737)

### Instalação do aplicativo

1. Em Releases, faça o download do arquivo `SISTEMA.-.SP.Assistencia.TV.jar`

2. Execute e verifique no rodapé o ícone que representa o banco de dados conectado. Se estiver com erro (conforme indicado na figura) verifique o XAMPP e revise novamente os passos 1 a 4 da instalação do banco.
<img width="1010" height="689" alt="login-inativo" src="https://github.com/user-attachments/assets/3f375d42-499b-49dc-b2c0-ddd800b44a48" />

3. Se tudo estiver OK, você pode iniciar gerando uma busca pelo Usuário padrão.
   - Login: `admin`
   - Senha: `admin`  
   OBS: SEM OS PARÊNTESES E AS ASPAS.

## Sistema OS

### Tela Login:

Essa Tela acessa a tela principal, utilizando seu usuário.
<img width="1004" height="689" alt="login-ativo" src="https://github.com/user-attachments/assets/b1e00f85-ca84-4221-b8e7-379ed0da520d" />


### Tela Principal:
<img width="1010" height="689" alt="main" src="https://github.com/user-attachments/assets/aa681bf0-5f61-4b98-af29-fbe8a115550c" />



Essa é a tela principal, dá acesso ao administrador às seguintes janelas: Usuários, Clientes, Fornecedores, Serviços, Relatórios, Produtos. O usuário com o perfil de "user" não tem acesso a algumas janelas.

### Tela Fornecedores:

Essa Tela para adicionar cadastro dos Fornecedores.
![fornecedores](https://github.com/thainarapires/sistemaOS/assets/104094316/0feb4168-7a33-4863-bd76-381f30ebc1e0)

### Tela Clientes:

Essa Tela para adicionar cadastro dos Clientes.
<img width="1354" height="689" alt="tela-clientes" src="https://github.com/user-attachments/assets/b9ad6520-96db-46aa-9087-085397e25db8" />

### Tela Usuário:

Essa Tela para adicionar cadastro do Usuário.
![TelaUsuarios](https://github.com/polianacaroline/assistenciatv/assets/104094484/8c22a39d-69cd-42fa-9ea3-e0ff44dcead2)

### Tela Sobre:

Essa Tela contém informações sobre a licença MIT e link do GitHub.
![TelaSobres](https://github.com/polianacaroline/assistenciatv/assets/104094484/62ecc091-7456-4b00-8c80-b1225d9d1fae)

### Tela Serviços:

Essa Tela para adicionar cadastro de Ordem de Serviço.
![servicos](https://github.com/thainarapires/sistemaOS/assets/104094316/2a2d8d6b-40e6-4ea8-80b6-53bd7cf8bcd2)

### Tela Relatórios:

Essa Tela contém Relatórios de Produtos, Clientes, Serviços e Usuários.
![TelaRelatorios](https://github.com/polianacaroline/assistenciatv/assets/104094484/1c288aff-68a9-4019-8b5d-8db6e46cd724)

### Tela Produto:

Essa Tela contém um método de inserção de imagem, para adição de um Produto e leitura de Código de Barras.
![TelaProdutos](https://github.com/polianacaroline/assistenciatv/assets/104094484/83eda0d4-3758-4065-8bfc-c089a1ae5360)
