package com.itacademy.spring.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itacademy.spring.apirest.domain.Botiga;


public interface BotigaDAO extends JpaRepository<Botiga, Integer> {

}
