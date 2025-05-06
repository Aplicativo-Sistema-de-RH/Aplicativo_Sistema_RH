package com.generation.rhcorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.rhcorp.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
	public List<Cargo> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
