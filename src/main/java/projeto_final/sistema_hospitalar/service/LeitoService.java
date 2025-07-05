package projeto_final.sistema_hospitalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.sistema_hospitalar.model.Leito;
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.repository.LeitoRepository;
import projeto_final.sistema_hospitalar.repository.PacienteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeitoService {
    
    @Autowired
    private LeitoRepository leitoRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public List<Leito> listarTodos() {
        return leitoRepository.findAll();
    }
    
    public Optional<Leito> buscarPorId(Long id) {
        return leitoRepository.findById(id);
    }
    
    public Optional<Leito> buscarPorNumero(String numeroLeito) {
        return leitoRepository.findByNumeroLeito(numeroLeito);
    }
    
    public List<Leito> buscarPorStatus(Leito.StatusLeito status) {
        return leitoRepository.findByStatus(status);
    }
    
    public List<Leito> buscarPorSetor(String setor) {
        return leitoRepository.findBySetor(setor);
    }
    
    public List<Leito> buscarPorTipo(String tipoLeito) {
        return leitoRepository.findByTipoLeito(tipoLeito);
    }
    
    public List<Leito> buscarLeitosDisponiveis() {
        return leitoRepository.findLeitosDisponiveis();
    }
    
    public List<Leito> buscarLeitosDisponiveisPorSetor(String setor) {
        return leitoRepository.findLeitosDisponiveisPorSetor(setor);
    }
    
    public Optional<Leito> buscarPorPaciente(Long pacienteId) {
        return leitoRepository.findByPacienteId(pacienteId);
    }
    
    public Leito salvar(Leito leito) {
        if (leito.getId() == null) {
            leito.setDataCadastro(LocalDateTime.now());
        }
        leito.setDataAtualizacao(LocalDateTime.now());
        return leitoRepository.save(leito);
    }
    
    public Leito atualizar(Long id, Leito leito) {
        Optional<Leito> leitoExistente = leitoRepository.findById(id);
        if (leitoExistente.isPresent()) {
            Leito leitoAtual = leitoExistente.get();
            leitoAtual.setNumeroLeito(leito.getNumeroLeito());
            leitoAtual.setSetor(leito.getSetor());
            leitoAtual.setTipoLeito(leito.getTipoLeito());
            leitoAtual.setStatus(leito.getStatus());
            leitoAtual.setPaciente(leito.getPaciente());
            leitoAtual.setDataInternacao(leito.getDataInternacao());
            leitoAtual.setDataAlta(leito.getDataAlta());
            leitoAtual.setObservacoes(leito.getObservacoes());
            leitoAtual.setDataAtualizacao(LocalDateTime.now());
            return leitoRepository.save(leitoAtual);
        }
        throw new RuntimeException("Leito não encontrado");
    }
    
    public void deletar(Long id) {
        leitoRepository.deleteById(id);
    }
    
    public Leito internarPaciente(Long leitoId, Long pacienteId) {
        Optional<Leito> leito = leitoRepository.findById(leitoId);
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        
        if (!leito.isPresent()) {
            throw new RuntimeException("Leito não encontrado");
        }
        if (!paciente.isPresent()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        
        Leito leitoAtual = leito.get();
        if (leitoAtual.getStatus() != Leito.StatusLeito.DISPONIVEL) {
            throw new RuntimeException("Leito não está disponível");
        }
        
        leitoAtual.setPaciente(paciente.get());
        leitoAtual.setStatus(Leito.StatusLeito.OCUPADO);
        leitoAtual.setDataInternacao(LocalDateTime.now());
        leitoAtual.setDataAtualizacao(LocalDateTime.now());
        
        return leitoRepository.save(leitoAtual);
    }
    
    public Leito darAltaPaciente(Long leitoId) {
        Optional<Leito> leito = leitoRepository.findById(leitoId);
        if (leito.isPresent()) {
            Leito leitoAtual = leito.get();
            leitoAtual.setStatus(Leito.StatusLeito.DISPONIVEL);
            leitoAtual.setDataAlta(LocalDateTime.now());
            leitoAtual.setDataAtualizacao(LocalDateTime.now());
            return leitoRepository.save(leitoAtual);
        }
        throw new RuntimeException("Leito não encontrado");
    }
    
    public Leito reservarLeito(Long leitoId) {
        Optional<Leito> leito = leitoRepository.findById(leitoId);
        if (leito.isPresent()) {
            Leito leitoAtual = leito.get();
            if (leitoAtual.getStatus() != Leito.StatusLeito.DISPONIVEL) {
                throw new RuntimeException("Leito não está disponível para reserva");
            }
            leitoAtual.setStatus(Leito.StatusLeito.RESERVADO);
            leitoAtual.setDataAtualizacao(LocalDateTime.now());
            return leitoRepository.save(leitoAtual);
        }
        throw new RuntimeException("Leito não encontrado");
    }
    
    public Leito liberarLeito(Long leitoId) {
        Optional<Leito> leito = leitoRepository.findById(leitoId);
        if (leito.isPresent()) {
            Leito leitoAtual = leito.get();
            leitoAtual.setStatus(Leito.StatusLeito.DISPONIVEL);
            leitoAtual.setDataAtualizacao(LocalDateTime.now());
            return leitoRepository.save(leitoAtual);
        }
        throw new RuntimeException("Leito não encontrado");
    }
    
    public Leito colocarEmManutencao(Long leitoId) {
        Optional<Leito> leito = leitoRepository.findById(leitoId);
        if (leito.isPresent()) {
            Leito leitoAtual = leito.get();
            leitoAtual.setStatus(Leito.StatusLeito.EM_MANUTENCAO);
            leitoAtual.setDataAtualizacao(LocalDateTime.now());
            return leitoRepository.save(leitoAtual);
        }
        throw new RuntimeException("Leito não encontrado");
    }
    
    public boolean existePorNumero(String numeroLeito) {
        return leitoRepository.existsByNumeroLeito(numeroLeito);
    }
    
    public long contarLeitosDisponiveis() {
        return leitoRepository.findLeitosDisponiveis().size();
    }
    
    public long contarLeitosOcupados() {
        return leitoRepository.findByStatus(Leito.StatusLeito.OCUPADO).size();
    }
} 