package projeto_final.sistema_hospitalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.sistema_hospitalar.dto.ExameCreateDTO;
import projeto_final.sistema_hospitalar.model.Exame;
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.repository.ExameRepository;
import projeto_final.sistema_hospitalar.repository.PacienteRepository;
import projeto_final.sistema_hospitalar.repository.ProfissionalSaudeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExameService {

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    public List<Exame> listarTodos() {
        return exameRepository.findAll();
    }

    public Optional<Exame> buscarPorId(Long id) {
        return exameRepository.findById(id);
    }

    public List<Exame> buscarPorPaciente(Long pacienteId) {
        return exameRepository.findByPacienteId(pacienteId);
    }

    public List<Exame> buscarPorProfissional(Long profissionalId) {
        return exameRepository.findByProfissionalId(profissionalId);
    }

    public List<Exame> buscarPorStatus(Exame.StatusExame status) {
        return exameRepository.findByStatus(status);
    }

    public List<Exame> buscarPorTipo(String tipoExame) {
        return exameRepository.findByTipoExame(tipoExame);
    }

    public List<Exame> buscarPorPacienteEData(Long pacienteId, LocalDateTime dataInicio) {
        return exameRepository.findByPacienteIdAndDataHoraAfter(pacienteId, dataInicio);
    }

    public List<Exame> buscarPorProfissionalEData(Long profissionalId, LocalDateTime dataInicio) {
        return exameRepository.findByProfissionalIdAndDataHoraAfter(profissionalId, dataInicio);
    }

    public List<Exame> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return exameRepository.findByDataHoraBetween(dataInicio, dataFim);
    }

    public Exame salvar(Exame exame) {
        // Validar se paciente e profissional existem
        Optional<Paciente> paciente = pacienteRepository.findById(exame.getPaciente().getId());
        Optional<ProfissionalSaude> profissional = profissionalRepository.findById(exame.getProfissional().getId());

        if (!paciente.isPresent()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        if (!profissional.isPresent()) {
            throw new RuntimeException("Profissional não encontrado");
        }

        exame.setPaciente(paciente.get());
        exame.setProfissional(profissional.get());

        if (exame.getId() == null) {
            exame.setDataCadastro(LocalDateTime.now());
        }
        exame.setDataAtualizacao(LocalDateTime.now());

        return exameRepository.save(exame);
    }

    public Exame criarExame(ExameCreateDTO dto) {
        // Validar se paciente e profissional existem
        Optional<Paciente> paciente = pacienteRepository.findById(dto.getPacienteId());
        Optional<ProfissionalSaude> profissional = profissionalRepository.findById(dto.getProfissionalId());

        if (!paciente.isPresent()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        if (!profissional.isPresent()) {
            throw new RuntimeException("Profissional não encontrado");
        }

        Exame exame = dto.toEntity();
        exame.setPaciente(paciente.get());
        exame.setProfissional(profissional.get());
        exame.setDataCadastro(LocalDateTime.now());
        exame.setDataAtualizacao(LocalDateTime.now());

        return exameRepository.save(exame);
    }

    public Exame atualizar(Long id, Exame exame) {
        Optional<Exame> exameExistente = exameRepository.findById(id);
        if (exameExistente.isPresent()) {
            Exame exameAtual = exameExistente.get();
            exameAtual.setPaciente(exame.getPaciente());
            exameAtual.setProfissional(exame.getProfissional());
            exameAtual.setTipoExame(exame.getTipoExame());
            exameAtual.setDataHoraAgendamento(exame.getDataHoraAgendamento());
            exameAtual.setDataHoraRealizacao(exame.getDataHoraRealizacao());
            exameAtual.setStatus(exame.getStatus());
            exameAtual.setObservacoes(exame.getObservacoes());
            exameAtual.setResultado(exame.getResultado());
            exameAtual.setLaudo(exame.getLaudo());
            exameAtual.setObservacoesMedicas(exame.getObservacoesMedicas());
            exameAtual.setDataAtualizacao(LocalDateTime.now());
            return exameRepository.save(exameAtual);
        }
        throw new RuntimeException("Exame não encontrado");
    }

    public void deletar(Long id) {
        exameRepository.deleteById(id);
    }

    public Exame realizarExame(Long id) {
        Optional<Exame> exame = exameRepository.findById(id);
        if (exame.isPresent()) {
            Exame exameAtual = exame.get();
            exameAtual.setStatus(Exame.StatusExame.REALIZADO);
            exameAtual.setDataHoraRealizacao(LocalDateTime.now());
            exameAtual.setDataAtualizacao(LocalDateTime.now());
            return exameRepository.save(exameAtual);
        }
        throw new RuntimeException("Exame não encontrado");
    }

    public Exame analisarExame(Long id) {
        Optional<Exame> exame = exameRepository.findById(id);
        if (exame.isPresent()) {
            Exame exameAtual = exame.get();
            exameAtual.setStatus(Exame.StatusExame.ANALISADO);
            exameAtual.setDataAtualizacao(LocalDateTime.now());
            return exameRepository.save(exameAtual);
        }
        throw new RuntimeException("Exame não encontrado");
    }

    public Exame concluirExame(Long id) {
        Optional<Exame> exame = exameRepository.findById(id);
        if (exame.isPresent()) {
            Exame exameAtual = exame.get();
            exameAtual.setStatus(Exame.StatusExame.CONCLUIDO);
            exameAtual.setDataAtualizacao(LocalDateTime.now());
            return exameRepository.save(exameAtual);
        }
        throw new RuntimeException("Exame não encontrado");
    }

    public Exame cancelarExame(Long id) {
        Optional<Exame> exame = exameRepository.findById(id);
        if (exame.isPresent()) {
            Exame exameAtual = exame.get();
            exameAtual.setStatus(Exame.StatusExame.CANCELADO);
            exameAtual.setDataAtualizacao(LocalDateTime.now());
            return exameRepository.save(exameAtual);
        }
        throw new RuntimeException("Exame não encontrado");
    }
}