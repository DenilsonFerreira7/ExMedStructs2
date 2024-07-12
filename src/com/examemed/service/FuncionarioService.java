package com.examemed.service;

import com.examemed.dao.FuncionarioDAO;
import com.examemed.exception.DatabaseException;
import com.examemed.model.Funcionario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FuncionarioService {
    private static final Logger logger = LogManager.getLogger(FuncionarioService.class);
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public List<Funcionario> getAllFuncionarios() {
        try {
            return funcionarioDAO.getAllFuncionarios();
        } catch (DatabaseException e) {
            logger.error("Erro ao listar todos os funcionários", e);
            throw e;
        }
    }

    public void addFuncionario(Funcionario funcionario) {
        try {
            if (!funcionarioDAO.existsById(funcionario.getId())) {
                funcionarioDAO.addFuncionario(funcionario);
            } else {
                throw new DatabaseException("ID do funcionário já existe.");
            }
        } catch (DatabaseException e) {
            logger.error("Erro ao adicionar funcionário", e);
            throw e;
        }
    }

    public void updateFuncionario(Funcionario funcionario) {
        try {
            funcionarioDAO.updateFuncionario(funcionario);
        } catch (DatabaseException e) {
            logger.error("Erro ao atualizar funcionário", e);
            throw e;
        }
    }

    public void deleteFuncionario(int id) {
        try {
            funcionarioDAO.deleteFuncionario(id);
        } catch (DatabaseException e) {
            logger.error("Erro ao deletar funcionário", e);
            throw e;
        }
    }

    public boolean existsById(int id) {
        try {
            return funcionarioDAO.existsById(id);
        } catch (DatabaseException e) {
            logger.error("Erro ao verificar existência do funcionário", e);
            throw e;
        }
    }

    public List<Funcionario> getAllFuncionariosOrderedByName() {
        try {
            return funcionarioDAO.getAllFuncionariosOrderedByName();
        } catch (DatabaseException e) {
            logger.error("Erro ao listar funcionários ordenados por nome", e);
            throw e;
        }
    }
}
