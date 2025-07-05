package projeto_final.sistema_hospitalar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.Exame;
import projeto_final.sistema_hospitalar.service.ExameService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exames")
@CrossOrigin(origins = "*")
@Tag(name = "Exames", description = "API para gerenciamento de exames laboratoriais e de imagem")
public class ExameController {

    @Autowired
    private ExameService exameService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'TECNICO')")
    public ResponseEntity<List<Exame>> listarTodos() {
        List<Exame> exames = exameService.listarTodos();
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exame> buscarPorId(@PathVariable Long id) {
        Optional<Exame> exame = exameService.buscarPorId(id);
        return exame.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Exame>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<Exame> exames = exameService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<Exame>> buscarPorProfissional(@PathVariable Long profissionalId) {
        List<Exame> exames = exameService.buscarPorProfissional(profissionalId);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Exame>> buscarPorStatus(@PathVariable Exame.StatusExame status) {
        List<Exame> exames = exameService.buscarPorStatus(status);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/tipo/{tipoExame}")
    public ResponseEntity<List<Exame>> buscarPorTipo(@PathVariable String tipoExame) {
        List<Exame> exames = exameService.buscarPorTipo(tipoExame);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/paciente/{pacienteId}/data")
    public ResponseEntity<List<Exame>> buscarPorPacienteEData(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio) {
        List<Exame> exames = exameService.buscarPorPacienteEData(pacienteId, dataInicio);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/profissional/{profissionalId}/data")
    public ResponseEntity<List<Exame>> buscarPorProfissionalEData(
            @PathVariable Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio) {
        List<Exame> exames = exameService.buscarPorProfissionalEData(profissionalId, dataInicio);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Exame>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Exame> exames = exameService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(exames);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<Exame> criar(@RequestBody Exame exame) {
        try {
            Exame exameSalvo = exameService.salvar(exame);
            return ResponseEntity.status(HttpStatus.CREATED).body(exameSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exame> atualizar(@PathVariable Long id, @RequestBody Exame exame) {
        try {
            Exame exameAtualizado = exameService.atualizar(id, exame);
            return ResponseEntity.ok(exameAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            exameService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/realizar")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Exame> realizarExame(@PathVariable Long id) {
        try {
            Exame exame = exameService.realizarExame(id);
            return ResponseEntity.ok(exame);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/analisar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'TECNICO')")
    public ResponseEntity<Exame> analisarExame(@PathVariable Long id) {
        try {
            Exame exame = exameService.analisarExame(id);
            return ResponseEntity.ok(exame);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<Exame> concluirExame(@PathVariable Long id) {
        try {
            Exame exame = exameService.concluirExame(id);
            return ResponseEntity.ok(exame);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Exame> cancelarExame(@PathVariable Long id) {
        try {
            Exame exame = exameService.cancelarExame(id);
            return ResponseEntity.ok(exame);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}