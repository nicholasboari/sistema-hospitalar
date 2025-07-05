package projeto_final.sistema_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto_final.sistema_hospitalar.model.Leito;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {
    
    Optional<Leito> findByNumeroLeito(String numeroLeito);
    
    List<Leito> findByStatus(Leito.StatusLeito status);
    
    List<Leito> findBySetor(String setor);
    
    List<Leito> findByTipoLeito(String tipoLeito);
    
    @Query("SELECT l FROM Leito l WHERE l.paciente.id = :pacienteId")
    Optional<Leito> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT l FROM Leito l WHERE l.status = :status AND l.setor = :setor")
    List<Leito> findByStatusAndSetor(@Param("status") Leito.StatusLeito status, @Param("setor") String setor);
    
    @Query("SELECT l FROM Leito l WHERE l.status = 'DISPONIVEL'")
    List<Leito> findLeitosDisponiveis();
    
    @Query("SELECT l FROM Leito l WHERE l.status = 'DISPONIVEL' AND l.setor = :setor")
    List<Leito> findLeitosDisponiveisPorSetor(@Param("setor") String setor);
    
    boolean existsByNumeroLeito(String numeroLeito);
} 