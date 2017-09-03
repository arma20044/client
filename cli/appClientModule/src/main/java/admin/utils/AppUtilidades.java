package src.main.java.admin.utils;

public class AppUtilidades {
	 public static void main(String[] args) throws Exception {
		 
		 	System.out.println("Texto Original: Esto es una prueba");
		 
	        String encriptado = Utilidades.Encriptar("4");
	        System.out.println("Encriptado: " + encriptado);
	       // String desencriptado = Utilidades.Desencriptar("mDtzEqaYl0g=");
	       // System.out.println("DesEncriptado: " + desencriptado);
	        
	        /*
	        Texto Original: Esto es una prueba
	        Encriptado: kE03YKUMUVH+7AmRH+KGLKbnY47aDuJ/
	        DesEncriptado: Esto es una prueba
	        */


}
}
