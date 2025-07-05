package projeto_final.sistema_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Long> {
    
    Optional<ProfissionalSaude> findByCpf(String cpf);
    
    Optional<ProfissionalSaude> findByRegistroProfissional(String registroProfissional);
    
    List<ProfissionalSaude> findByAtivoTrue();
    
    List<ProfissionalSaude> findByTipoProfissional(ProfissionalSaude.TipoProfissional tipoProfissional);
    
    List<ProfissionalSaude> findByEspecialidadeContainingIgnoreCase(String especialidade);
    
    @Query("SELECT p FROM ProfissionalSaude p WHERE p.cpf = :cpf AND p.ativo = true")
    Optional<ProfissionalSaude> findByCpfAndAtivo(@Param("cpf") String cpf);
    
    @Query("SELECT p FROM ProfissionalSaude p WHERE p.nome LIKE %:nome% AND p.ativo = true")
    List<ProfissionalSaude> findByNomeContainingAndAtivo(@Param("nome") String nome);
    
    @Query("SELECT p FROM ProfissionalSaude p WHERE p.tipoProfissional = :tipo AND p.ativo = true")
    List<ProfissionalSaude> findByTipoProfissionalAndAtivo(@Param("tipo") ProfissionalSaude.TipoProfissional tipoProfissional);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByRegistroProfissional(String registroProfissional);
} 