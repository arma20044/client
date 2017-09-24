package entity;

// Generated 25/04/2017 03:26:32 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

/**
 * UcsawsPersona generated by hbm2java
 */
public class UcsawsPersona implements java.io.Serializable {

  private Integer idPersona;
  private UcsawsGenero ucsawsGenero;
  private UcsawsNacionalidad ucsawsNacionalidad;
  private UcsawsPais ucsawsPaisByIdPaisActual;
  private UcsawsPais ucsawsPaisByIdPaisOrigen;
  private String nombre;
  private String apellido;
  private Date fechaNacimiento;
  private Date fchIns;
  private Date fchUpd;
  private String usuarioIns;
  private String usuarioUpd;
  private String telLineaBaja;
  private String telCelular;
  private UcsawsEvento idEvento;
  private BigDecimal ci;
  private String email;


  
  private String datosPersonales;
  
  public String getDatosPersonales() {
    return nombre + " " + apellido;
  }

  public void setDatosPersonales(String datosPersonales) {
    this.datosPersonales = datosPersonales;
  }


  public UcsawsPersona() {}

  public UcsawsPersona(Integer idPersona, UcsawsGenero ucsawsGenero,
      UcsawsNacionalidad ucsawsNacionalidad, UcsawsPais ucsawsPaisByIdPaisActual,
      UcsawsPais ucsawsPaisByIdPaisOrigen, String nombre, String apellido, Date fechaNacimiento,
      Date fchIns, String usuarioIns, UcsawsEvento idEvento, BigDecimal ci) {
    this.idPersona = idPersona;
    this.ucsawsGenero = ucsawsGenero;
    this.ucsawsNacionalidad = ucsawsNacionalidad;
    this.ucsawsPaisByIdPaisActual = ucsawsPaisByIdPaisActual;
    this.ucsawsPaisByIdPaisOrigen = ucsawsPaisByIdPaisOrigen;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.fchIns = fchIns;
    this.usuarioIns = usuarioIns;
    this.idEvento = idEvento;
    this.ci = ci;
  }

  public UcsawsPersona(Integer idPersona, UcsawsGenero ucsawsGenero,
      UcsawsNacionalidad ucsawsNacionalidad, UcsawsPais ucsawsPaisByIdPaisActual,
      UcsawsPais ucsawsPaisByIdPaisOrigen, String nombre, String apellido, Date fechaNacimiento,
      Date fchIns, Date fchUpd, String usuarioIns, String usuarioUpd, String telLineaBaja,
      String telCelular, UcsawsEvento idEvento, BigDecimal ci, String email) {
    this.idPersona = idPersona;
    this.ucsawsGenero = ucsawsGenero;
    this.ucsawsNacionalidad = ucsawsNacionalidad;
    this.ucsawsPaisByIdPaisActual = ucsawsPaisByIdPaisActual;
    this.ucsawsPaisByIdPaisOrigen = ucsawsPaisByIdPaisOrigen;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.fchIns = fchIns;
    this.fchUpd = fchUpd;
    this.usuarioIns = usuarioIns;
    this.usuarioUpd = usuarioUpd;
    this.telLineaBaja = telLineaBaja;
    this.telCelular = telCelular;
    this.idEvento = idEvento;
    this.ci = ci;
    this.email = email;

  }

  public Integer getIdPersona() {
    return this.idPersona;
  }

  public void setIdPersona(Integer idPersona) {
    this.idPersona = idPersona;
  }

  public UcsawsGenero getUcsawsGenero() {
    return this.ucsawsGenero;
  }

  public void setUcsawsGenero(UcsawsGenero ucsawsGenero) {
    this.ucsawsGenero = ucsawsGenero;
  }

  public UcsawsNacionalidad getUcsawsNacionalidad() {
    return this.ucsawsNacionalidad;
  }

  public void setUcsawsNacionalidad(UcsawsNacionalidad ucsawsNacionalidad) {
    this.ucsawsNacionalidad = ucsawsNacionalidad;
  }

  public UcsawsPais getUcsawsPaisByIdPaisActual() {
    return this.ucsawsPaisByIdPaisActual;
  }

  public void setUcsawsPaisByIdPaisActual(UcsawsPais ucsawsPaisByIdPaisActual) {
    this.ucsawsPaisByIdPaisActual = ucsawsPaisByIdPaisActual;
  }

  public UcsawsPais getUcsawsPaisByIdPaisOrigen() {
    return this.ucsawsPaisByIdPaisOrigen;
  }

  public void setUcsawsPaisByIdPaisOrigen(UcsawsPais ucsawsPaisByIdPaisOrigen) {
    this.ucsawsPaisByIdPaisOrigen = ucsawsPaisByIdPaisOrigen;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Date getFechaNacimiento() {
    return this.fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
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

  public String getTelLineaBaja() {
    return this.telLineaBaja;
  }

  public void setTelLineaBaja(String telLineaBaja) {
    this.telLineaBaja = telLineaBaja;
  }

  public String getTelCelular() {
    return this.telCelular;
  }

  public void setTelCelular(String telCelular) {
    this.telCelular = telCelular;
  }

  public UcsawsEvento getIdEvento() {
    return this.idEvento;
  }

  public void setIdEvento(UcsawsEvento idEvento) {
    this.idEvento = idEvento;
  }

  public BigDecimal getCi() {
    return this.ci;
  }

  public void setCi(BigDecimal ci) {
    this.ci = ci;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }



}
