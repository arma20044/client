
package src.main.java.hello;


import java.io.Serializable;
import java.sql.Date;

public class Voto implements Serializable{

	
	private Integer idVoto;
	private String nombre;
	private Boolean Voto;
	private Date fecha;
	private String Canditato;
	
	public Integer getIdVoto() {
		return idVoto;
	}
	public void setIdVoto(Integer idVoto) {
		this.idVoto = idVoto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getVoto() {
		return Voto;
	}
	public void setVoto(Boolean voto) {
		Voto = voto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCanditato() {
		return Canditato;
	}
	public void setCanditato(String canditato) {
		Canditato = canditato;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
