package com.generation.rhcorp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rhcorp.model.Cargo;
import com.generation.rhcorp.model.Departamento;
import com.generation.rhcorp.repository.CargoRepository;
import com.generation.rhcorp.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cargo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CargoController {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Cargo>> getAll(){
		return ResponseEntity.ok(cargoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cargo> getById(@PathVariable Long id){
		return cargoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nomecargo/{nomeCargo}")
	public ResponseEntity<List<Cargo>> getByDescricao(@PathVariable String nomeCargo){
		return ResponseEntity.ok(cargoRepository.findAllByNomeCargoContainingIgnoreCase(nomeCargo));
	}
	
	@PostMapping
	public ResponseEntity<Cargo> post(@Valid @RequestBody Cargo cargo){
		if (usuarioRepository.existsById(cargo.getUsuario().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(cargoRepository.save(cargo));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não existe!", null);
	}
	
}
