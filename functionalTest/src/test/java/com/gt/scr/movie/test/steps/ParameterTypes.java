package com.gt.scr.movie.test.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.test.domain.TestMovieDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;

import java.lang.reflect.Type;
import java.util.List;

public class ParameterTypes {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @DataTableType
    private List<TestMovieDTO> convert(DataTable dataTable) {
        return dataTable.asList(TestMovieDTO.class);
    }
}
