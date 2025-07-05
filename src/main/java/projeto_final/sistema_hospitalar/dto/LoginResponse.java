package projeto_final.sistema_hospitalar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta de autenticação")
public class LoginResponse {

    @Schema(description = "Token JWT de acesso")
    private String token;

    @Schema(description = "Tipo do token", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "ID do usuário")
    private Long id;

    @Schema(description = "Nome do usuário")
    private String username;

    @Schema(description = "Nome completo do usuário")
    private String nome;

    @Schema(description = "Email do usuário")
    private String email;

    @Schema(description = "Roles/permissões do usuário")
    private List<String> roles;

    public LoginResponse(String token, Long id, String username, String nome, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.roles = roles;
    }
}