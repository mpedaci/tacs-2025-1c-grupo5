package utn.tacs.grupo5.mapper;

public interface IMapper<T, DTO> {

    DTO toDto(T entity);

    T toEntity(DTO dto);

}