package entity;
// Generated 22/04/2017 10:52:11 PM by Hibernate Tools 4.3.1

/**
 * UcsawsHabilitado generated by hbm2java
 */
public class UcsawsHabilitado implements java.io.Serializable {

	private int idHabilitado;
	private String codHabilitado;

	public UcsawsHabilitado() {
	}

	public UcsawsHabilitado(int idHabilitado) {
		this.idHabilitado = idHabilitado;
	}

	public UcsawsHabilitado(int idHabilitado, String codHabilitado) {
		this.idHabilitado = idHabilitado;
		this.codHabilitado = codHabilitado;
	}

	public int getIdHabilitado() {
		return this.idHabilitado;
	}

	public void setIdHabilitado(int idHabilitado) {
		this.idHabilitado = idHabilitado;
	}

	public String getCodHabilitado() {
		return this.codHabilitado;
	}

	public void setCodHabilitado(String codHabilitado) {
		this.codHabilitado = codHabilitado;
	}

}
