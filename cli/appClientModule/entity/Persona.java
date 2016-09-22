package entity;

import javax.persistence.Entity;

@Entity
public class Persona {
	
	private String id_persona;
	private String nombre;
	private String apellido;
	private String fecha_Nacimiento;
	private String pais_origen;
	private String pais_actual;
	private String genero;
	private String lineabaja;
	private String celular;
	private String nacionalidad;
	private String evento;
	private String ci;
	private String email;
	
	public String getId_persona() {
		return id_persona;
	}
	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFecha_Nacimiento() {
		return fecha_Nacimiento;
	}
	public void setFecha_Nacimiento(String fecha_Nacimiento) {
		this.fecha_Nacimiento = fecha_Nacimiento;
	}
	public String getPais_origen() {
		return pais_origen;
	}
	public void setPais_origen(String pais_origen) {
		this.pais_origen = pais_origen;
	}
	public String getPais_actual() {
		return pais_actual;
	}
	public void setPais_actual(String pais_actual) {
		this.pais_actual = pais_actual;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getLineabaja() {
		return lineabaja;
	}
	public void setLineabaja(String lineabaja) {
		this.lineabaja = lineabaja;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	

}
