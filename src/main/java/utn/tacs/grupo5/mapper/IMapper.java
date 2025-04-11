package utn.tacs.grupo5.mapper;

public interface IMapper<T, InputDto, OutputDto> {

    OutputDto toDto(T entity);

    T toEntity(InputDto dto);

}