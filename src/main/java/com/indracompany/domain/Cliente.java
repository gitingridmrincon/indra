package com.indracompany.domain;

import javax.persistence.*;

import java.io.Serializable;

import com.indracompany.domain.enumeration.TipoDeDocumento;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "identificacion")
    private String identificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_documento")
    private TipoDeDocumento tipoDeDocumento;

    @Column(name = "nombre_del_cliente")
    private String nombreDelCliente;

    @Column(name = "segundo_nombre_del_cliente")
    private String segundoNombreDelCliente;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "rezon_social")
    private String rezonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public Cliente identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public Cliente tipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
        return this;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public Cliente nombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
        return this;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public String getSegundoNombreDelCliente() {
        return segundoNombreDelCliente;
    }

    public Cliente segundoNombreDelCliente(String segundoNombreDelCliente) {
        this.segundoNombreDelCliente = segundoNombreDelCliente;
        return this;
    }

    public void setSegundoNombreDelCliente(String segundoNombreDelCliente) {
        this.segundoNombreDelCliente = segundoNombreDelCliente;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public Cliente primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Cliente segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getRezonSocial() {
        return rezonSocial;
    }

    public Cliente rezonSocial(String rezonSocial) {
        this.rezonSocial = rezonSocial;
        return this;
    }

    public void setRezonSocial(String rezonSocial) {
        this.rezonSocial = rezonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", identificacion='" + getIdentificacion() + "'" +
            ", tipoDeDocumento='" + getTipoDeDocumento() + "'" +
            ", nombreDelCliente='" + getNombreDelCliente() + "'" +
            ", segundoNombreDelCliente='" + getSegundoNombreDelCliente() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", rezonSocial='" + getRezonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
