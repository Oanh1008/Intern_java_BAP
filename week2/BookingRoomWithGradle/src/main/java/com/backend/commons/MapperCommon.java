package com.backend.commons;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MapperCommon {

    private MapperCommon(){}

    public static <E,T> List<T> mapperListToListDTO(List<E> eList, Class<T> targerClass, ModelMapper modelMapper){
        return eList.stream()
                .map(e -> modelMapper.map(e , targerClass))
                .collect(Collectors.toList());
    }
}
