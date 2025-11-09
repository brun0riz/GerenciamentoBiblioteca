package Servicos.Observer;

import Biblioteca.Livro;

public interface Subject {
    boolean registrarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers(Livro livro);

}
