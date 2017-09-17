package entity;
// Generated 06/07/2017 07:49:28 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * UcsawsVigenciaHorarioXPais generated by hbm2java
 */
public class UcsawsVigenciaHorarioXPais implements java.io.Serializable {


  private Integer idVigencia;
  private UcsawsPais idPais;
  private Date fchVigenciaDesde;
  private Date fchVigenciaHasta;
  private Date fchIns;
  private Date fchUpd;
  private UcsawsEvento idEvento;
  private String usuarioIns;
  private String usuarioUpd;

  public UcsawsVigenciaHorarioXPais() {}


  public UcsawsVigenciaHorarioXPais(Integer idVigencia, UcsawsPais idPais, UcsawsEvento idEvento, String usuarioIns) {
    this.idVigencia = idVigencia;
    this.idPais = idPais;
    this.idEvento = idEvento;
    this.usuarioIns = usuarioIns;
  }

  public UcsawsVigenciaHorarioXPais(Integer idVigencia, UcsawsPais idPais, Date fchVigenciaDesde,
      Date fchVigenciaHasta, Date fchIns, Date fchUpd, UcsawsEvento idEvento, String usuarioIns,
      String usuarioUpd) {
    this.idVigencia = idVigencia;
    this.idPais = idPais;
    this.fchVigenciaDesde = fchVigenciaDesde;
    this.fchVigenciaHasta = fchVigenciaHasta;
    this.fchIns = fchIns;
    this.fchUpd = fchUpd;
    this.idEvento = idEvento;
    this.usuarioIns = usuarioIns;
    this.usuarioUpd = usuarioUpd;
  }

  public Integer getIdVigencia() {
    return this.idVigencia;
  }

  public void setIdVigencia(Integer idVigencia) {
    this.idVigencia = idVigencia;
  }

  public UcsawsPais getIdPais() {
    return this.idPais;
  }

  public void setIdPais(UcsawsPais idPais) {
    this.idPais = idPais;
  }

  public Date getFchVigenciaDesde() {
    return this.fchVigenciaDesde;
  }

  public void setFchVigenciaDesde(Date fchVigenciaDesde) {
    this.fchVigenciaDesde = fchVigenciaDesde;
  }

  public Date getFchVigenciaHasta() {
    return this.fchVigenciaHasta;
  }

  public void setFchVigenciaHasta(Date fchVigenciaHasta) {
    this.fchVigenciaHasta = fchVigenciaHasta;
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



}