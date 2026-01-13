package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimentacao {
    private Integer id;
    private Integer contaId;
    private LocalDate data;
    private String acao; // PAGAR ou RECEBER
    private BigDecimal valor;
    private String observacao;

    public Movimentacao() {}

    public Movimentacao(Integer id, Integer contaId, LocalDate data, String acao, BigDecimal valor, String observacao) {
        this.id = id;
        this.contaId = contaId;
        this.data = data;
        this.acao = acao;
        this.valor = valor;
        this.observacao = observacao;
    }

    public Movimentacao(Integer contaId, LocalDate data, String acao, BigDecimal valor, String observacao) {
        this(null, contaId, data, acao, valor, observacao);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getContaId() { return contaId; }
    public void setContaId(Integer contaId) { this.contaId = contaId; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", contaId=" + contaId +
                ", data=" + data +
                ", acao='" + acao + '\'' +
                ", valor=" + valor +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}

