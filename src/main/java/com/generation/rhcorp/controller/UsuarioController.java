package com.generation.rhcorp.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rhcorp.model.Usuario;
import com.generation.rhcorp.model.UsuarioLogin;
import com.generation.rhcorp.repository.CargoRepository;
import com.generation.rhcorp.repository.DepartamentoRepository;
import com.generation.rhcorp.repository.UsuarioRepository;
import com.generation.rhcorp.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private DepartamentoRepository departamentoRepository;

	UsuarioController(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin) {

		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario) {
		if (cargoRepository.existsById(usuario.getCargo().getId())
				&& departamentoRepository.existsById(usuario.getDepartamento().getId())) {
			return usuarioService.cadastrarUsuario(usuario)
					.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioRepository.findById(usuario.getId()).map(resposta -> {
			if (!cargoRepository.existsById(usuario.getCargo().getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cargo não existe!", null);
			}
			if (!departamentoRepository.existsById(usuario.getDepartamento().getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não existe!", null);
			}
			return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/{id}/calcular-salario")
	public ResponseEntity<Map<String, Object>> calcularSalario(
			@PathVariable Long id,
			@RequestParam int horasTrabalhadas,
			@RequestParam(required = false) BigDecimal bonus,
			@RequestParam(required = false) BigDecimal descontos) {

		return usuarioService.calcularSalario(id, horasTrabalhadas, bonus, descontos)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Map.of("mensagem", "Usuário não encontrado ou dados inválidos.")));
	}

}
