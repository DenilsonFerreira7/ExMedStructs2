package com.examemed.action;

import com.examemed.model.Exame;
import com.examemed.service.ExameService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

public class ListarExamesAction extends ActionSupport {
    private Integer searchId;
    private Boolean searchActive;
    private int page = 1;
    private int totalPages;
    private List<Exame> exames;
    private int totalExames;
    private int totalExamesAtivos;
    private int totalExamesInativos;

    private static final long serialVersionUID = 1L;
    private ExameService exameService = new ExameService();

    public String execute() {
        try {
            totalExames = exameService.getTotalExames();
            totalExamesAtivos = exameService.getTotalExamesAtivos();
            totalExamesInativos = exameService.getTotalExamesInativos();
        } catch (Exception e) {
            addActionError("Erro ao listar exames.");
            return ERROR;
        }
        return SUCCESS;
    }
    // Getters e setters
    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Boolean getSearchActive() {
        return searchActive;
    }

    public void setSearchActive(Boolean searchActive) {
        this.searchActive = searchActive;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public int getTotalExames() {
        return totalExames;
    }

    public void setTotalExames(int totalExames) {
        this.totalExames = totalExames;
    }

    public int getTotalExamesAtivos() {
        return totalExamesAtivos;
    }

    public void setTotalExamesAtivos(int totalExamesAtivos) {
        this.totalExamesAtivos = totalExamesAtivos;
    }

    public int getTotalExamesInativos() {
        return totalExamesInativos;
    }

    public void setTotalExamesInativos(int totalExamesInativos) {
        this.totalExamesInativos = totalExamesInativos;
    }
}
