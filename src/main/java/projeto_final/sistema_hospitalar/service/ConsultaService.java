package projeto_final.sistema_hospitalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.sistema_hospitalar.model.Consulta;
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.repository.ConsultaRepository;
import projeto_final.sistema_hospitalar.repository.PacienteRepository;
import projeto_final.sistema_hospitalar.repository.ProfissionalSaudeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConsultaService {
    
    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;
    
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }
    
    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }
    
    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }
    
    public List<Consulta> buscarPorProfissional(Long profissionalId) {
        return consultaRepository.findByProfissionalId(profissionalId);
    }
    
    public List<Consulta> buscarPorStatus(Consulta.StatusConsulta status) {
        return consultaRepository.findByStatus(status);
    }
    
    public List<Consulta> buscarPorTipo(Consulta.TipoConsulta tipo) {
        return consultaRepository.findByTipoConsulta(tipo);
    }
    
    public List<Consulta> buscarPorPacienteEData(Long pacienteId, LocalDateTime dataInicio) {
        return consultaRepository.findByPacienteIdAndDataHoraAfter(pacienteId, dataInicio);
    }
    
    public List<Consulta> buscarPorProfissionalEData(Long profissionalId, LocalDateTime dataInicio) {
        return consultaRepository.findByProfissionalIdAndDataHoraAfter(profissionalId, dataInicio);
    }
    
    public List<Consulta> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultaRepository.findByDataHoraBetween(dataInicio, dataFim);
    }
    
    public List<Consulta> buscarPorProfissionalEPeriodo(Long profissionalId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultaRepository.findByProfissionalIdAndDataHoraBetween(profissionalId, dataInicio, dataFim);
    }
    
    public Consulta salvar(Consulta consulta) {
        // Validar se paciente e profissional existem
        Optional<Paciente> paciente = pacienteRepository.findById(consulta.getPaciente().getId());
        Optional<ProfissionalSaude> profissional = profissionalRepository.findById(consulta.getProfissional().getId());
        
        if (!paciente.isPresent()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        if (!profissional.isPresent()) {
            throw new RuntimeException("Profissional não encontrado");
        }
        
        consulta.setPaciente(paciente.get());
        consulta.setProfissional(profissional.get());
        
        if (consulta.getId() == null) {
            consulta.setDataCadastro(LocalDateTime.now());
        }
        consulta.setDataAtualizacao(LocalDateTime.now());
        
        return consultaRepository.save(consulta);
    }
    
    public Consulta atualizar(Long id, Consulta consulta) {
        Optional<Consulta> consultaExistente = consultaRepository.findById(id);
        if (consultaExistente.isPresent()) {
            Consulta consultaAtual = consultaExistente.get();
            consultaAtual.setPaciente(consulta.getPaciente());
            consultaAtual.setProfissional(consulta.getProfissional());
            consultaAtual.setDataHora(consulta.getDataHora());
            consultaAtual.setDataHoraFim(consulta.getDataHoraFim());
            consultaAtual.setTipoConsulta(consulta.getTipoConsulta());
            consultaAtual.setStatus(consulta.getStatus());
            consultaAtual.setObservacoes(consulta.getObservacoes());
            consultaAtual.setSintomas(consulta.getSintomas());
            consultaAtual.setDiagnostico(consulta.getDiagnostico());
            consultaAtual.setPrescricao(consulta.getPrescricao());
            consultaAtual.setObservacoesMedicas(consulta.getObservacoesMedicas());
            consultaAtual.setDataAtualizacao(LocalDateTime.now());
            return consultaRepository.save(consultaAtual);
        }
        throw new RuntimeException("Consulta não encontrada");
    }
    
    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }
    
    public Consulta confirmarConsulta(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            Consulta consultaAtual = consulta.get();
            consultaAtual.setStatus(Consulta.StatusConsulta.CONFIRMADA);
            consultaAtual.setDataAtualizacao(LocalDateTime.now());
            return consultaRepository.save(consultaAtual);
        }
        throw new RuntimeException("Consulta não encontrada");
    }
    
    public Consulta cancelarConsulta(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            Consulta consultaAtual = consulta.get();
            consultaAtual.setStatus(Consulta.StatusConsulta.CANCELADA);
            consultaAtual.setDataAtualizacao(LocalDateTime.now());
            return consultaRepository.save(consultaAtual);
        }
        throw new RuntimeException("Consulta não encontrada");
    }
    
    public Consulta iniciarConsulta(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            Consulta consultaAtual = consulta.get();
            consultaAtual.setStatus(Consulta.StatusConsulta.EM_ANDAMENTO);
            consultaAtual.setDataAtualizacao(LocalDateTime.now());
            return consultaRepository.save(consultaAtual);
        }
        throw new RuntimeException("Consulta não encontrada");
    }
    
    public Consulta finalizarConsulta(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            Consulta consultaAtual = consulta.get();
            consultaAtual.setStatus(Consulta.StatusConsulta.CONCLUIDA);
            consultaAtual.setDataAtualizacao(LocalDateTime.now());
            return consultaRepository.save(consultaAtual);
        }
        throw new RuntimeException("Consulta não encontrada");
    }
} 