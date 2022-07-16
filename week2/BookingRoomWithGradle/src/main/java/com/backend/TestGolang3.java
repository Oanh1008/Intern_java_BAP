package com.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Predicate;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestGolang3 {
    public static void main(String[] args) {
        StringUtils.hasLength("22");
        System.out.println(StringUtils.hasLength(""));

        List<Integer> numbers = Arrays.asList(1,2,4,2);
        Map map = CollectionUtils.getIndexMap(numbers);
        for(Object m : map.entrySet()){
            System.out.println(m);
        }

        Logger logger = LoggerFactory.getLogger(TestGolang3.class);



    }
}
