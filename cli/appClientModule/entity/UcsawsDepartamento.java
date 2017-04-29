package entity;
// Generated 25/04/2017 09:24:26 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UcsawsDepartamento generated by hbm2java
 */
public class UcsawsDepartamento implements java.io.Serializable {

	private int idDepartamento;
	private String descDepartamento;
	private String nroDepartamento;
	private String usuarioIns;
	private String usuarioUpd;
	private Date fchIns;
	private Date fchUpd;
	private UcsawsEvento idEvento;


	public UcsawsDepartamento() {
	}

	public UcsawsDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public UcsawsDepartamento(int idDepartamento, String descDepartamento,
			String nroDepartamento, String usuarioIns, String usuarioUpd,
			Date fchIns, Date fchUpd, UcsawsEvento idEvento) {
		this.idDepartamento = idDepartamento;
		this.descDepartamento = descDepartamento;
		this.nroDepartamento = nroDepartamento;
		this.usuarioIns = usuarioIns;
		this.usuarioUpd = usuarioUpd;
		this.fchIns = fchIns;
		this.fchUpd = fchUpd;
		this.idEvento = idEvento;
		
	}

	public int getIdDepartamento() {
		return this.idDepartamento;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDescDepartamento() {
		return this.descDepartamento;
	}

	public void setDescDepartamento(String descDepartamento) {
		this.descDepartamento = descDepartamento;
	}

	public String getNroDepartamento() {
		return this.nroDepartamento;
	}

	public void setNroDepartamento(String nroDepartamento) {
		this.nroDepartamento = nroDepartamento;
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

	public UcsawsEvento getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(UcsawsEvento idEvento) {
		this.idEvento = idEvento;
	}

	

}
