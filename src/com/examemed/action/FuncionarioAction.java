package com.examemed.action;

import com.examemed.model.Funcionario;
import com.examemed.service.FuncionarioService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FuncionarioAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(FuncionarioAction.class);

    private FuncionarioService funcionarioService = new FuncionarioService();
    private Funcionario funcionario;
    private List<Funcionario> funcionarios;
    private int id;

    public String listar() {
        logger.info("Iniciando método listar");
        funcionarios = funcionarioService.getAllFuncionarios();
        logger.info("Listagem de funcionários concluída com sucesso");
        return SUCCESS;
    }

    public String adicionar() {
        logger.info("Iniciando método adicionar");
        funcionarioService.addFuncionario(funcionario);
        logger.info("Funcionário adicionado com sucesso: {}", funcionario);
        return SUCCESS;
    }

    public String atualizar() {
        logger.info("Iniciando método atualizar");
        funcionarioService.updateFuncionario(funcionario);
        logger.info("Funcionário atualizado com sucesso: {}", funcionario);
        return SUCCESS;
    }

    public String deletar() {
        logger.info("Iniciando método deletar");
        funcionarioService.deleteFuncionario(id);
        logger.info("Funcionário deletado com sucesso: ID = {}", id);
        return SUCCESS;
    }

    // Getters e Setters
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
