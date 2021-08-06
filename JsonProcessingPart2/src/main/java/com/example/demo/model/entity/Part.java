package com.example.demo.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "parts")
public class Part extends BaseEntity{
    //Parts have name, price and quantity.
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne
    private PartSupplier partSupplier;



    public Part() {
    }

    public PartSupplier getPartSupplier() {
        return partSupplier;
    }

    public void setPartSupplier(PartSupplier partSupplier) {
        this.partSupplier = partSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part part)) return false;
        return getName().equals(part.getName())
                && getPrice().equals(part.getPrice())
                && getQuantity().equals(part.getQuantity())
                && getPartSupplier().equals(part.getPartSupplier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getQuantity(), getPartSupplier());
    }
}
