package com.generation.rhcorp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rhcorp.model.UsuarioLogin;
import com.generation.rhcorp.model.Cargo;
import com.generation.rhcorp.model.Usuario;
import com.generation.rhcorp.repository.UsuarioRepository;
import com.generation.rhcorp.security.JwtService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));

    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {

        if (usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.ofNullable(usuarioRepository.save(usuario));

        }

        return Optional.empty();

    }

    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

        // Gera o Objeto de autenticação
        var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
                usuarioLogin.get().getSenha());

        // Autentica o Usuario
        Authentication authentication = authenticationManager.authenticate(credenciais);

        // Se a autenticação foi efetuada com sucesso
        if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
            Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

            // Se o usuário foi encontrado
            if (usuario.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados
                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
                usuarioLogin.get().setSenha("");

                // Retorna o Objeto preenchido
                return usuarioLogin;

            }

        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private String gerarToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }

    public Optional<Map<String, Object>> calcularSalario(Long Id, int horasTrabalhadas, BigDecimal bonus,
            BigDecimal descontos) {

        if ((bonus != null && bonus.compareTo(BigDecimal.ZERO) < 0)
                || (descontos != null && descontos.compareTo(BigDecimal.ZERO) < 0)
                || (horasTrabalhadas < 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os parametros não podem ser negativos");
        }

        Optional<Usuario> usuarioExiste = usuarioRepository.findById(Id);

        if (usuarioExiste.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        Usuario usuario = usuarioExiste.get();
        Cargo cargo = usuario.getCargo();

        if (cargo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não possui cargo associado");
        }

        BigDecimal salarioBase = cargo.getSalario();

        // Cálculo do salário proporcional às horas trabalhadas
        // Considerando 220 horas como carga horária mensal padrão
        BigDecimal salarioProporcional = salarioBase
                .multiply(new BigDecimal(horasTrabalhadas))
                .divide(new BigDecimal(220), 2, RoundingMode.HALF_UP);

        // Aplicar bônus e descontos
        BigDecimal salarioLiquido = salarioProporcional
                .add(bonus != null ? bonus : BigDecimal.ZERO)
                .subtract(descontos != null ? descontos : BigDecimal.ZERO);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("salarioBase", salarioBase);
        resultado.put("salarioProporcional", salarioProporcional);
        resultado.put("bonus", bonus != null ? bonus : BigDecimal.ZERO);
        resultado.put("descontos", descontos != null ? descontos : BigDecimal.ZERO);
        resultado.put("salarioLiquido", salarioLiquido);

        return Optional.of(resultado);
    }
}