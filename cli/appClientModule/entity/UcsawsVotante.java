package entity;// default package
// Generated 25/04/2017 09:24:26 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * UcsawsVotante generated by hbm2java
 */
public class UcsawsVotante implements java.io.Serializable {

	private Integer idVotante;
	private UcsawsMesa ucsawsMesa;
	private UcsawsPersona idPersona;
	private Integer sufrago;
	private Date fchIns;
	private Date fchUpd;
	private String usuarioIns;
	private String usuarioUpd;
	private Integer habilitado;
	private UcsawsEvento idEvento;

	public UcsawsVotante() {
	}

	public UcsawsVotante(Integer idVotante, UcsawsMesa ucsawsMesa, UcsawsPersona idPersona,
			Integer sufrago, Date fchIns, String usuarioIns, Integer habilitado,
			UcsawsEvento idEvento) {
		this.idVotante = idVotante;
		this.ucsawsMesa = ucsawsMesa;
		this.idPersona = idPersona;
		this.sufrago = sufrago;
		this.fchIns = fchIns;
		this.usuarioIns = usuarioIns;
		this.habilitado = habilitado;
		this.idEvento = idEvento;
	}

	public UcsawsVotante(Integer idVotante, UcsawsMesa ucsawsMesa, UcsawsPersona idPersona,
			Integer sufrago, Date fchIns, Date fchUpd, String usuarioIns,
			String usuarioUpd, Integer habilitado, UcsawsEvento idEvento) {
		this.idVotante = idVotante;
		this.ucsawsMesa = ucsawsMesa;
		this.idPersona = idPersona;
		this.sufrago = sufrago;
		this.fchIns = fchIns;
		this.fchUpd = fchUpd;
		this.usuarioIns = usuarioIns;
		this.usuarioUpd = usuarioUpd;
		this.habilitado = habilitado;
		this.idEvento = idEvento;
	}

	public Integer getIdVotante() {
		return this.idVotante;
	}

	public void setIdVotante(Integer idVotante) {
		this.idVotante = idVotante;
	}

	public UcsawsMesa getUcsawsMesa() {
		return this.ucsawsMesa;
	}

	public void setUcsawsMesa(UcsawsMesa ucsawsMesa) {
		this.ucsawsMesa = ucsawsMesa;
	}

	public UcsawsPersona getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(UcsawsPersona idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getSufrago() {
		return this.sufrago;
	}

	public void setSufrago(Integer sufrago) {
		this.sufrago = sufrago;
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

	public Integer getHabilitado() {
		return this.habilitado;
	}

	public void setHabilitado(Integer habilitado) {
		this.habilitado = habilitado;
	}

	public UcsawsEvento getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(UcsawsEvento idEvento) {
		this.idEvento = idEvento;
	}

}
