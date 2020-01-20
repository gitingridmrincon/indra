package com.indracompany.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Banco.
 */
@Entity
@Table(name = "banco")
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_banco")
    private String codigoBanco;

    @Column(name = "nombre")
    private String nombre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public Banco codigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
        return this;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public Banco nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banco)) {
            return false;
        }
        return id != null && id.equals(((Banco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banco{" +
            "id=" + getId() +
            ", codigoBanco='" + getCodigoBanco() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
