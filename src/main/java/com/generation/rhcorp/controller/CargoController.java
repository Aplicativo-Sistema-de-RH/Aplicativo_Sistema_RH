package com.generation.rhcorp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rhcorp.model.Cargo;
import com.generation.rhcorp.repository.CargoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cargo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CargoController {

	@Autowired
	private CargoRepository cargoRepository;

	@GetMapping
	public ResponseEntity<List<Cargo>> getAll() {
		return ResponseEntity.ok(cargoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cargo> getById(@PathVariable Long id) {
		return cargoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nomecargo/{nome}")
	public ResponseEntity<List<Cargo>> getByDescricao(@PathVariable String nome) {
		return ResponseEntity.ok(cargoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Cargo> post(@Valid @RequestBody Cargo cargo) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cargoRepository.save(cargo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cargo> put(@PathVariable Long id, @Valid @RequestBody Cargo cargo) {
		return cargoRepository.findById(id)
				.map(resposta -> {
					cargo.setId(id);
					return ResponseEntity.status(HttpStatus.OK).body(cargoRepository.save(cargo));
				})
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);

		if (cargo.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		cargoRepository.deleteById(id);
	}

}
