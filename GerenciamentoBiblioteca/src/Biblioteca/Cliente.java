package Biblioteca;

import Servicos.Observer.Observer;
import Servicos.Logger;

public class Cliente implements Observer {

    private String nome;
    private int idUsuario;

    private transient Logger logger = Logger.getInstance();

    public Cliente(String nome, int idUsuario) {
        this.nome = nome;
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    @Override
    public  String toString() {
        return "Cliente [nome=" + nome + ", id=" + idUsuario + "]";
    }

    @Override
    public void update(Livro livro) {
        String notificacao = "Olá " + this.nome + "! O livro '" +
                livro.getTitulo() + "' que você estava esperando " +
                "agora está disponível!";

        System.out.println("\n--- NOTIFICAÇÃO DE DISPONIBILIDADE ---");
        System.out.println(notificacao);
        System.out.println("------------------------------------------\n");

        logger.log("NOTIFICACAO", "Cliente '" + this.nome + "' (ID: " + this.idUsuario +
                ") foi notificado sobre a disponibilidade do livro '" +
                livro.getTitulo() + "'.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        // 3. Converte o objeto e compara o ID
        Cliente cliente = (Cliente) o;
        return this.idUsuario == cliente.idUsuario;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idUsuario);
    }
}