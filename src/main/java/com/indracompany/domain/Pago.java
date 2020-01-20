package com.indracompany.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pago.
 */
@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_pago")
    private String codigoPago;

    @Column(name = "valor_pagado")
    private Integer valorPagado;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "periodo_pagado")
    private Integer periodoPagado;

    @ManyToOne
    @JsonIgnoreProperties("pagos")
    private Obligacion obligacion;

    @ManyToOne
    @JsonIgnoreProperties("pagos")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("pagos")
    private Banco banco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPago() {
        return codigoPago;
    }

    public Pago codigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
        return this;
    }

    public void setCodigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
    }

    public Integer getValorPagado() {
        return valorPagado;
    }

    public Pago valorPagado(Integer valorPagado) {
        this.valorPagado = valorPagado;
        return this;
    }

    public void setValorPagado(Integer valorPagado) {
        this.valorPagado = valorPagado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public Pago fechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
        return this;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPeriodoPagado() {
        return periodoPagado;
    }

    public Pago periodoPagado(Integer periodoPagado) {
        this.periodoPagado = periodoPagado;
        return this;
    }

    public void setPeriodoPagado(Integer periodoPagado) {
        this.periodoPagado = periodoPagado;
    }

    public Obligacion getObligacion() {
        return obligacion;
    }

    public Pago obligacion(Obligacion obligacion) {
        this.obligacion = obligacion;
        return this;
    }

    public void setObligacion(Obligacion obligacion) {
        this.obligacion = obligacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pago cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Banco getBanco() {
        return banco;
    }

    public Pago banco(Banco banco) {
        this.banco = banco;
        return this;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pago)) {
            return false;
        }
        return id != null && id.equals(((Pago) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pago{" +
            "id=" + getId() +
            ", codigoPago='" + getCodigoPago() + "'" +
            ", valorPagado=" + getValorPagado() +
            ", fechaPago='" + getFechaPago() + "'" +
            ", periodoPagado=" + getPeriodoPagado() +
            "}";
    }
}
