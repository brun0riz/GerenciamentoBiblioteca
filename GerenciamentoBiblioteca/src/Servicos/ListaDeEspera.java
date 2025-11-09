package Servicos;

import Servicos.Observer.Observer;
import Servicos.Observer.Subject;
import Biblioteca.Livro;

import java.util.ArrayList;
import java.util.List;


public class ListaDeEspera implements Subject {

    private Livro livro;
    private List<Observer> observadores = new ArrayList<>();

    public ListaDeEspera(Livro livro){
        this.livro = livro;
    }

    @Override
    public boolean registrarObserver(Observer observer) {
        if (!observadores.contains(observer)) {
            observadores.add(observer);
            return true;
        }
        return false;
    }

    @Override
    public void removerObserver(Observer observer) {
        observadores.remove(observer);
    }

    @Override
    public void notificarObservers(Livro livro) {

        if(!observadores.isEmpty()){
            Observer proximoFila = observadores.get(0);

            proximoFila.update(livro);

            observadores.remove(0);
        }

    }

    public boolean estaVazia(){
        return observadores.isEmpty();
    }


}
