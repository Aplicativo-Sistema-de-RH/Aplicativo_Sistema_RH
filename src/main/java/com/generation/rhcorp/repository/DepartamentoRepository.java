package com.generation.rhcorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.rhcorp.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

	public List<Departamento> findAllByDescricaoContainingIgnoreCase (@Param("descricao") String descricao);
}
