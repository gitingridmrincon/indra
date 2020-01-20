package com.indracompany.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.indracompany.domain.enumeration.Estado;

/**
 * A Obligacion.
 */
@Entity
@Table(name = "obligacion")
public class Obligacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_obligacion")
    private String codigoObligacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "valor_total")
    private Integer valorTotal;

    @Column(name = "valor_periodo")
    private Integer valorPeriodo;   

    @Column(name = "nomero_periodos")
    private Integer nomeroPeriodos;

    @Column(name = "periodo_actual")
    private Integer periodoActual;

    @Column(name = "vigente")
    private Boolean vigente=Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @ManyToOne
    @JsonIgnoreProperties("obligacions")
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoObligacion() {
        return codigoObligacion;
    }

    public Obligacion codigoObligacion(String codigoObligacion) {
        this.codigoObligacion = codigoObligacion;
        return this;
    }

    public void setCodigoObligacion(String codigoObligacion) {
        this.codigoObligacion = codigoObligacion;
    }

    public String getNombre() {
        return nombre;
    }

    public Obligacion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Obligacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Obligacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public Obligacion valorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getValorPeriodo() {
        return valorPeriodo;
    }

    public Obligacion valorPeriodo(Integer valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
        return this;
    }

    public void setValorPeriodo(Integer valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    public Integer getNomeroPeriodos() {
        return nomeroPeriodos;
    }

    public Obligacion nomeroPeriodos(Integer nomeroPeriodos) {
        this.nomeroPeriodos = nomeroPeriodos;
        return this;
    }

    public void setNomeroPeriodos(Integer nomeroPeriodos) {
        this.nomeroPeriodos = nomeroPeriodos;
    }

    public Integer getPeriodoActual() {
        return periodoActual;
    }

    public Obligacion periodoActual(Integer periodoActual) {
        this.periodoActual = periodoActual;
        return this;
    }

    public void setPeriodoActual(Integer periodoActual) {
        this.periodoActual = periodoActual;
    }

    public Boolean isVigente() {
        return vigente;
    }

    public Obligacion vigente(Boolean vigente) {
        this.vigente = vigente;
        return this;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public Estado getEstado() {
        return estado;
    }

    public Obligacion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public Obligacion producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Obligacion)) {
            return false;
        }
        return id != null && id.equals(((Obligacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Obligacion{" +
            "id=" + getId() +
            ", codigoObligacion='" + getCodigoObligacion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", valorPeriodo=" + getValorPeriodo() +
            ", nomeroPeriodos=" + getNomeroPeriodos() +
            ", periodoActual=" + getPeriodoActual() +
            ", vigente='" + isVigente() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
