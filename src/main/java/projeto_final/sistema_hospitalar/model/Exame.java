package projeto_final.sistema_hospitalar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exames")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalSaude profissional;

    @Column(nullable = false)
    private String tipoExame;

    @Column(nullable = false)
    private LocalDateTime dataHoraAgendamento;

    @Column
    private LocalDateTime dataHoraRealizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExame status;

    @Column
    private String observacoes;

    @Column
    private String resultado;

    @Column
    private String laudo;

    @Column
    private String observacoesMedicas;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Column
    private LocalDateTime dataAtualizacao;

    public enum StatusExame {
        AGENDADO,
        REALIZADO,
        ANALISADO,
        CONCLUIDO,
        CANCELADO
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}