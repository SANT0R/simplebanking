package com.eteration.simplebanking.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="BetweenDto")
public class BetweenDto {
    @ApiModelProperty(value="value1")
    private Double value1;

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    @ApiModelProperty(value="value2")
    private Double value2;

    public Double getValue1() {
        return value1;
    }


    public Double getValue2() {
        return value2;
    }

}
