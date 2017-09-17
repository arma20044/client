package entity;

import java.util.Date;

public class UcsawsTipoMiembroMesa implements java.io.Serializable {


  private Integer idTipoMiembroMesa;


  private String codigo;


  private String descripcion;


  private UcsawsEvento idEvento;


  private Date fchIns;


  private Date fchUpd;


  private String usuarioIns;


  private String usuarioUpd;


  public Integer getIdTipoMiembroMesa() {
    return idTipoMiembroMesa;
  }


  public void setIdTipoMiembroMesa(Integer idTipoMiembroMesa) {
    this.idTipoMiembroMesa = idTipoMiembroMesa;
  }


  public String getCodigo() {
    return codigo;
  }


  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }


  public String getDescripcion() {
    return descripcion;
  }


  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
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
