package com.example.springbootinit.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataListVO<E> {

    private String listRelatedOperation;

    private Long listRelatedData;

    private List<E> dataList;

}
