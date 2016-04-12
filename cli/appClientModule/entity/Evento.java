package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Evento {
	
	private Integer id_evento;
	private String descripcion;
	private String id_tipo_evento;
	private Date fch_desde;
	private Date fch_hasta;
	private Date fch_ins;
	private Date fch_upd;
	private String usuario_ins;
	private String usuario_upd;
	private String nro_evento;
	public Integer getId_evento() {
		return id_evento;
	}
	public void setId_evento(Integer id_evento) {
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
	public Date getFch_desde() {
		return fch_desde;
	}
	public void setFch_desde(Date fch_desde) {
		this.fch_desde = fch_desde;
	}
	public Date getFch_hasta() {
		return fch_hasta;
	}
	public void setFch_hasta(Date fch_hasta) {
		this.fch_hasta = fch_hasta;
	}
	public Date getFch_ins() {
		return fch_ins;
	}
	public void setFch_ins(Date fch_ins) {
		this.fch_ins = fch_ins;
	}
	public Date getFch_upd() {
		return fch_upd;
	}
	public void setFch_upd(Date fch_upd) {
		this.fch_upd = fch_upd;
	}
	public String getUsuario_ins() {
		return usuario_ins;
	}
	public void setUsuario_ins(String usuario_ins) {
		this.usuario_ins = usuario_ins;
	}
	public String getUsuario_upd() {
		return usuario_upd;
	}
	public void setUsuario_upd(String usuario_upd) {
		this.usuario_upd = usuario_upd;
	}
	public String getNro_evento() {
		return nro_evento;
	}
	public void setNro_evento(String nro_evento) {
		this.nro_evento = nro_evento;
	}
	
	
	
	

}
