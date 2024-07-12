package com.examemed.action;

import com.examemed.model.ExameRealizado;
import com.examemed.service.ExameRealizadoService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExameRealizadoAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ExameRealizadoAction.class);

    private List<ExameRealizado> examesRealizados;
    private Integer searchFuncionarioId;
    private String searchFuncionarioNome;
    private Integer searchExameId;
    private String searchDataRealizacao;
    private String startDate;
    private String endDate;

    private ExameRealizadoService exameRealizadoService = new ExameRealizadoService();

    public String listar() {
        logger.info("Iniciando método listar");
        examesRealizados = exameRealizadoService.getExamesRealizados(searchFuncionarioId, searchFuncionarioNome, searchExameId, searchDataRealizacao);
        logger.info("Listagem de exames realizados concluída com sucesso");
        return SUCCESS;
    }

    public String listarPorIntervalo() {
        logger.info("Iniciando método listarPorIntervalo");
        examesRealizados = exameRealizadoService.getExamesRealizadosPorIntervalo(searchFuncionarioId, searchExameId, startDate, endDate);
        logger.info("Listagem de exames realizados por intervalo concluída com sucesso");
        return SUCCESS;
    }

    // Getters e Setters...
    public List<ExameRealizado> getExamesRealizados() {
        return examesRealizados;
    }

    public void setExamesRealizados(List<ExameRealizado> examesRealizados) {
        this.examesRealizados = examesRealizados;
    }

    public Integer getSearchFuncionarioId() {
        return searchFuncionarioId;
    }

    public void setSearchFuncionarioId(Integer searchFuncionarioId) {
        this.searchFuncionarioId = searchFuncionarioId;
    }

    public String getSearchFuncionarioNome() {
        return searchFuncionarioNome;
    }

    public void setSearchFuncionarioNome(String searchFuncionarioNome) {
        this.searchFuncionarioNome = searchFuncionarioNome;
    }

    public Integer getSearchExameId() {
        return searchExameId;
    }

    public void setSearchExameId(Integer searchExameId) {
        this.searchExameId = searchExameId;
    }

    public String getSearchDataRealizacao() {
        return searchDataRealizacao;
    }

    public void setSearchDataRealizacao(String searchDataRealizacao) {
        this.searchDataRealizacao = searchDataRealizacao;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
