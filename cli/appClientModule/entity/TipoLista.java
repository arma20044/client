package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class TipoLista {
	
	private String id;
	private String descripcion;
	private String codigo;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
	
	
	

}
