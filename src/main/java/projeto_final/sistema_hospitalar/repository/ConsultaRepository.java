package projeto_final.sistema_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto_final.sistema_hospitalar.model.Consulta;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByPacienteId(Long pacienteId);
    
    List<Consulta> findByProfissionalId(Long profissionalId);
    
    List<Consulta> findByStatus(Consulta.StatusConsulta status);
    
    List<Consulta> findByTipoConsulta(Consulta.TipoConsulta tipoConsulta);
    
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.dataHora >= :dataInicio ORDER BY c.dataHora DESC")
    List<Consulta> findByPacienteIdAndDataHoraAfter(@Param("pacienteId") Long pacienteId, @Param("dataInicio") LocalDateTime dataInicio);
    
    @Query("SELECT c FROM Consulta c WHERE c.profissional.id = :profissionalId AND c.dataHora >= :dataInicio ORDER BY c.dataHora")
    List<Consulta> findByProfissionalIdAndDataHoraAfter(@Param("profissionalId") Long profissionalId, @Param("dataInicio") LocalDateTime dataInicio);
    
    @Query("SELECT c FROM Consulta c WHERE c.dataHora BETWEEN :dataInicio AND :dataFim ORDER BY c.dataHora")
    List<Consulta> findByDataHoraBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM Consulta c WHERE c.profissional.id = :profissionalId AND c.dataHora BETWEEN :dataInicio AND :dataFim ORDER BY c.dataHora")
    List<Consulta> findByProfissionalIdAndDataHoraBetween(@Param("profissionalId") Long profissionalId, @Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.status = :status ORDER BY c.dataHora DESC")
    List<Consulta> findByPacienteIdAndStatus(@Param("pacienteId") Long pacienteId, @Param("status") Consulta.StatusConsulta status);
} 