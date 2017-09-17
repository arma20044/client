package entity;

import java.util.Date;



public class UcsawsTipoActa implements java.io.Serializable {

  private Integer idTipoActa;

  private String codigoActa;

  private UcsawsEvento idEvento;

  private String descripcion;

  private Date fchIns;


  private Date fchUpd;


  private String usuarioIns;


  private String usuarioUpd;
  
  

  public Date getFchIns() {
    return fchIns;
  }

  public void setFchIns(Date fchIns) {
    this.fchIns = fchIns;
  }

  public Date getFchUpd() {
    return fchUpd;
  }

  public void setFchUpd(Date fchUpd) {
    this.fchUpd = fchUpd;
  }

  public String getUsuarioIns() {
    return usuarioIns;
  }

  public void setUsuarioIns(String usuarioIns) {
    this.usuarioIns = usuarioIns;
  }

  public String getUsuarioUpd() {
    return usuarioUpd;
  }

  public void setUsuarioUpd(String usuarioUpd) {
    this.usuarioUpd = usuarioUpd;
  }

  public Integer getIdTipoActa() {
    return idTipoActa;
  }

  public void setIdTipoActa(Integer idTipoActa) {
    this.idTipoActa = idTipoActa;
  }

  public String getCodigoActa() {
    return codigoActa;
  }

  public void setCodigoActa(String codigoActa) {
    this.codigoActa = codigoActa;
  }

  public UcsawsEvento getIdEvento() {
    return idEvento;
  }

  public void setIdEvento(UcsawsEvento idEvento) {
    this.idEvento = idEvento;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }



}
