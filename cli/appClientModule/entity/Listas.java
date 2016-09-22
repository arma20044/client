package entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Listas implements Serializable{
	
	String id_lista;
	String nombre_lista;
	String nro_lista;
	String anho_lista;
	String id_tipo_lista;
	String id_evento;
	public String getId_lista() {
		return id_lista;
	}
	public void setId_lista(String id_lista) {
		this.id_lista = id_lista;
	}
	public String getNombre_lista() {
		return nombre_lista;
	}
	public void setNombre_lista(String nombre_lista) {
		this.nombre_lista = nombre_lista;
	}
	public String getNro_lista() {
		return nro_lista;
	}
	public void setNro_lista(String nro_lista) {
		this.nro_lista = nro_lista;
	}
	public String getAnho_lista() {
		return anho_lista;
	}
	public void setAnho_lista(String anho_lista) {
		this.anho_lista = anho_lista;
	}
	public String getId_tipo_lista() {
		return id_tipo_lista;
	}
	public void setId_tipo_lista(String id_tipo_lista) {
		this.id_tipo_lista = id_tipo_lista;
	}
	public String getId_evento() {
		return id_evento;
	}
	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}
	
	

	
	
}
