package Servicos;

import Biblioteca.Cliente;
import Biblioteca.Emprestimo;
import Biblioteca.Livro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServicoEmprestimo {

    private RepLivros repositorioLivros;
    private RepClientes repositorioClientes;

    private List<Emprestimo> emprestimosAtivos = new ArrayList<>();

    private Map<String, ListaDeEspera> listasDeEspera = new HashMap<>();

    private Logger logger = Logger.getInstance();

    public ServicoEmprestimo(RepLivros repositorioLivros, RepClientes repositorioClientes) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioClientes = repositorioClientes;
        logger.log("SYSTEM", "ServicoEmprestimo iniciado com dependências injetadas.");
    }

    public void emprestarLivro(String tituloDoLivro, int idDoCliente) {
        Livro livro = repositorioLivros.buscarLivro(tituloDoLivro);
        Cliente cliente = repositorioClientes.buscarCliente(idDoCliente);

        if (livro == null) {
            logger.log("ERRO", "Falha no empréstimo: Livro '" + tituloDoLivro + "' não foi encontrado.");
            return;
        }
        if (cliente == null) {
            logger.log("ERRO", "Falha no empréstimo: Cliente com ID " + idDoCliente + " não foi encontrado.");
            return;
        }

        if (livro.isDisponivel()) {
            Emprestimo novoEmprestimo = new Emprestimo(livro, cliente);
            emprestimosAtivos.add(novoEmprestimo);
            logger.log("EMPRESTIMO", "Sucesso: " + cliente.getNome() + " pegou o livro '" + livro.getTitulo() + "'.");
        } else {
            logger.log("AVISO", "Livro '" + livro.getTitulo() + "' está indisponível.");
            adicionarClienteListaEspera(livro, cliente);
        }
    }

    public void devolverLivro(String tituloDoLivro) {
        Emprestimo emprestimoParaRemover = null;
        Livro livroDevolvido = null;

        for (Emprestimo emprestimo : emprestimosAtivos) {
            if (emprestimo.getLivro().getTitulo().equalsIgnoreCase(tituloDoLivro)) {
                emprestimo.devolver(); // Marca o livro como disponível e seta data devolução
                livroDevolvido = emprestimo.getLivro();
                emprestimoParaRemover = emprestimo;
                break;
            }
        }

        if (emprestimoParaRemover != null) {
            emprestimosAtivos.remove(emprestimoParaRemover);
            logger.log("DEVOLUCAO", "Sucesso: O livro '" + livroDevolvido.getTitulo() + "' foi devolvido.");

            notificarListaDeEspera(livroDevolvido);

        } else {
            logger.log("ERRO", "Falha na devolução: Não foi encontrado empréstimo ativo para '" + tituloDoLivro + "'.");
        }
    }


    private void adicionarClienteListaEspera(Livro livro, Cliente cliente) {
        String titulo = livro.getTitulo();

        ListaDeEspera lista = listasDeEspera.get(titulo);
        if (lista == null) {
            lista = new ListaDeEspera(livro);
            listasDeEspera.put(titulo, lista);
            logger.log("LISTA DE ESPERA", "Criada nova lista de espera para '" + titulo + "'.");
        }

        boolean adiconado = lista.registrarObserver(cliente);
        if (adiconado){
            logger.log("LISTA DE ESPERA", cliente.getNome() + " foi adicionado à fila por '" + titulo + "'.");
        }else{
            logger.log("LISTA DE ESPERA", cliente.getNome() + " já estava na fila por '" + titulo + "'.");
        }
    }

    private void notificarListaDeEspera(Livro livro) {
        String titulo = livro.getTitulo();
        ListaDeEspera lista = listasDeEspera.get(titulo);

        if (lista != null && !lista.estaVazia()) {
            logger.log("LISTA DE ESPERA", "Livro '" + titulo + "' ficou disponível. Notificando o próximo da fila...");

            lista.notificarObservers(livro);

            if (lista.estaVazia()) {
                listasDeEspera.remove(titulo);
                logger.log("LISTA DE ESPERA", "A lista de espera por '" + titulo + "' agora está vazia e foi removida.");
            }
        }
    }
}