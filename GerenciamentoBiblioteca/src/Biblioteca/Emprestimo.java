package Biblioteca;

import java.time.LocalDate;

public class Emprestimo {
    private Livro livro;
    private Cliente cliente;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao; // Pode ser null enquanto estiver emprestado

    public Emprestimo(Livro livro, Cliente cliente) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = LocalDate.now();
        this.livro.setDisponivel(false); // Marca o livro como indisponível
    }

    public void devolver() {
        this.dataDevolucao = LocalDate.now();
        this.livro.setDisponivel(true); // Marca o livro como disponível
    }

    public Livro getLivro() {
        return livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Emprestimo [Livro=" + livro.getTitulo() + ", Cliente=" + cliente.getNome() + ", Data=" + dataEmprestimo + "]";
    }
}