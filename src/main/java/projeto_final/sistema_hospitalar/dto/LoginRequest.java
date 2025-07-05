package projeto_final.sistema_hospitalar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para autenticação")
public class LoginRequest {

    @NotBlank(message = "Username é obrigatório")
    @Schema(description = "Nome de usuário", example = "admin")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "123456")
    private String password;
}