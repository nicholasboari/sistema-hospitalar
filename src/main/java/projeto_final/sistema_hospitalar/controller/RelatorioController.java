package projeto_final.sistema_hospitalar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;
import projeto_final.sistema_hospitalar.service.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
@Tag(name = "Relatórios", description = "API para relatórios e estatísticas do sistema")
public class RelatorioController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfissionalSaudeService profissionalService;

    @Autowired
    private LeitoService leitoService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estatísticas gerais
        dashboard.put("totalPacientes", pacienteService.contarPacientesAtivos());
        dashboard.put("totalProfissionais", profissionalService.contarProfissionaisAtivos());
        dashboard.put("leitosDisponiveis", leitoService.contarLeitosDisponiveis());
        dashboard.put("leitosOcupados", leitoService.contarLeitosOcupados());

        // Contadores por tipo de profissional
        Map<String, Long> profissionaisPorTipo = new HashMap<>();
        for (ProfissionalSaude.TipoProfissional tipo : ProfissionalSaude.TipoProfissional.values()) {
            profissionaisPorTipo.put(tipo.name(), profissionalService.contarPorTipo(tipo));
        }
        dashboard.put("profissionaisPorTipo", profissionaisPorTipo);

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/pacientes/estatisticas")
    public ResponseEntity<Map<String, Object>> estatisticasPacientes() {
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("totalPacientes", pacienteService.contarPacientesAtivos());
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/profissionais/estatisticas")
    public ResponseEntity<Map<String, Object>> estatisticasProfissionais() {
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("totalProfissionais", profissionalService.contarProfissionaisAtivos());

        Map<String, Long> porTipo = new HashMap<>();
        for (ProfissionalSaude.TipoProfissional tipo : ProfissionalSaude.TipoProfissional.values()) {
            porTipo.put(tipo.name(), profissionalService.contarPorTipo(tipo));
        }
        estatisticas.put("porTipo", porTipo);

        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/leitos/estatisticas")
    public ResponseEntity<Map<String, Object>> estatisticasLeitos() {
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("leitosDisponiveis", leitoService.contarLeitosDisponiveis());
        estatisticas.put("leitosOcupados", leitoService.contarLeitosOcupados());
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/ocupacao-leitos")
    public ResponseEntity<Map<String, Object>> ocupacaoLeitos() {
        Map<String, Object> ocupacao = new HashMap<>();
        long disponiveis = leitoService.contarLeitosDisponiveis();
        long ocupados = leitoService.contarLeitosOcupados();
        long total = disponiveis + ocupados;

        if (total > 0) {
            double percentualOcupacao = (double) ocupados / total * 100;
            ocupacao.put("percentualOcupacao", Math.round(percentualOcupacao * 100.0) / 100.0);
        } else {
            ocupacao.put("percentualOcupacao", 0.0);
        }

        ocupacao.put("disponiveis", disponiveis);
        ocupacao.put("ocupados", ocupados);
        ocupacao.put("total", total);

        return ResponseEntity.ok(ocupacao);
    }
}