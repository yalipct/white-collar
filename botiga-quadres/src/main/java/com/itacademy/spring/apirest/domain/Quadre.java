package com.itacademy.spring.apirest.domain;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="quadres")
public class Quadre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="quadre_id")
	private int id;
	
	@Column(name="nom_autor", nullable = false, length = 30)
	private String nom_autor;
	
	@Column(name="nom_quadre", nullable = false, length = 30)
	private String nom;
	
	@Column(name="preu_quadre", nullable = false, length = 30)
	private double preu;
	
	@Column(name="data_registre", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Calendar data_registre;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "botiga_id")
	private Botiga botiga;
	
	
	public Quadre(int id, String nom_autor, String nom, double preu, Calendar data_registre, Botiga botiga) {
		this.id = id;
		this.nom_autor = nom_autor;
		this.nom = nom;
		this.preu = preu;
		this.data_registre = data_registre;
		this.botiga = botiga;
	}


	public Quadre() {
		// TODO Auto-generated constructor stub
	}
		

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom_autor() {
		return nom_autor;
	}

	public void setNom_autor(String nom_autor) {
		this.nom_autor = nom_autor;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPreu() {
		return preu;
	}

	public void setPreu(double preu) {
		this.preu = preu;
	}

	
	public Calendar getData_registre() {
		return data_registre;
	}


	public void setData_registre(Calendar data_registre) {
		this.data_registre = data_registre;
	}


	public Botiga getBotiga() {
		return botiga;
	}


	public void setBotiga(Botiga botiga) {
		this.botiga = botiga;
	}
		
}
