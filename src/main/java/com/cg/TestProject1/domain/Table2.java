package com.cg.TestProject1.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Table2.
 */
@Entity
@Table(name = "table2")
public class Table2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "column_2")
    private String Column2;

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

    public String getColumn2() {
        return Column2;
    }

    public void setColumn2(String Column2) {
        this.Column2 = Column2;
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
        if (!(o instanceof Table2)) {
            return false;
        }
        return id != null && id.equals(((Table2) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Table2{" +
            "id=" + getId() +
            ", Column2='" + getColumn2() + "'" +
            "}";
    }
}
