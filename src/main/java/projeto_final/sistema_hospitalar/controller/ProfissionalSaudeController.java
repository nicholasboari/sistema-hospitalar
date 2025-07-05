package projeto_final.sistema_hospitalar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.service.ProfissionalSaudeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
@Tag(name = "Profissionais de Saúde", description = "API para gerenciamento de profissionais de saúde")
public class ProfissionalSaudeController {
    
    @Autowired
    private ProfissionalSaudeService profissionalService;
    
    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listarTodos() {
        List<ProfissionalSaude> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> buscarPorId(@PathVariable Long id) {
        Optional<ProfissionalSaude> profissional = profissionalService.buscarPorId(id);
        return profissional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ProfissionalSaude> buscarPorCpf(@PathVariable String cpf) {
        Optional<ProfissionalSaude> profissional = profissionalService.buscarPorCpf(cpf);
        return profissional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/registro/{registro}")
    public ResponseEntity<ProfissionalSaude> buscarPorRegistro(@PathVariable String registro) {
        Optional<ProfissionalSaude> profissional = profissionalService.buscarPorRegistro(registro);
        return profissional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorNome(@PathVariable String nome) {
        List<ProfissionalSaude> profissionais = profissionalService.buscarPorNome(nome);
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorTipo(@PathVariable ProfissionalSaude.TipoProfissional tipo) {
        List<ProfissionalSaude> profissionais = profissionalService.buscarPorTipo(tipo);
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<ProfissionalSaude> profissionais = profissionalService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(profissionais);
    }
    
    @PostMapping
    public ResponseEntity<ProfissionalSaude> criar(@RequestBody ProfissionalSaude profissional) {
        try {
            ProfissionalSaude profissionalSalvo = profissionalService.salvar(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(profissionalSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> atualizar(@PathVariable Long id, @RequestBody ProfissionalSaude profissional) {
        try {
            ProfissionalSaude profissionalAtualizado = profissionalService.atualizar(id, profissional);
            return ResponseEntity.ok(profissionalAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            profissionalService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/contar")
    public ResponseEntity<Long> contarProfissionaisAtivos() {
        long quantidade = profissionalService.contarProfissionaisAtivos();
        return ResponseEntity.ok(quantidade);
    }
    
    @GetMapping("/contar-tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@PathVariable ProfissionalSaude.TipoProfissional tipo) {
        long quantidade = profissionalService.contarPorTipo(tipo);
        return ResponseEntity.ok(quantidade);
    }
    
    @GetMapping("/existe-cpf/{cpf}")
    public ResponseEntity<Boolean> existePorCpf(@PathVariable String cpf) {
        boolean existe = profissionalService.existePorCpf(cpf);
        return ResponseEntity.ok(existe);
    }
    
    @GetMapping("/existe-registro/{registro}")
    public ResponseEntity<Boolean> existePorRegistro(@PathVariable String registro) {
        boolean existe = profissionalService.existePorRegistro(registro);
        return ResponseEntity.ok(existe);
    }
} 