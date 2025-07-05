package projeto_final.sistema_hospitalar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    
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
    private LocalDateTime dataHora;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraFim;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConsulta tipoConsulta;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;
    
    @Column
    private String observacoes;
    
    @Column
    private String sintomas;
    
    @Column
    private String diagnostico;
    
    @Column
    private String prescricao;
    
    @Column
    private String observacoesMedicas;
    
    @Column(nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    public enum TipoConsulta {
        CONSULTA_PRESENCIAL,
        TELEMEDICINA,
        RETORNO,
        EMERGENCIA
    }
    
    public enum StatusConsulta {
        AGENDADA,
        CONFIRMADA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA,
        NAO_COMPARECEU
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