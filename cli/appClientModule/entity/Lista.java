package entity;

import java.io.Serializable;

public class Lista implements Serializable{
	
	String nombreLista;
	String descripcion;
	String anhoLista;
	String nroLista;
	String fechaIns;
	String usuIns;
	
	public String getNombreLista() {
		return nombreLista;
	}
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAnhoLista() {
		return anhoLista;
	}
	public void setAnhoLista(String anhoLista) {
		this.anhoLista = anhoLista;
	}
	public String getNroLista() {
		return nroLista;
	}
	public void setNroLista(String nroLista) {
		this.nroLista = nroLista;
	}
	public String getFechaIns() {
		return fechaIns;
	}
	public void setFechaIns(String fechaIns) {
		this.fechaIns = fechaIns;
	}
	public String getUsuIns() {
		return usuIns;
	}
	public void setUsuIns(String usuIns) {
		this.usuIns = usuIns;
	}

	
	
}
