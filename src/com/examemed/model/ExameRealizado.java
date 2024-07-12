package com.examemed.model;

import java.sql.Date;

public class ExameRealizado {
    private int funcionarioId;
    private String funcionarioNome;
    private int exameId;
    private String exameNome;
    private Date dataRealizacao;

    // Getters e Setters

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public int getExameId() {
        return exameId;
    }

    public void setExameId(int exameId) {
        this.exameId = exameId;
    }

    public String getExameNome() {
        return exameNome;
    }

    public void setExameNome(String exameNome) {
        this.exameNome = exameNome;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
