package Servicos;

import Biblioteca.Cliente;
import Biblioteca.Livro;

import java.util.ArrayList;
import java.util.List;

public class Gerenciamento {

	
	private List<Livro> acervo;
	private List<Cliente> clientes;
	
	public Gerenciamento() {
		this.acervo = new ArrayList<>();
		this.clientes = new  ArrayList<>();
		System.out.println("Sistema iniciado");
	}
	public void adicionarLivro (Livro livro) {
		this.acervo.add(livro);
		System.out.println("Livro adicionado"+livro.getTitulo());
	}
	public void cadastrarCliente(Cliente cliente) {
		this.clientes.add(cliente);
		System.out.println("Cliente cadastrado:"+cliente.getNome());
	}
	private Livro buscarLivro (String titulo) {
		for (Livro livro: acervo) {
			if(livro.getTitulo().equalsIgnoreCase(titulo)) {
				return livro;
			}
		}
		return null;
	}
	public void emprestarLivro (String tituloDoLivro, String nomeDoCliente) {
		Livro livro = buscarLivro(tituloDoLivro);
		
		if (livro ==null) {
			System.out.println("Livro" + tituloDoLivro + "não foi encontrado");
			return;
		}
		if (livro.isDisponivel()) {
			livro.setDisponivel(false);
			System.out.println("Livro emprestado." + nomeDoCliente + "pegou o livro" + livro.getTitulo() + ".");
		}else {
			System.out.println("O livro" + livro.getTitulo() + "está emprestado");
		}
	}
	public void devolverLivro(String tituloDoLivro) {
		Livro livro = buscarLivro(tituloDoLivro);
		
		if (livro == null) {
			System.out.println("Livro" + tituloDoLivro + "não foi encontrado");
			return;
		}
		if(!livro.isDisponivel()) {
			livro.setDisponivel(true);
			System.out.println("O livro" + livro.getTitulo() + "foi devolvido.");
		}else {
			System.out.println("O livro" + livro.getTitulo() + "já estava no acervo.");
		}
	}
}
