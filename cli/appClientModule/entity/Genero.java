package entity;

import java.util.Date;

public class Genero {
	
	private Integer id_genero;
	private String descripcion;
	private String codigo;
	private Integer id_evento;
	private Date fchins;
	private Date fchupd;
	private String usuarioins;
	private String usuarioupd;
	
	
	public Integer getId_genero() {
		return id_genero;
	}
	public void setId_genero(Integer id_genero) {
		this.id_genero = id_genero;
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
	public Integer getId_evento() {
		return id_evento;
	}
	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
	}
	public Date getFchins() {
		return fchins;
	}
	public void setFchins(Date fchins) {
		this.fchins = fchins;
	}
	public Date getFchupd() {
		return fchupd;
	}
	public void setFchupd(Date fchupd) {
		this.fchupd = fchupd;
	}
	public String getUsuarioins() {
		return usuarioins;
	}
	public void setUsuarioins(String usuarioins) {
		this.usuarioins = usuarioins;
	}
	public String getUsuarioupd() {
		return usuarioupd;
	}
	public void setUsuarioupd(String usuarioupd) {
		this.usuarioupd = usuarioupd;
	}
	
	
	
	


}
