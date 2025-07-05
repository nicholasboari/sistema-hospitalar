package projeto_final.sistema_hospitalar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import projeto_final.sistema_hospitalar.dto.PacienteCreateDTO;
import projeto_final.sistema_hospitalar.model.Paciente;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Paciente toEntity(PacienteCreateDTO dto);

    PacienteCreateDTO toDto(Paciente entity);
}