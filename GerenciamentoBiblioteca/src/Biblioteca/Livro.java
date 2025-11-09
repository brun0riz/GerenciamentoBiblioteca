package Biblioteca;

public class Livro {
   private String titulo;
   private String autor;
   private boolean disponivel = true;
 
   public Livro(String titulo, String autor) {
	   this.titulo = titulo;
	   this.autor = autor;
   }
   public String getTitulo() {
	   return titulo;
   }
   public String getAutor() {
	   return autor;
   }
   public boolean isDisponivel() {
	   return disponivel;
   }
   public void setDisponivel (boolean disponivel) {
	   this.disponivel = disponivel;
   }
   
   @Override
   public String toString() {
	   return "Livro [titulo=" + titulo + ",autor="+autor+"]";
   }
}
