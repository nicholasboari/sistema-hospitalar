package projeto_final.sistema_hospitalar.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import projeto_final.sistema_hospitalar.model.Usuario;
import projeto_final.sistema_hospitalar.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin se não existir
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNome("Administrador");
            admin.setEmail("admin@hospital.com");
            admin.setRole(Usuario.Role.ADMIN);
            admin.setAtivo(true);
            usuarioRepository.save(admin);
        }

        // Criar usuário médico se não existir
        if (!usuarioRepository.existsByUsername("medico")) {
            Usuario medico = new Usuario();
            medico.setUsername("medico");
            medico.setPassword(passwordEncoder.encode("medico123"));
            medico.setNome("Dr. João Silva");
            medico.setEmail("joao.silva@hospital.com");
            medico.setRole(Usuario.Role.MEDICO);
            medico.setAtivo(true);
            usuarioRepository.save(medico);
        }

        // Criar usuário enfermeiro se não existir
        if (!usuarioRepository.existsByUsername("enfermeiro")) {
            Usuario enfermeiro = new Usuario();
            enfermeiro.setUsername("enfermeiro");
            enfermeiro.setPassword(passwordEncoder.encode("enfermeiro123"));
            enfermeiro.setNome("Maria Santos");
            enfermeiro.setEmail("maria.santos@hospital.com");
            enfermeiro.setRole(Usuario.Role.ENFERMEIRO);
            enfermeiro.setAtivo(true);
            usuarioRepository.save(enfermeiro);
        }

        // Criar usuário recepcionista se não existir
        if (!usuarioRepository.existsByUsername("recepcionista")) {
            Usuario recepcionista = new Usuario();
            recepcionista.setUsername("recepcionista");
            recepcionista.setPassword(passwordEncoder.encode("recepcionista123"));
            recepcionista.setNome("Ana Costa");
            recepcionista.setEmail("ana.costa@hospital.com");
            recepcionista.setRole(Usuario.Role.RECEPCIONISTA);
            recepcionista.setAtivo(true);
            usuarioRepository.save(recepcionista);
        }
    }
}