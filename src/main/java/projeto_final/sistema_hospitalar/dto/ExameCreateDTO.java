package projeto_final.sistema_hospitalar.dto;

import lombok.Data;
import projeto_final.sistema_hospitalar.model.Exame;

import java.time.LocalDateTime;

@Data
public class ExameCreateDTO {
    private Long pacienteId;
    private Long profissionalId;
    private String nomeExame;
    private String tipoExame;
    private LocalDateTime dataHoraAgendamento;
    private String observacoes;
    private String observacoesMedicas;

    public Exame toEntity() {
        Exame exame = new Exame();
        exame.setTipoExame(this.tipoExame);
        exame.setDataHoraAgendamento(this.dataHoraAgendamento);
        exame.setObservacoes(this.observacoes);
        exame.setObservacoesMedicas(this.observacoesMedicas);
        exame.setStatus(Exame.StatusExame.AGENDADO);
        return exame;
    }
}