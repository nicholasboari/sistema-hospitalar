package projeto_final.sistema_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto_final.sistema_hospitalar.model.Exame;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    
    List<Exame> findByPacienteId(Long pacienteId);
    
    List<Exame> findByProfissionalId(Long profissionalId);
    
    List<Exame> findByStatus(Exame.StatusExame status);
    
    List<Exame> findByTipoExame(String tipoExame);
    
    @Query("SELECT e FROM Exame e WHERE e.paciente.id = :pacienteId AND e.dataHoraAgendamento >= :dataInicio ORDER BY e.dataHoraAgendamento DESC")
    List<Exame> findByPacienteIdAndDataHoraAfter(@Param("pacienteId") Long pacienteId, @Param("dataInicio") LocalDateTime dataInicio);
    
    @Query("SELECT e FROM Exame e WHERE e.profissional.id = :profissionalId AND e.dataHoraAgendamento >= :dataInicio ORDER BY e.dataHoraAgendamento")
    List<Exame> findByProfissionalIdAndDataHoraAfter(@Param("profissionalId") Long profissionalId, @Param("dataInicio") LocalDateTime dataInicio);
    
    @Query("SELECT e FROM Exame e WHERE e.dataHoraAgendamento BETWEEN :dataInicio AND :dataFim ORDER BY e.dataHoraAgendamento")
    List<Exame> findByDataHoraBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM Exame e WHERE e.paciente.id = :pacienteId AND e.status = :status ORDER BY e.dataHoraAgendamento DESC")
    List<Exame> findByPacienteIdAndStatus(@Param("pacienteId") Long pacienteId, @Param("status") Exame.StatusExame status);
} 