package projeto_final.sistema_hospitalar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projeto_final.sistema_hospitalar.dto.LoginRequest;
import projeto_final.sistema_hospitalar.dto.LoginResponse;
import projeto_final.sistema_hospitalar.model.Usuario;
import projeto_final.sistema_hospitalar.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        // Atualizar último login
        user.setUltimoLogin(LocalDateTime.now());
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        List<String> roles = user.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new LoginResponse(
                jwtToken,
                user.getId(),
                user.getUsername(),
                user.getNome(),
                user.getEmail(),
                roles);
    }

    public Usuario register(Usuario request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já existe");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já existe");
        }

        var user = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nome(request.getNome())
                .email(request.getEmail())
                .role(request.getRole())
                .ativo(true)
                .build();

        return repository.save(user);
    }
}