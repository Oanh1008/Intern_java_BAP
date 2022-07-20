package com.backend.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    private MapperUtils(){}

    public static <E,T> List<T> mapperListToListDTO(List<E> eList, Class<T> targerClass, ModelMapper modelMapper){
        return eList.stream()
                .map(e -> modelMapper.map(e , targerClass))
                .collect(Collectors.toList());
    }
}
