/**
Sistema para gestao de OS
@author Thainara Pires
*/

create database dbsistema;
use dbsistema;


create table usuarios (
id int primary key auto_increment,
nome varchar(50) not null,
login varchar(15) not null unique,
senha varchar(250) not null,
perfil varchar(10) not null
 );

insert into usuarios(nome, login, senha, perfil) values ('Administrador', 'admin', md5('admin'), 'Admin');
insert into usuarios(nome, login, senha, perfil) values ('Usuário', 'usuario', md5('usuario'), 'User');

 create table clientes (
idcli int primary key auto_increment,
nome varchar(50) not null,
cpf varchar(11),
rg varchar(9),
cnpj varchar(14),
endereco varchar(35) not null,
numero varchar(10) not null,
complemento varchar(20),
bairro varchar(50) not null,
cep varchar(20),
cidade varchar(30) not null,
uf char(2),
fone varchar(12),
email varchar(50)
 ); 
 

create table servicos (
os int primary key auto_increment,
dataOS timestamp default current_timestamp,
equipamento varchar(200) not null,
marca varchar(45),
modelo varchar(45),
serie varchar(45),
defeito varchar(200),
valor decimal(10,2), 
idcli int not null,
material varchar(105),
foreign key (idcli) references clientes(idcli)
);

create table fornecedores (
idfornecedor int primary key auto_increment,
razaosocial varchar(50),
nomefantasia varchar(50),
cnpj varchar(14) unique,
logradouro varchar(35),
numero varchar(10),
complemento varchar(20),
cep varchar(20),
bairro varchar(50) not null,
referencia varchar(50),
cidade varchar(30) not null,
uf char(2),
telefone varchar(12),
celular varchar(12),
email varchar(50),
site varchar(45),
vendedor varchar(45),
ie varchar(45)
 ); 

create table produtos (
codigo int primary key auto_increment,
barcode varchar(100) unique,
descricao varchar(100),
foto longblob,
estoque int,
estoquemin int,
valor decimal(10,2),
medida char(2),
armazenagem varchar(50),
idfornecedor int,
nome varchar(100),
lote varchar(20),
fabricante varchar(20),
lucro decimal(10,2),
dataent timestamp default current_timestamp,
dataval date,
codigobarras varchar (100),
razaosocial varchar(50),
foreign key (idfornecedor) references fornecedores(idfornecedor)
); 
