package com.example.AchadosPerdidos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.AchadosPerdidos.model.Role;
import com.example.AchadosPerdidos.model.Usuario;
import com.example.AchadosPerdidos.repository.RoleRepository;
import com.example.AchadosPerdidos.repository.UsuarioRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Criar roles padrão se não existirem
            createRoleIfNotExists("ROLE_ALUNO");
            createRoleIfNotExists("ROLE_FUNCIONARIO");
            createRoleIfNotExists("ROLE_PROFESSOR");
            createRoleIfNotExists("ROLE_ADMIN");

            // Limpar usuários com senhas em texto plano (migração de segurança)
            limparSenhasTextoPuro();

            // Criar usuário admin de teste se não existir
            criarUsuarioTesteSeNaoExistir();

            System.out.println("✓ Banco de dados inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByNome(roleName)) {
            Role role = new Role();
            role.setNome(roleName);
            roleRepository.save(role);
        }
    }

    private void limparSenhasTextoPuro() {
        usuarioRepository.findAll().forEach(usuario -> {
            String senha = usuario.getSenha();
            // Se a senha não começa com $2a, $2b ou $2y (BCrypt), é texto plano
            if (senha != null && !senha.matches("^\\$2[aby]\\$.*")) {
                // Deletar usuário com senha em texto plano
                usuarioRepository.delete(usuario);
            }
        });
    }

    private void criarUsuarioTesteSeNaoExistir() {
        String emailTeste = "admin@achados.com";
        if (usuarioRepository.findByEmail(emailTeste).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNome("Administrador");
            usuario.setEmail(emailTeste);
            usuario.setSenha(passwordEncoder.encode("admin123"));
            usuario.setTelefone("(11) 99999-9999");

            Role roleAdmin = roleRepository.findByNome("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ROLE_ADMIN não encontrada"));
            usuario.getRoles().add(roleAdmin);

            usuarioRepository.save(usuario);
            System.out.println("✓ Usuário admin de teste criado!");
        }
    }
}
