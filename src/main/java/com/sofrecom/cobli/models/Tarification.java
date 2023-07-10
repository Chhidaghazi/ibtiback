package com.sofrecom.cobli.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tarification {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String codeTarif;
	@ManyToOne
    @JoinColumn(name = "idPrestation")
    Prestation typeprestation;
	private long valeur;
	
	
	
	public String getCodeTarif() {
		return codeTarif;
	}
	public void setCodeTarif(String codeTarif) {
		this.codeTarif = codeTarif;
	}
	
	
	
	public Prestation getTypeprestation() {
		return typeprestation;
	}
	public void setTypeprestation(Prestation typeprestation) {
		this.typeprestation = typeprestation;
	}
	public long getValeur() {
		return valeur;
	}
	public void setValeur(long valeur) {
		this.valeur = valeur;
	}
	public Tarification() {
		super();
	}
	public Tarification(String codeTarif, Prestation typeprestation, long valeur) {
		super();
		this.codeTarif = codeTarif;
		this.typeprestation = typeprestation;
		this.valeur = valeur;
	}
	@Override
	public String toString() {
		return "Tarification [codeTarif=" + codeTarif + ", typeprestation=" + typeprestation + ", valeur=" + valeur
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
