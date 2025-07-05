package projeto_final.sistema_hospitalar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "leitos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String numeroLeito;
    
    @Column(nullable = false)
    private String setor;
    
    @Column(nullable = false)
    private String tipoLeito;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLeito status;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    @Column
    private LocalDateTime dataInternacao;
    
    @Column
    private LocalDateTime dataAlta;
    
    @Column
    private String observacoes;
    
    @Column(nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    public enum StatusLeito {
        DISPONIVEL,
        OCUPADO,
        EM_MANUTENCAO,
        RESERVADO
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