package ar.com.juanferrara.GestionHotelera.business.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface IMapper <ENTITY, DTO> {
    ENTITY toEntity(DTO dto);
    DTO toDto(ENTITY entity);
    List<DTO> toDTOList(List<ENTITY> entityList);
    List<ENTITY> toEntityList(List<DTO> dtoList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ENTITY target, ENTITY source);
}
