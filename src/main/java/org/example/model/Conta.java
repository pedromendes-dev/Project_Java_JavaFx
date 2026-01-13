package org.example.model;

import java.math.BigDecimal;

public class Conta {
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private String tipo; // PAGAR ou RECEBER
    private Integer clienteId;

    public Conta() {}

    public Conta(Integer id, String descricao, BigDecimal valor, String tipo, Integer clienteId) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.clienteId = clienteId;
    }

    public Conta(String descricao, BigDecimal valor, String tipo, Integer clienteId) {
        this(null, descricao, valor, tipo, clienteId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", clienteId=" + clienteId +
                '}';
    }
}

