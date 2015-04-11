package entity;

import java.io.Serializable;

public class Pais implements Serializable{
	
	String nombre;
	String descripcion;
	String fechaIns;
	String usuIns;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
