package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Evento {
	
	private String id_evento;
	private String descripcion;
	private String id_tipo_evento;
	private String fch_desde;
	private String fch_hasta;
	private String nro_evento;
	public String getId_evento() {
		return id_evento;
	}
	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId_tipo_evento() {
		return id_tipo_evento;
	}
	public void setId_tipo_evento(String id_tipo_evento) {
		this.id_tipo_evento = id_tipo_evento;
	}
	public String getFch_desde() {
		return fch_desde;
	}
	public void setFch_desde(String fch_desde) {
		this.fch_desde = fch_desde;
	}
	public String getFch_hasta() {
		return fch_hasta;
	}
	public void setFch_hasta(String fch_hasta) {
		this.fch_hasta = fch_hasta;
	}
	public String getNro_evento() {
		return nro_evento;
	}
	public void setNro_evento(String nro_evento) {
		this.nro_evento = nro_evento;
	}
	
	
	
	

}
