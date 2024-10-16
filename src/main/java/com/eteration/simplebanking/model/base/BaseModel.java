package com.eteration.simplebanking.model.base;


import javax.persistence.*;

import lombok.Data;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
