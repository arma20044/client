package entity;

import java.util.Date;

 


public class UcsawsMiembroMesa implements java.io.Serializable {


  private Integer idMiembroMesa;


  private UcsawsPersona idPersona;


  private UcsawsEvento idEvento;


  private Date fchIns;


  private Date fchUpd;


  private String usuarioIns;


  private String usuarioUpd;
  
  private UcsawsTipoMiembroMesa miembroMesa;


  public UcsawsTipoMiembroMesa getMiembroMesa() {
    return miembroMesa;
  }


  public void setMiembroMesa(UcsawsTipoMiembroMesa miembroMesa) {
    this.miembroMesa = miembroMesa;
  }


  public Integer getIdMiembroMesa() {
    return idMiembroMesa;
  }


  public void setIdMiembroMesa(Integer idMiembroMesa) {
    this.idMiembroMesa = idMiembroMesa;
  }


  public UcsawsPersona getIdPersona() {
    return idPersona;
  }


  public void setIdPersona(UcsawsPersona idPersona) {
    this.idPersona = idPersona;
  }


  public UcsawsEvento getIdEvento() {
    return idEvento;
  }


  public void setIdEvento(UcsawsEvento idEvento) {
    this.idEvento = idEvento;
  }


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



}
