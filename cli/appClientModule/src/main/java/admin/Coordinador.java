package src.main.java.admin;


import src.main.java.hello.VentanaPrincipal;


public class Coordinador {

	private Logica miLogica;
	private VentanaPrincipal miVentanaPrincipal;

	
	
	public VentanaPrincipal getMiVentanaPrincipal() {
		return miVentanaPrincipal;
	}
	public void setMiVentanaPrincipal(VentanaPrincipal miVentanaPrincipal) {
		this.miVentanaPrincipal = miVentanaPrincipal;
	}

	public Logica getMiLogica() {
		return miLogica;
	}
	public void setMiLogica(Logica miLogica) {
		this.miLogica = miLogica;
	}
	
	
	
//////////////////////////////////////////////////////////
	

//	
////	public void registrarPersona(PersonaVo miPersona) {
////		miLogica.validarRegistro(miPersona);
////	}
//	
////	public PersonaVo buscarPersona(String codigoPersona) {
////		return miLogica.validarConsulta(codigoPersona);
////	}
//	
////	public void modificarPersona(PersonaVo miPersona) {
////		miLogica.validarModificacion(miPersona);
////	}
////	public void eliminarPersona(String codigo) {
////		miLogica.validarEliminacion(codigo);
////	}


}
