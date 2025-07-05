package projeto_final.sistema_hospitalar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profissionais_saude")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProfissional tipoProfissional;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false, unique = true)
    private String registroProfissional;

    @Column(nullable = false)
    private LocalDate dataFormacao;

    @Column
    private String instituicaoFormacao;

    @Column
    private String observacoes;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Column
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    public enum TipoProfissional {
        MEDICO,
        ENFERMEIRO,
        TECNICO,
        FISIOTERAPEUTA,
        PSICOLOGO,
        NUTRICIONISTA
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