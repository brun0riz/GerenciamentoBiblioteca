package Servicos;

import Biblioteca.Livro;
import java.util.ArrayList;
import java.util.List;


public class RepLivros {
    private List<Livro> acervo = new ArrayList<>();

    private Logger logger = Logger.getInstance();

    // Funções para gerenciamento dos livros

    public void adicionarLivro(Livro livro){
        this.acervo.add(livro);
        logger.log("ACERVO", "Livro adicionado: " + livro.getTitulo());
    }

    public Livro buscarLivro(String titulo){
        for(Livro livro : acervo){
            if(livro.getTitulo().equalsIgnoreCase(titulo)){
                return livro;
            }
        }
        return null;
    }

    public List<Livro> getAcervo(){
        return acervo;
    }
}
