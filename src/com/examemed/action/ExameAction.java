package com.examemed.action;

import com.examemed.model.Exame;
import com.examemed.service.ExameService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExameAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ExameAction.class);

    private Exame exame;
    private List<Exame> exames;
    private int id;
    private Integer searchId;
    private String searchActive = "true"; 
    private Integer funcionarioId;
    private int page = 1; 
    private int limit = 10; 
    private int totalExames;
    private int totalExamesAtivos;
    private int totalExamesInativos;

    private ExameService exameService = new ExameService();

    public String listar() {
        logger.info("Iniciando método listar");
        exames = exameService.listarExames(searchId, searchActive, page, limit);
        totalExames = exameService.getTotalExames();
        totalExamesAtivos = exameService.getTotalExamesAtivos();
        totalExamesInativos = exameService.getTotalExamesInativos();
        logger.info("Listagem de exames concluída com sucesso");
        return SUCCESS;
    }

    public String adicionar() {
        logger.info("Iniciando método adicionar");
        exameService.adicionarExame(exame, funcionarioId);
        logger.info("Exame adicionado com sucesso: {}", exame);
        return SUCCESS;
    }

    public String atualizar() {
        logger.info("Iniciando método atualizar");
        exameService.atualizarExame(exame, funcionarioId);
        logger.info("Exame atualizado com sucesso: {}", exame);
        return SUCCESS;
    }

    public String deletar() {
        logger.info("Iniciando método deletar");
        exameService.deletarExame(id);
        logger.info("Exame deletado com sucesso: ID = {}", id);
        return SUCCESS;
    }

    public String associarFuncionario() {
        logger.info("Iniciando método associarFuncionario");
        exameService.associarFuncionario(id, funcionarioId);
        logger.info("Funcionário associado com sucesso ao exame ID = {}", id);
        return SUCCESS;
    }

    // Getters and Setters
    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public String getSearchActive() {
        return searchActive;
    }

    public void setSearchActive(String searchActive) {
        this.searchActive = searchActive;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
