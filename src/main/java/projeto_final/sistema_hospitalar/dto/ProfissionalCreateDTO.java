package projeto_final.sistema_hospitalar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import projeto_final.sistema_hospitalar.model.ProfissionalSaude;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de profissional de saúde")
public class ProfissionalCreateDTO {

    @Schema(description = "Nome completo do profissional", example = "Dr. Carlos Santos")
    private String nome;

    @Schema(description = "CPF do profissional", example = "12345678901")
    private String cpf;

    @Schema(description = "Data de nascimento", example = "1985-05-20")
    private LocalDate dataNascimento;

    @Schema(description = "Telefone", example = "(11) 99999-9999")
    private String telefone;

    @Schema(description = "Email", example = "carlos@hospital.com")
    private String email;

    @Schema(description = "Endereço completo", example = "Rua das Flores, 123")
    private String endereco;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado", example = "SP")
    private String estado;

    @Schema(description = "CEP", example = "01234-567")
    private String cep;

    @Schema(description = "Tipo de profissional", example = "MEDICO")
    private ProfissionalSaude.TipoProfissional tipoProfissional;

    @Schema(description = "Especialidade", example = "Cardiologia")
    private String especialidade;

    @Schema(description = "Registro profissional", example = "CRM12345")
    private String registroProfissional;

    @Schema(description = "Data de formação", example = "2010-12-15")
    private LocalDate dataFormacao;

    @Schema(description = "Instituição de formação", example = "Universidade de São Paulo")
    private String instituicaoFormacao;

    @Schema(description = "Observações", example = "Especialista em cardiologia intervencionista")
    private String observacoes;

    /**
     * Converte o DTO para uma entidade ProfissionalSaude
     * 
     * @return ProfissionalSaude com os dados do DTO
     */
    public ProfissionalSaude toEntity() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome(this.nome);
        profissional.setCpf(this.cpf);
        profissional.setDataNascimento(this.dataNascimento);
        profissional.setTelefone(this.telefone);
        profissional.setEmail(this.email);
        profissional.setEndereco(this.endereco);
        profissional.setCidade(this.cidade);
        profissional.setEstado(this.estado);
        profissional.setCep(this.cep);
        profissional.setTipoProfissional(this.tipoProfissional);
        profissional.setEspecialidade(this.especialidade);
        profissional.setRegistroProfissional(this.registroProfissional);
        profissional.setDataFormacao(this.dataFormacao);
        profissional.setInstituicaoFormacao(this.instituicaoFormacao);
        profissional.setObservacoes(this.observacoes);
        return profissional;
    }
}