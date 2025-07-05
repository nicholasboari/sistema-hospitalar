package projeto_final.sistema_hospitalar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.Consulta;
import projeto_final.sistema_hospitalar.service.ConsultaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
@Tag(name = "Consultas", description = "API para gerenciamento de consultas e agendamentos")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'ENFERMEIRO')")
    public ResponseEntity<List<Consulta>> listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaService.buscarPorId(id);
        return consulta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<Consulta>> buscarPorProfissional(@PathVariable Long profissionalId) {
        List<Consulta> consultas = consultaService.buscarPorProfissional(profissionalId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Consulta>> buscarPorStatus(@PathVariable Consulta.StatusConsulta status) {
        List<Consulta> consultas = consultaService.buscarPorStatus(status);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Consulta>> buscarPorTipo(@PathVariable Consulta.TipoConsulta tipo) {
        List<Consulta> consultas = consultaService.buscarPorTipo(tipo);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/paciente/{pacienteId}/data")
    public ResponseEntity<List<Consulta>> buscarPorPacienteEData(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio) {
        List<Consulta> consultas = consultaService.buscarPorPacienteEData(pacienteId, dataInicio);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/profissional/{profissionalId}/data")
    public ResponseEntity<List<Consulta>> buscarPorProfissionalEData(
            @PathVariable Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio) {
        List<Consulta> consultas = consultaService.buscarPorProfissionalEData(profissionalId, dataInicio);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Consulta>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Consulta> consultas = consultaService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/profissional/{profissionalId}/periodo")
    public ResponseEntity<List<Consulta>> buscarPorProfissionalEPeriodo(
            @PathVariable Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Consulta> consultas = consultaService.buscarPorProfissionalEPeriodo(profissionalId, dataInicio, dataFim);
        return ResponseEntity.ok(consultas);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'ENFERMEIRO')")
    public ResponseEntity<Consulta> criar(@RequestBody Consulta consulta) {
        try {
            Consulta consultaSalva = consultaService.salvar(consulta);
            return ResponseEntity.status(HttpStatus.CREATED).body(consultaSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'ENFERMEIRO')")
    public ResponseEntity<Consulta> atualizar(@PathVariable Long id, @RequestBody Consulta consulta) {
        try {
            Consulta consultaAtualizada = consultaService.atualizar(id, consulta);
            return ResponseEntity.ok(consultaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            consultaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Consulta> confirmarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.confirmarConsulta(id);
            return ResponseEntity.ok(consulta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.cancelarConsulta(id);
            return ResponseEntity.ok(consulta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<Consulta> iniciarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.iniciarConsulta(id);
            return ResponseEntity.ok(consulta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Consulta> finalizarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.finalizarConsulta(id);
            return ResponseEntity.ok(consulta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}