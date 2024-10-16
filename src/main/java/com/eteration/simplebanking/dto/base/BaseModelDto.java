package com.eteration.simplebanking.dto.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
@ApiModel(value="User Api Doc",description="Model")
public abstract class BaseModelDto implements Serializable {
    @ApiModelProperty(value="User ID")
    private Long id ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
