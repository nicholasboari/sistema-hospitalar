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
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.service.PacienteService;
import projeto_final.sistema_hospitalar.dto.PacienteCreateDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
@Tag(name = "Pacientes", description = "API para gerenciamento de pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @Operation(summary = "Listar todos os pacientes", description = "Retorna uma lista de todos os pacientes ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacientes encontrados com sucesso", content = @Content(schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum paciente encontrado")
    })
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso", content = @Content(schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<Paciente> buscarPorId(
            @Parameter(description = "ID do paciente") @PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> buscarPorCpf(@PathVariable String cpf) {
        Optional<Paciente> paciente = pacienteService.buscarPorCpf(cpf);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Paciente>> buscarPorNome(@PathVariable String nome) {
        List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping
    @Operation(summary = "Criar novo paciente", description = "Cria um novo paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso", content = @Content(schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Paciente> criar(@RequestBody PacienteCreateDTO pacienteDTO) {
        try {
            // Converter DTO para entidade usando o método toEntity()
            Paciente paciente = pacienteDTO.toEntity();

            Paciente pacienteSalvo = pacienteService.salvar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);
            return ResponseEntity.ok(pacienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pacienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarPacientesAtivos() {
        long quantidade = pacienteService.contarPacientesAtivos();
        return ResponseEntity.ok(quantidade);
    }

    @GetMapping("/existe-cpf/{cpf}")
    public ResponseEntity<Boolean> existePorCpf(@PathVariable String cpf) {
        boolean existe = pacienteService.existePorCpf(cpf);
        return ResponseEntity.ok(existe);
    }
}