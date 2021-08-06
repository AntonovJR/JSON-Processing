package com.example.demo.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "suppliers")
public class PartSupplier extends BaseEntity{
    //Part supplier have name and info whether he uses imported parts.
    private String name;
    @Column(name = "is_importer")
    private Boolean isImporter;
    @OneToMany(mappedBy = "partSupplier",fetch = FetchType.EAGER)
    private List<Part> partList;

    public PartSupplier() {
    }

    public List<Part> getPartList() {
        return partList;
    }

    public void setPartList(List<Part> partList) {
        this.partList = partList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartSupplier that)) return false;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(isImporter, that.isImporter)
                && Objects.equals(getPartList(), that.getPartList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isImporter, getPartList());
    }
}
