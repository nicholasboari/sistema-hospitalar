package projeto_final.sistema_hospitalar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto_final.sistema_hospitalar.model.Paciente;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de paciente")
public class PacienteCreateDTO {

    @Schema(description = "Nome completo do paciente", example = "João Silva")
    private String nome;

    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Data de nascimento", example = "1990-01-01")
    private LocalDate dataNascimento;

    @Schema(description = "Telefone do paciente", example = "(11) 99999-9999")
    private String telefone;

    @Schema(description = "Email do paciente", example = "joao@email.com")
    private String email;

    @Schema(description = "Endereço completo", example = "Rua das Flores, 123")
    private String endereco;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado", example = "SP")
    private String estado;

    @Schema(description = "CEP", example = "01234-567")
    private String cep;

    @Schema(description = "Nome do responsável (opcional)", example = "Maria Silva")
    private String nomeResponsavel;

    @Schema(description = "Telefone do responsável (opcional)", example = "(11) 88888-8888")
    private String telefoneResponsavel;

    @Schema(description = "Tipo sanguíneo", example = "O+")
    private String tipoSanguineo;

    @Schema(description = "Alergias conhecidas", example = "Penicilina")
    private String alergias;

    @Schema(description = "Observações médicas", example = "Paciente com histórico de alergias")
    private String observacoes;

    /**
     * Converte o DTO para uma entidade Paciente
     * 
     * @return Paciente com os dados do DTO
     */
    public Paciente toEntity() {
        Paciente paciente = new Paciente();
        paciente.setNome(this.nome);
        paciente.setCpf(this.cpf);
        paciente.setDataNascimento(this.dataNascimento);
        paciente.setTelefone(this.telefone);
        paciente.setEmail(this.email);
        paciente.setEndereco(this.endereco);
        paciente.setCidade(this.cidade);
        paciente.setEstado(this.estado);
        paciente.setCep(this.cep);
        paciente.setNomeResponsavel(this.nomeResponsavel);
        paciente.setTelefoneResponsavel(this.telefoneResponsavel);
        paciente.setTipoSanguineo(this.tipoSanguineo);
        paciente.setAlergias(this.alergias);
        paciente.setObservacoes(this.observacoes);
        return paciente;
    }
}