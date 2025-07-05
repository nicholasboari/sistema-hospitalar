package projeto_final.sistema_hospitalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.Paciente;
import projeto_final.sistema_hospitalar.service.PacienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
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
    public ResponseEntity<Paciente> criar(@RequestBody Paciente paciente) {
        try {
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