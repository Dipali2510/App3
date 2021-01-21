package com.cg.TestProject1.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Table1.
 */
@Entity
@Table(name = "table1")
public class Table1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "column_1")
    private String Column1;

    @OneToOne
    @JoinColumn(unique = true)
    private Table2 table1_Column1;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumn1() {
        return Column1;
    }

    public void setColumn1(String Column1) {
        this.Column1 = Column1;
    }

    public Table2 getTable1_Column1() {
        return table1_Column1;
    }

    public void setTable1_Column1(Table2 Table2) {
        this.table1_Column1 = Table2;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Table1)) {
            return false;
        }
        return id != null && id.equals(((Table1) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Table1{" +
            "id=" + getId() +
            ", Column1='" + getColumn1() + "'" +
            "}";
    }
}
