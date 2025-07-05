package projeto_final.sistema_hospitalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.repository.ProfissionalSaudeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfissionalSaudeService {
    
    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;
    
    public List<ProfissionalSaude> listarTodos() {
        return profissionalRepository.findByAtivoTrue();
    }
    
    public Optional<ProfissionalSaude> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }
    
    public Optional<ProfissionalSaude> buscarPorCpf(String cpf) {
        return profissionalRepository.findByCpfAndAtivo(cpf);
    }
    
    public Optional<ProfissionalSaude> buscarPorRegistro(String registro) {
        return profissionalRepository.findByRegistroProfissional(registro);
    }
    
    public List<ProfissionalSaude> buscarPorNome(String nome) {
        return profissionalRepository.findByNomeContainingAndAtivo(nome);
    }
    
    public List<ProfissionalSaude> buscarPorTipo(ProfissionalSaude.TipoProfissional tipo) {
        return profissionalRepository.findByTipoProfissionalAndAtivo(tipo);
    }
    
    public List<ProfissionalSaude> buscarPorEspecialidade(String especialidade) {
        return profissionalRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }
    
    public ProfissionalSaude salvar(ProfissionalSaude profissional) {
        if (profissional.getId() == null) {
            profissional.setDataCadastro(LocalDateTime.now());
        }
        profissional.setDataAtualizacao(LocalDateTime.now());
        profissional.setAtivo(true);
        return profissionalRepository.save(profissional);
    }
    
    public ProfissionalSaude atualizar(Long id, ProfissionalSaude profissional) {
        Optional<ProfissionalSaude> profissionalExistente = profissionalRepository.findById(id);
        if (profissionalExistente.isPresent()) {
            ProfissionalSaude profissionalAtual = profissionalExistente.get();
            profissionalAtual.setNome(profissional.getNome());
            profissionalAtual.setCpf(profissional.getCpf());
            profissionalAtual.setDataNascimento(profissional.getDataNascimento());
            profissionalAtual.setTelefone(profissional.getTelefone());
            profissionalAtual.setEmail(profissional.getEmail());
            profissionalAtual.setEndereco(profissional.getEndereco());
            profissionalAtual.setCidade(profissional.getCidade());
            profissionalAtual.setEstado(profissional.getEstado());
            profissionalAtual.setCep(profissional.getCep());
            profissionalAtual.setTipoProfissional(profissional.getTipoProfissional());
            profissionalAtual.setEspecialidade(profissional.getEspecialidade());
            profissionalAtual.setRegistroProfissional(profissional.getRegistroProfissional());
            profissionalAtual.setDataFormacao(profissional.getDataFormacao());
            profissionalAtual.setInstituicaoFormacao(profissional.getInstituicaoFormacao());
            profissionalAtual.setObservacoes(profissional.getObservacoes());
            profissionalAtual.setDataAtualizacao(LocalDateTime.now());
            return profissionalRepository.save(profissionalAtual);
        }
        throw new RuntimeException("Profissional não encontrado");
    }
    
    public void deletar(Long id) {
        Optional<ProfissionalSaude> profissional = profissionalRepository.findById(id);
        if (profissional.isPresent()) {
            ProfissionalSaude profissionalAtual = profissional.get();
            profissionalAtual.setAtivo(false);
            profissionalAtual.setDataAtualizacao(LocalDateTime.now());
            profissionalRepository.save(profissionalAtual);
        }
    }
    
    public boolean existePorCpf(String cpf) {
        return profissionalRepository.existsByCpf(cpf);
    }
    
    public boolean existePorRegistro(String registro) {
        return profissionalRepository.existsByRegistroProfissional(registro);
    }
    
    public long contarProfissionaisAtivos() {
        return profissionalRepository.findByAtivoTrue().size();
    }
    
    public long contarPorTipo(ProfissionalSaude.TipoProfissional tipo) {
        return profissionalRepository.findByTipoProfissionalAndAtivo(tipo).size();
    }
} 