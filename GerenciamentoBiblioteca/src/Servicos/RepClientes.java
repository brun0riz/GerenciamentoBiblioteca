package Servicos;

import Biblioteca.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RepClientes {
    private List<Cliente> clientes = new ArrayList<>();

    private Logger logger = Logger.getInstance();

    public void cadastrarCliente(Cliente cliente){
        this.clientes.add(cliente);
        logger.log("CLIENTE", "Cliente cadastrado: " + cliente.getNome());
    }

    public Cliente buscarCliente(int idUsuario) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdUsuario() == idUsuario) {
                return cliente;
            }
        }
        return null;
    }

    // Método para listar todos, útil para o futuro
    public List<Cliente> getClientes() {
        return clientes;
    }
}
