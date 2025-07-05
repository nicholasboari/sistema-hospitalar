package projeto_final.sistema_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto_final.sistema_hospitalar.model.Paciente;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    Optional<Paciente> findByCpf(String cpf);
    
    List<Paciente> findByAtivoTrue();
    
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
    
    @Query("SELECT p FROM Paciente p WHERE p.cpf = :cpf AND p.ativo = true")
    Optional<Paciente> findByCpfAndAtivo(@Param("cpf") String cpf);
    
    @Query("SELECT p FROM Paciente p WHERE p.nome LIKE %:nome% AND p.ativo = true")
    List<Paciente> findByNomeContainingAndAtivo(@Param("nome") String nome);
    
    boolean existsByCpf(String cpf);
} 