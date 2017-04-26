package entity;
// Generated 22/04/2017 10:29:02 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * UcsawsTipoEvento generated by hbm2java
 */
public class UcsawsTipoEvento implements java.io.Serializable {

	private Integer idTipoEvento;
	private String descripcion;
	private Date fchIns;
	private Date fchUpd;
	private String usuarioIns;
	private String usuarioUpd;
	private Integer idEvento;

	public UcsawsTipoEvento() {
	}

	public UcsawsTipoEvento(Integer idTipoEvento, Date fchIns, String usuarioIns) {
		this.idTipoEvento = idTipoEvento;
		this.fchIns = fchIns;
		this.usuarioIns = usuarioIns;
	}

	public UcsawsTipoEvento(Integer idTipoEvento, String descripcion, Date fchIns,
			Date fchUpd, String usuarioIns, String usuarioUpd, Integer idEvento) {
		this.idTipoEvento = idTipoEvento;
		this.descripcion = descripcion;
		this.fchIns = fchIns;
		this.fchUpd = fchUpd;
		this.usuarioIns = usuarioIns;
		this.usuarioUpd = usuarioUpd;
		this.idEvento = idEvento;
	}

	public Integer getIdTipoEvento() {
		return this.idTipoEvento;
	}

	public void setIdTipoEvento(Integer idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

}
