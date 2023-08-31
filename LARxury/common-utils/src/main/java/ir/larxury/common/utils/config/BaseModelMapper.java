package ir.larxury.common.utils.config;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BaseModelMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public <M, D> D toDto(M model, Class<D> dClass) {
        return modelMapper.map(model, dClass);
    }

    public <M, D> List<D> toDtoList(List<M> modelList, Class<D> dClass) {
        return modelList.stream()
                .map(model -> modelMapper.map(model, dClass))
                .collect(Collectors.toList());
    }

    public <M, D> M toModel(D dto, Class<M> mClass) {
        return modelMapper.map(dto, mClass);
    }

    public <M, D> List<M> toModelList(List<D> dtoList, Class<M> mClass) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, mClass))
                .collect(Collectors.toList());
    }
}
