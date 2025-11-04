package Biblioteca;

public class Cliente {
	
	       private String nome;
	       private int idUsuario;
	       
	       public Cliente(String nome, int idUsuario) {
	    	   this.nome = nome;
	    	   this.idUsuario = idUsuario;
	       }
	       
	       public String getNome() {
	    	   return nome;
	       }
	       public int idUsuario() {
	    	   return idUsuario;
	       }
	       
	       @Override
	       public  String toString() {
	    	   return "Cliente [nome=" + nome + "id=" + idUsuario + "]";
	       }
	}

}
