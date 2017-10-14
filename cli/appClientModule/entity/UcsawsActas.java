package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "ucsaws_actas")
public class UcsawsActas implements java.io.Serializable {

  private Integer idActa;

  private UcsawsEvento idEvento;

  private UcsawsMesa idMesa;

  private String observacion;

  private String descripcion;

  private Date fecha;

  private Integer numeroVotantes;

  private UcsawsTipoActa tipoActa;

  private Date fchIns;

  private Date fchUpd;

  private String usuarioIns;

  private String usuarioUpd;
  
  private UcsawsActas actaFinalizada;

  public Integer getIdActa() {
    return idActa;
  }

  public void setIdActa(Integer idActa) {
    this.idActa = idActa;
  }

  public UcsawsEvento getIdEvento() {
    return idEvento;
  }

  public void setIdEvento(UcsawsEvento idEvento) {
    this.idEvento = idEvento;
  }

  public UcsawsMesa getIdMesa() {
    return idMesa;
  }

  public void setIdMesa(UcsawsMesa idMesa) {
    this.idMesa = idMesa;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Integer getNumeroVotantes() {
    return numeroVotantes;
  }

  public void setNumeroVotantes(Integer numeroVotantes) {
    this.numeroVotantes = numeroVotantes;
  }

  public UcsawsTipoActa getTipoActa() {
    return tipoActa;
  }

  public void setTipoActa(UcsawsTipoActa tipoActa) {
    this.tipoActa = tipoActa;
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

  public UcsawsActas getActaFinalizada() {
    return actaFinalizada;
  }

  public void setActaFinalizada(UcsawsActas actaFinalizada) {
    this.actaFinalizada = actaFinalizada;
  }
 

  
  
}
