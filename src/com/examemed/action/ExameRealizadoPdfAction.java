package com.examemed.action;

import com.examemed.model.ExameRealizado;
import com.examemed.model.Exame;
import com.examemed.service.ExameRealizadoService;
import com.examemed.service.ExameService;
import com.examemed.util.PdfGenerator;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import java.util.List;
import java.util.stream.Collectors;

public class ExameRealizadoPdfAction extends ActionSupport implements ServletResponseAware {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ExameRealizadoPdfAction.class);

    private HttpServletResponse response;
    private Integer searchFuncionarioId;
    private String searchFuncionarioNome;
    private Integer searchExameId;
    private String searchDataRealizacao;

    private ExameRealizadoService exameRealizadoService = new ExameRealizadoService();
    private ExameService exameService = new ExameService();

    public String execute() {
        logger.info("Iniciando método execute");
        List<ExameRealizado> examesRealizados = exameRealizadoService.getExamesRealizados(searchFuncionarioId, searchFuncionarioNome, searchExameId, searchDataRealizacao);
        List<Exame> detalhesExames = exameService.getExamesByIds(examesRealizados.stream().map(ExameRealizado::getExameId).collect(Collectors.toList()));

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=exames_realizados.pdf");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generateExameRealizadoPdf(response.getOutputStream(), examesRealizados, detalhesExames);
            response.flushBuffer();  
        } catch (Exception e) {
            logger.error("Erro ao gerar PDF", e);
            return ERROR;
        }
        logger.info("PDF gerado com sucesso");
        return null;  
    }

    // Getters e Setters...
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
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
}
