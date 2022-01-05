package com.itacademy.spring.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.spring.apirest.domain.Botiga;
import com.itacademy.spring.apirest.domain.Quadre;

public interface QuadreDAO extends JpaRepository<Quadre, Integer> {
	int deleteAllByBotiga(Botiga botiga);
}
