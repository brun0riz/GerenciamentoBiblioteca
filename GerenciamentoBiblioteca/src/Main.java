import Biblioteca.Cliente;
import Biblioteca.Livro;
import Servicos.RepClientes;
import Servicos.RepLivros;
import Servicos.ServicoEmprestimo;

/**
 * Classe principal para testar o sistema da Biblioteca.
 * Simula todos os casos de uso definidos:
 * 1. Cadastro
 * 2. Empréstimo com sucesso
 * 3. Tentativa de empréstimo (livro indisponível) -> Entra na Fila 1
 * 4. Tentativa de empréstimo (livro indisponível) -> Entra na Fila 2
 * 5. Devolução -> Notifica Fila 1
 * 6. Empréstimo (Fila 1) -> Pega o livro
 * 7. Devolução -> Notifica Fila 2
 * 8. Empréstimo (Fila 2) -> Pega o livro
 * 9. Devolução -> Lista fica vazia
 * 10. Casos de Erro (livro/cliente não existem)
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("=== 🚀 INICIANDO SIMULAÇÃO DA BIBLIOTECA ===");
        System.out.println("==================================================");

        // --- 1. CONFIGURAÇÃO (SETUP) ---
        // Aqui aplicamos o SRP (Princípio da Responsabilidade Única)
        // e o Singleton (Logger é instanciado dentro dos serviços)
        RepLivros repoLivros = new RepLivros();
        RepClientes repoClientes = new RepClientes();

        // Aqui aplicamos a Injeção de Dependência (DI)
        ServicoEmprestimo servicoEmp = new ServicoEmprestimo(repoLivros, repoClientes);

        System.out.println("\n--- 1. CADASTRANDO LIVROS E CLIENTES ---");
        repoLivros.adicionarLivro(new Livro("O Hobbit", "J.R.R. Tolkien"));
        repoLivros.adicionarLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien"));

        // Nossos Clientes (Observers)
        Cliente aragorn = new Cliente("Aragorn", 101);
        Cliente gandalf = new Cliente("Gandalf", 102);
        Cliente bilbo = new Cliente("Bilbo", 103);

        repoClientes.cadastrarCliente(aragorn);
        repoClientes.cadastrarCliente(gandalf);
        repoClientes.cadastrarCliente(bilbo);

        System.out.println("\n--- 2. TESTANDO CASOS DE ERRO (Livro/Cliente não existem) ---");
        servicoEmp.emprestarLivro("A Sociedade do Anel", 101); // Livro não existe
        servicoEmp.emprestarLivro("O Hobbit", 999); // Cliente não existe

        System.out.println("\n--- 3. TESTE DE EMPRÉSTIMO (Sucesso) ---");
        // Aragorn pega "O Hobbit".
        servicoEmp.emprestarLivro("O Hobbit", 101);

        System.out.println("\n--- 4. TESTE DA LISTA DE ESPERA (OBSERVER) ---");
        // Gandalf tenta pegar. Livro está com Aragorn.
        // Gandalf deve ser o 1º da fila.
        servicoEmp.emprestarLivro("O Hobbit", 102);

        // Bilbo tenta pegar. Livro está com Aragorn.
        // Bilbo deve ser o 2º da fila.
        servicoEmp.emprestarLivro("O Hobbit", 103);

        System.out.println("\n--- 5. TESTE DE DEVOLUÇÃO (Notifica o 1º da Fila) ---");
        // Aragorn devolve. O sistema deve notificar APENAS Gandalf.
        servicoEmp.devolverLivro("O Hobbit");
        // (A notificação para Gandalf deve aparecer no console)

        System.out.println("\n--- 6. TESTE DE EMPRÉSTIMO (1º da Fila Pega) ---");
        // Gandalf (que foi notificado) vai e pega o livro.
        servicoEmp.emprestarLivro("O Hobbit", 102);

        // Bilbo (que é o próximo da fila) tenta pegar, mas o livro já está com Gandalf.
        // Isso é um teste importante: ele não deveria conseguir e nem ser removido da fila.
        // Como ele já está na fila, o sistema apenas loga o aviso.
        servicoEmp.emprestarLivro("O Hobbit", 103);

        System.out.println("\n--- 7. TESTE DE DEVOLUÇÃO (Notifica o 2º da Fila) ---");
        // Gandalf devolve. O sistema deve notificar APENAS Bilbo.
        servicoEmp.devolverLivro("O Hobbit");
        // (A notificação para Bilbo deve aparecer no console)

        System.out.println("\n--- 8. TESTE DE EMPRÉSTIMO (2º da Fila Pega) ---");
        // Bilbo (que foi notificado) vai e pega o livro.
        servicoEmp.emprestarLivro("O Hobbit", 103);

        System.out.println("\n--- 9. TESTE DE DEVOLUÇÃO (Lista Fica Vazia) ---");
        // Bilbo devolve. Não há mais ninguém na fila.
        // A lista de espera deve ser removida do Map.
        servicoEmp.devolverLivro("O Hobbit");

        System.out.println("\n--- 10. TESTE DE ERRO (Devolução Dupla) ---");
        // Tentar devolver um livro que já foi devolvido.
        servicoEmp.devolverLivro("O Hobbit");


        System.out.println("\n==================================================");
        System.out.println("=== ✅ SIMULAÇÃO FINALIZADA ===");
        System.out.println("=== Verifique o arquivo biblioteca_log.txt ===");
        System.out.println("==================================================");
    }
}