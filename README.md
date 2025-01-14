
# Sistema de Gestão de Exames

Este projeto é um sistema de gestão de exames desenvolvido em Java, utilizando Struts 2 para o framework MVC e JDBC para comunicação direta com o banco de dados. O objetivo principal do sistema é gerenciar exames, funcionários e a associação entre eles, além de oferecer funcionalidades adicionais como geração de relatórios em PDF e autenticação de usuários


## Funcionalidades

CRUD de Funcionários:

Adicionar, listar, atualizar e deletar funcionários.
Campos obrigatórios: código e nome do funcionário.
CRUD de Exames:

Adicionar, listar, atualizar e deletar exames.
Campos obrigatórios: nome do exame e status (ativo/inativo).
Filtro para listar exames ativos ou inativos.
Associação de Exames a Funcionários:

Registrar a realização de exames por funcionários, incluindo data de realização.
Não permitir deletar exame se associado a registros de exames realizados.
Não permitir cadastrar exames realizados duplicados para o mesmo funcionário na mesma data.
Gerenciamento de Sessão:

Autenticação de usuários com redirecionamento para a página de login se não autenticado.
Controle de tempo de inatividade da sessão, com redirecionamento para login após expiração.
Relatórios:

Geração de relatórios de exames realizados dentro de um período, com exportação para formatos PDF.
WebService SOAP:

Implementação de um WebService SOAP para operações CRUD de exames.
Autenticação via usuário/senha para acesso ao WebService.
Transações e Manutenção de Banco de Dados:

Operações transacionais para garantir a consistência dos dados.
Execução de consultas e atualizações de forma segura e eficiente.


## Arquitetura e Padrões de Design

Arquitetura
O sistema segue a arquitetura Model-View-Controller (MVC) utilizando Struts 2 como framework para organizar a aplicação.

O padrão DAO encapsula o acesso ao banco de dados, fornecendo uma interface abstrata para várias operações de banco de dados. Ele separa a lógica de persistência de dados do resto da aplicação, facilitando a manutenção e evolução do código.

JDBC é uma API do Java que permite a execução de operações sobre um banco de dados. Ele fornece métodos para se conectar ao banco, executar consultas SQL e manipular os dados. No contexto deste sistema, JDBC é usado para todas as interações com o banco de dados MySQL, sem a utilização de frameworks ORM (Object-Relational Mapping) como Hibernate.

Interceptores como AuthenticationInterceptor e SessionTimeoutInterceptor são utilizados para gerenciar a autenticação e a expiração da sessão do usuário.

## Instalação

Configurar Banco de Dados:

Crie o banco de dados MySQL e configure as tabelas necessárias.
Configurar Servidor de Aplicações:

Configure o JBoss WildFly para deploy da aplicação.
Compilar e Deploy:

Compile o projeto utilizando sua IDE de preferência (ex: Eclipse).
Faça o deploy da aplicação no JBoss WildFly.
Configurar Conexão com Banco de Dados:

Ajuste as configurações de conexão com o banco de dados no arquivo de configuração adequado.


## Screenshots

![App Screenshot](https://i.postimg.cc/hPybJWJY/login.png)

![App Screenshot](https://i.postimg.cc/SNNfNNK8/tabela.png/login.png/468x300?text=App+Screenshot+Here)
## 🚀 Sobre mim
Analista de sistemas | Desenvolvedor Back-end | JAVA | Spring | Banco de dados | SQL | DBA
## 🔗 Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/denilson-ferreira-dos-santos-2a994b215/)


