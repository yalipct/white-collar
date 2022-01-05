package com.itacademy.spring.apirest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "botiga")
public class Botiga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="botiga_id")
	private int id;
		
	@Column(name="nom_botiga", nullable = false, length = 30)
	private String nom;
	
	@Column(name="capacitat_botiga", nullable = false)
	private int capacitat_max;
	
	@JsonIgnore
	@OneToMany(mappedBy = "botiga", cascade = CascadeType.ALL, orphanRemoval = true) 
	private List<Quadre> quadres;
	
	public Botiga(int id,String nom, int capacitat_max) {
		this.id = id;
		this.nom = nom;
		this.capacitat_max = capacitat_max;
	}


	public Botiga() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public int getCapacitat_max() {
		return capacitat_max;
	}


	public void setCapacitat_max(int capacitat_max) {
		this.capacitat_max = capacitat_max;
	}


	public List<Quadre> getQuadres() {
		return quadres;
	}
	
	public List<Quadre> addQuadre(Quadre quadre) {
		
		if(quadres == null) {
			quadres = new ArrayList<Quadre>();
			quadres.add(quadre);
			quadre.setBotiga(this);
			return quadres;
		}
		return null;
	}
	
}
