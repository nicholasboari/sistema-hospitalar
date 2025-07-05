package projeto_final.sistema_hospitalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.repository.PacienteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public List<Paciente> listarTodos() {
        return pacienteRepository.findByAtivoTrue();
    }
    
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
    
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpfAndAtivo(cpf);
    }
    
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingAndAtivo(nome);
    }
    
    public Paciente salvar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setDataCadastro(LocalDateTime.now());
        }
        paciente.setDataAtualizacao(LocalDateTime.now());
        paciente.setAtivo(true);
        return pacienteRepository.save(paciente);
    }
    
    public Paciente atualizar(Long id, Paciente paciente) {
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(id);
        if (pacienteExistente.isPresent()) {
            Paciente pacienteAtual = pacienteExistente.get();
            pacienteAtual.setNome(paciente.getNome());
            pacienteAtual.setCpf(paciente.getCpf());
            pacienteAtual.setDataNascimento(paciente.getDataNascimento());
            pacienteAtual.setTelefone(paciente.getTelefone());
            pacienteAtual.setEmail(paciente.getEmail());
            pacienteAtual.setEndereco(paciente.getEndereco());
            pacienteAtual.setCidade(paciente.getCidade());
            pacienteAtual.setEstado(paciente.getEstado());
            pacienteAtual.setCep(paciente.getCep());
            pacienteAtual.setNomeResponsavel(paciente.getNomeResponsavel());
            pacienteAtual.setTelefoneResponsavel(paciente.getTelefoneResponsavel());
            pacienteAtual.setTipoSanguineo(paciente.getTipoSanguineo());
            pacienteAtual.setAlergias(paciente.getAlergias());
            pacienteAtual.setObservacoes(paciente.getObservacoes());
            pacienteAtual.setDataAtualizacao(LocalDateTime.now());
            return pacienteRepository.save(pacienteAtual);
        }
        throw new RuntimeException("Paciente não encontrado");
    }
    
    public void deletar(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            Paciente pacienteAtual = paciente.get();
            pacienteAtual.setAtivo(false);
            pacienteAtual.setDataAtualizacao(LocalDateTime.now());
            pacienteRepository.save(pacienteAtual);
        }
    }
    
    public boolean existePorCpf(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }
    
    public long contarPacientesAtivos() {
        return pacienteRepository.findByAtivoTrue().size();
    }
} 