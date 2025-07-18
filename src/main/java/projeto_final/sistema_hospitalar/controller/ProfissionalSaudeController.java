package projeto_final.sistema_hospitalar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.service.ProfissionalSaudeService;
import projeto_final.sistema_hospitalar.dto.ProfissionalCreateDTO;

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
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<ProfissionalSaude>> listarTodos() {
        List<ProfissionalSaude> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
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
    public ResponseEntity<List<ProfissionalSaude>> buscarPorTipo(
            @PathVariable ProfissionalSaude.TipoProfissional tipo) {
        List<ProfissionalSaude> profissionais = profissionalService.buscarPorTipo(tipo);
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<ProfissionalSaude> profissionais = profissionalService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(profissionais);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar novo profissional", description = "Cria um novo profissional de saúde no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso", content = @Content(schema = @Schema(implementation = ProfissionalSaude.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF ou registro já cadastrado")
    })
    public ResponseEntity<ProfissionalSaude> criar(@RequestBody ProfissionalCreateDTO profissionalDTO) {
        try {
            // Validar dados obrigatórios
            if (profissionalDTO.getNome() == null || profissionalDTO.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (profissionalDTO.getCpf() == null || profissionalDTO.getCpf().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (profissionalDTO.getTipoProfissional() == null) {
                return ResponseEntity.badRequest().build();
            }
            if (profissionalDTO.getEspecialidade() == null || profissionalDTO.getEspecialidade().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            ProfissionalSaude profissional = profissionalDTO.toEntity();
            ProfissionalSaude profissionalSalvo = profissionalService.salvar(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(profissionalSalvo);
        } catch (Exception e) {
            System.err.println("Erro ao criar profissional: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfissionalSaude> atualizar(@PathVariable Long id,
            @RequestBody ProfissionalSaude profissional) {
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
    @PreAuthorize("hasRole('ADMIN')")
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