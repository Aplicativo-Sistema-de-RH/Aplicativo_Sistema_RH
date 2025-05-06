# Aplicativo de Sistema de RH

Projeto desenvolvido como parte do **Desafio 3 do Projeto Integrador (Backend)**, com foco na criação de uma aplicação para gerenciamento de um sistema de RH.

<p align="center">
 <a href="#descrição-do-projeto">Descrição</a> •
 <a href="#problema-que-o-projeto-visa-resolver">Problema</a> •
 <a href="#entidades-e-atributos">Entidades</a> •
 <a href="#funcionalidades-do-crud">Funcionalidades</a> •
 <a href="#tecnologias-utilizadas">Tecnologias</a> • 
 <a href="#testes">Testes</a> •
 <a href="#integrantes-do-grupo">Integrantes</a>
</p>

**Escopo do Projeto:** Clique [aqui]

## Descrição do Projeto

<!-- A aplicação de Fitness se trata de um sistema de cadastro e autenticação de usuários voltado para o acompanhamento da saúde física e controle de treinos personalizados. O sistema permite calcular o IMC (Índice de Massa Corporal) dos usuários, além de oferecer funcionalidades CRUD completas. -->

## Problema que o projeto visa resolver

<!-- Muitas pessoas iniciam suas rotinas de treino sem um controle real de sua saúde corporal. O projeto busca resolver a falta de organização e personalização no acompanhamento fitness, possibilitando que usuários cadastrem seus dados, acessem informações de saúde (como o IMC) e mantenham um histórico organizado para seu progresso. -->

## Entidades e Atributos

### Banco de dados (`db_rhcorp`)

### Usuario (`tb_usuario`)

- `id` — Identificador único do usuário
- `nome` — Nome completo do usuário
- `usuario` — Email do usuário
- `senha` — Senha criptografada do usuário
- `foto` - Foto do usuário
- `tipo` — Define o tipo de usuario
- `cargo_id` — Chave estrangeira para `cargo.id`
- `departamento_id` - Chave estrangeira para `departamento.id`

### Cargo (`tb_cargo`)

- `id` — Identificador único do cargo
- `nome` — Nome do cargo (ex: Analista, Gerente)
- `nivel` — Nível do cargo (Júnior, Pleno, Sênior)
- `descricao` — Descrição/responsabilidades do cargo
- `salario` — Valor do salario do cargo

### Departamento (`tb_departamento`)

- `id` — Identificador único do departamento
- `descricao` — Descrição do departamento
- `nome` — Nome do departamento (RH, TI, Financeiro)
- `andar` — Localização física dentro da empresa
- `ramal` — Número de ramal do departamento

### 📌 Relacionamentos

- cargo ↔️ usuario: 1:N
- departamento ↔️ usuario: 1:N

## Funcionalidades do CRUD

### Usuario

- Cadastrar: Cadastra um novo usuario
- Entrar: Permite o acesso do sistema ao usuario
- Listar: Visualiza todos os usuario (filtro por ID)
- Atualizar: Edita as informações de um usuario
- Calcular salário: Permite o usuario calcular automaticamente o salario com base em horas trabalhadas, bônus, descontos, etc.

### Cargo

- Criar: Cadastra um novo cargo
- Listar: Visualizar todos os cargos (filtra por ID e Titulo)
- Atualizar: Altera as informações de um cargo
- Excluir: Deleta um cargo especifico

### Departamento

- Criar: Cadastrar uma nova departamento
- Listar: Visualizar todas as departamento cadastrados (filtra por ID e por Descrição)
- Atualizar: Altera as informações de uma departamento
- Excluir: Deletar uma departamento especifica

## Tecnologias Utilizadas

| Tecnologia    | Descrição                                          |
| ------------- | -------------------------------------------------- |
| Java          | Linguagem principal do backend                     |
| Spring Boot   | Framework para desenvolvimento web                 |
| MySQL         | Banco de dados relacional                          |
| JPA/Hibernate | ORM para mapeamento objeto-relacional              |
| Maven         | Gerenciador de dependências                        |
| Insomnia      | Testes de endpoints RESTful                        |
| Trello        | Organização e gerenciamento das tarefas do projeto |

## Testes

As funcionalidades da API foram testadas utilizando o Insomnia, simulando requisições HTTP para validação de cada endpoint.

## Integrantes do Grupo

| Responsavel      | Função       | GitHub                                                      |
| ---------------- | ------------ | ----------------------------------------------------------- |
| Weslley Ferreira | Developer    | [wdwf](https://github.com/wdwf/)                            |
| Ruan Barreto     | Developer    | [BarretoRuan](https://github.com/BarretoRuan)               |
| Rodrigo Henrique | Product Owne | [RodrigoHenrikeH](https://github.com/RodrigoHenrikeH)       |
| Giulia Lopes     | Developer    | [Giulia-L-Ferreira](https://github.com/Giulia-L-Ferreira)   |
| Larissa Soares   | Developer    | [LarissaSoaresSilva](https://github.com/LarissaSoaresSilva) |
| Elisa Bicudo     | Tester       | [eblopes23](https://github.com/eblopes23)                   |

📅 Projeto executado no dia: 06/05/2025

---

📌 Observações
Este projeto é voltado para fins educacionais e representa uma solução inicial que pode ser expandida com autenticação, agendamentos, relatórios e integração com frontend no futuro.

---

Feito com muito carinho Grupo Javason's Five 💖
