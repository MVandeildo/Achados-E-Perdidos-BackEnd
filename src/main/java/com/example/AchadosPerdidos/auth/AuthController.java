package com.example.AchadosPerdidos.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AchadosPerdidos.model.Role;
import com.example.AchadosPerdidos.model.Usuario;
import com.example.AchadosPerdidos.repository.RoleRepository;
import com.example.AchadosPerdidos.repository.UsuarioRepository;
import com.example.AchadosPerdidos.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public record LoginRequest(String email, String senha) {
    }

    public record LoginResponse(String token) {
    }

    public record RegisterRequest(String nome, String email, String senha, String telefone) {
    }

    public record RegisterResponse(String mensagem, String email) {
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(), request.senha()));

            String token = jwtService.generateToken(
                    (UserDetails) authentication.getPrincipal());

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email ou senha inválidos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validar se email já existe
            if (usuarioRepository.findByEmail(request.email()).isPresent()) {
                return ResponseEntity.badRequest().body("Email já registrado");
            }

            // Criar novo usuário
            Usuario usuario = new Usuario();
            usuario.setNome(request.nome());
            usuario.setEmail(request.email());
            usuario.setSenha(new BCryptPasswordEncoder().encode(request.senha()));
            usuario.setTelefone(request.telefone());

            // Obter ou criar role ALUNO
            Role roleAluno = roleRepository.findByNome("ROLE_ALUNO")
                    .orElseGet(() -> {
                        Role novaRole = new Role();
                        novaRole.setNome("ROLE_ALUNO");
                        return roleRepository.save(novaRole);
                    });

            // Associar role ao usuário
            usuario.getRoles().add(roleAluno);

            // Salvar usuário
            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse("Usuário registrado com sucesso!", request.email()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }
}
