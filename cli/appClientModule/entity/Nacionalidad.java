package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Nacionalidad {
	
	private String id_nacionalidad;
	private String cod_nacionalidad;
	private String desc_nacionalidad;
	private String id_pais;
	
	
	public String getId_nacionalidad() {
		return id_nacionalidad;
	}
	public void setId_nacionalidad(String id_nacionalidad) {
		this.id_nacionalidad = id_nacionalidad;
	}
	public String getCod_nacionalidad() {
		return cod_nacionalidad;
	}
	public void setCod_nacionalidad(String cod_nacionalidad) {
		this.cod_nacionalidad = cod_nacionalidad;
	}
	public String getDesc_nacionalidad() {
		return desc_nacionalidad;
	}
	public void setDesc_nacionalidad(String desc_nacionalidad) {
		this.desc_nacionalidad = desc_nacionalidad;
	}
	public String getId_pais() {
		return id_pais;
	}
	public void setId_pais(String id_pais) {
		this.id_pais = id_pais;
	}

	

	
	
	

}
