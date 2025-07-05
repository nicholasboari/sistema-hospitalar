package projeto_final.sistema_hospitalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.Leito;
import projeto_final.sistema_hospitalar.service.LeitoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leitos")
@CrossOrigin(origins = "*")
public class LeitoController {
    
    @Autowired
    private LeitoService leitoService;
    
    @GetMapping
    public ResponseEntity<List<Leito>> listarTodos() {
        List<Leito> leitos = leitoService.listarTodos();
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Leito> buscarPorId(@PathVariable Long id) {
        Optional<Leito> leito = leitoService.buscarPorId(id);
        return leito.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/numero/{numeroLeito}")
    public ResponseEntity<Leito> buscarPorNumero(@PathVariable String numeroLeito) {
        Optional<Leito> leito = leitoService.buscarPorNumero(numeroLeito);
        return leito.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Leito>> buscarPorStatus(@PathVariable Leito.StatusLeito status) {
        List<Leito> leitos = leitoService.buscarPorStatus(status);
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<Leito>> buscarPorSetor(@PathVariable String setor) {
        List<Leito> leitos = leitoService.buscarPorSetor(setor);
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/tipo/{tipoLeito}")
    public ResponseEntity<List<Leito>> buscarPorTipo(@PathVariable String tipoLeito) {
        List<Leito> leitos = leitoService.buscarPorTipo(tipoLeito);
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Leito>> buscarLeitosDisponiveis() {
        List<Leito> leitos = leitoService.buscarLeitosDisponiveis();
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/disponiveis/setor/{setor}")
    public ResponseEntity<List<Leito>> buscarLeitosDisponiveisPorSetor(@PathVariable String setor) {
        List<Leito> leitos = leitoService.buscarLeitosDisponiveisPorSetor(setor);
        return ResponseEntity.ok(leitos);
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Leito> buscarPorPaciente(@PathVariable Long pacienteId) {
        Optional<Leito> leito = leitoService.buscarPorPaciente(pacienteId);
        return leito.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Leito> criar(@RequestBody Leito leito) {
        try {
            Leito leitoSalvo = leitoService.salvar(leito);
            return ResponseEntity.status(HttpStatus.CREATED).body(leitoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Leito> atualizar(@PathVariable Long id, @RequestBody Leito leito) {
        try {
            Leito leitoAtualizado = leitoService.atualizar(id, leito);
            return ResponseEntity.ok(leitoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            leitoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{leitoId}/internar/{pacienteId}")
    public ResponseEntity<Leito> internarPaciente(@PathVariable Long leitoId, @PathVariable Long pacienteId) {
        try {
            Leito leito = leitoService.internarPaciente(leitoId, pacienteId);
            return ResponseEntity.ok(leito);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/alta")
    public ResponseEntity<Leito> darAltaPaciente(@PathVariable Long id) {
        try {
            Leito leito = leitoService.darAltaPaciente(id);
            return ResponseEntity.ok(leito);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/reservar")
    public ResponseEntity<Leito> reservarLeito(@PathVariable Long id) {
        try {
            Leito leito = leitoService.reservarLeito(id);
            return ResponseEntity.ok(leito);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/liberar")
    public ResponseEntity<Leito> liberarLeito(@PathVariable Long id) {
        try {
            Leito leito = leitoService.liberarLeito(id);
            return ResponseEntity.ok(leito);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<Leito> colocarEmManutencao(@PathVariable Long id) {
        try {
            Leito leito = leitoService.colocarEmManutencao(id);
            return ResponseEntity.ok(leito);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/contar/disponiveis")
    public ResponseEntity<Long> contarLeitosDisponiveis() {
        long quantidade = leitoService.contarLeitosDisponiveis();
        return ResponseEntity.ok(quantidade);
    }
    
    @GetMapping("/contar/ocupados")
    public ResponseEntity<Long> contarLeitosOcupados() {
        long quantidade = leitoService.contarLeitosOcupados();
        return ResponseEntity.ok(quantidade);
    }
    
    @GetMapping("/existe-numero/{numeroLeito}")
    public ResponseEntity<Boolean> existePorNumero(@PathVariable String numeroLeito) {
        boolean existe = leitoService.existePorNumero(numeroLeito);
        return ResponseEntity.ok(existe);
    }
} 