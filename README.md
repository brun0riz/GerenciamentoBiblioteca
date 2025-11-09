# 📘 Manual do Sistema de Biblioteca

Este projeto tem como objetivo simular o funcionamento de uma **biblioteca digital**, permitindo o **empréstimo e devolução de livros**, além do **gerenciamento de clientes** e **listas de espera automáticas**.  
O sistema foi desenvolvido em **Java**, aplicando **conceitos de POO**, **padrões de projeto** e **princípios SOLID** para garantir **organização, reusabilidade e manutenibilidade** do código.

---

## ⚙️ Funcionalidades

- Cadastro e busca de livros  
- Cadastro de clientes  
- Registro de empréstimos e devoluções  
- Lista de espera automática quando o livro está indisponível  
- Notificação ao cliente quando o livro reservado fica disponível  
- Registro de logs em arquivo para auditoria do sistema  

---

## 🧩 Padrões de Projeto Utilizados

### 🔹 **Observer**
Aplicado na comunicação entre o sistema e os clientes que aguardam um livro:
- `Cliente` atua como **Observer** (observador);
- `ListaDeEspera` atua como **Subject** (sujeito observado);
- Quando um livro é devolvido, o `Subject` notifica automaticamente o próximo `Observer` da fila.

Esse padrão garante **baixo acoplamento** entre os objetos e facilita a **extensão de novas notificações** no futuro.

---

### 🔹 **Singleton**
Aplicado na classe `Logger`, que é responsável por registrar todas as ações do sistema em um arquivo de log.  
O Singleton garante que **apenas uma instância** do `Logger` exista durante a execução, centralizando o registro e **evitando conflitos** de escrita em arquivo.

---

## 🧠 Princípios SOLID Aplicados

### **S – Single Responsibility Principle (Responsabilidade Única)**
Cada classe possui **apenas uma responsabilidade**:
- `Cliente` lida apenas com dados e notificações do cliente;
- `Livro` apenas com informações do livro;
- `RepLivros` e `RepClientes` cuidam de seus respectivos repositórios;
- `ServicoEmprestimo` gerencia empréstimos e listas de espera;
- `Logger` é responsável somente pelo registro de logs.

Isso torna o código **mais modular e de fácil manutenção**.

---


---

## 💻 Execução Básica

```java
RepLivros repLivros = new RepLivros();
RepClientes repClientes = new RepClientes();
ServicoEmprestimo servico = new ServicoEmprestimo(repLivros, repClientes);

Livro livro = new Livro("Clean Code", "Robert C. Martin");
repLivros.adicionarLivro(livro);

Cliente cliente = new Cliente("Bruno", 1);
repClientes.cadastrarCliente(cliente);

servico.emprestarLivro("Clean Code", 1);
servico.devolverLivro("Clean Code");
``

