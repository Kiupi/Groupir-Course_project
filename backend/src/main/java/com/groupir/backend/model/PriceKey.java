package com.groupir.backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class PriceKey implements Serializable {

    @Getter @Setter
    @ManyToOne
    public ProductOption option;

    @Getter @Setter
    @ManyToOne
    public Step step;
}
