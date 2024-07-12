package com.examemed.model;

public class Usuario {
    private String login;
    private String senha;
    private int tempoInatividade;

    // Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTempoInatividade() {
        return tempoInatividade;
    }

    public void setTempoInatividade(int tempoInatividade) {
        this.tempoInatividade = tempoInatividade;
    }
}
