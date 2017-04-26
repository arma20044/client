package entity;

// Generated 22/04/2017 08:46:05 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * UcsawsRoles generated by hbm2java
 */
public class UcsawsRoles implements java.io.Serializable {

	private Integer idRol;
	private UcsawsEvento ucsawsEvento;
	private String codigo;
	private String descripcion;
	private String usuarioIns;
	private String usuarioUpd;
	private Date fchIns;
	private Date fchUpd;

	public UcsawsRoles() {
	}

	public UcsawsRoles(Integer idRol, UcsawsEvento ucsawsEvento) {
		this.idRol = idRol;
		this.ucsawsEvento = ucsawsEvento;
	}

	public UcsawsRoles(Integer idRol, UcsawsEvento ucsawsEvento, String codigo,
			String descripcion, String usuarioIns, String usuarioUpd,
			Date fchIns, Date fchUpd) {
		this.idRol = idRol;
		this.ucsawsEvento = ucsawsEvento;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.usuarioIns = usuarioIns;
		this.usuarioUpd = usuarioUpd;
		this.fchIns = fchIns;
		this.fchUpd = fchUpd;
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public UcsawsEvento getUcsawsEvento() {
		return this.ucsawsEvento;
	}

	public void setUcsawsEvento(UcsawsEvento ucsawsEvento) {
		this.ucsawsEvento = ucsawsEvento;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuarioIns() {
		return this.usuarioIns;
	}

	public void setUsuarioIns(String usuarioIns) {
		this.usuarioIns = usuarioIns;
	}

	public String getUsuarioUpd() {
		return this.usuarioUpd;
	}

	public void setUsuarioUpd(String usuarioUpd) {
		this.usuarioUpd = usuarioUpd;
	}

	public Date getFchIns() {
		return this.fchIns;
	}

	public void setFchIns(Date fchIns) {
		this.fchIns = fchIns;
	}

	public Date getFchUpd() {
		return this.fchUpd;
	}

	public void setFchUpd(Date fchUpd) {
		this.fchUpd = fchUpd;
	}

}
