package com.groupir.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Data
public class PriceKey implements Serializable {

    @ManyToOne
    public ProductOption option;

    @ManyToOne
    public Step step;
}
