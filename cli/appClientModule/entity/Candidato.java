package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Candidato {
	
	private String id_candidato;
	private String id_persona;
	private String descripcion;
	private String id_lista;
	private String codigo;
	private String id_evento;
	
	public String getId_candidato() {
		return id_candidato;
	}
	public void setId_candidato(String id_candidato) {
		this.id_candidato = id_candidato;
	}
	public String getId_persona() {
		return id_persona;
	}
	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId_lista() {
		return id_lista;
	}
	public void setId_lista(String id_lista) {
		this.id_lista = id_lista;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getId_evento() {
		return id_evento;
	}
	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}
	
	
	
	

}
