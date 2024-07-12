package com.examemed.service;

import com.examemed.dao.ExameDAO;
import com.examemed.dao.FuncionarioDAO;
import com.examemed.exception.DatabaseException;
import com.examemed.model.Exame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExameService {
    private static final Logger logger = LogManager.getLogger(ExameService.class);
    private ExameDAO exameDAO = new ExameDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public List<Exame> listarExames(Integer searchId, String searchActive, int page, int limit) {
        try {
            Boolean activeFilter = (searchActive == null || searchActive.isEmpty()) ? null : Boolean.parseBoolean(searchActive);
            int offset = (page - 1) * limit;
            return exameDAO.getExames(searchId, activeFilter, offset, limit);
        } catch (Exception e) {
            logger.error("Erro ao listar exames", e);
            throw new DatabaseException("Erro ao listar exames", e);
        }
    }

    public int getTotalExames() {
        try {
            return exameDAO.getExameCount(null, null);
        } catch (DatabaseException e) {
            logger.error("Erro ao obter contagem total de exames", e);
            throw new DatabaseException("Erro ao obter contagem total de exames", e);
        }
    }

    public int getTotalExamesAtivos() {
        try {
            return exameDAO.getExameCount(null, true);
        } catch (DatabaseException e) {
            logger.error("Erro ao obter contagem de exames ativos", e);
            throw new DatabaseException("Erro ao obter contagem de exames ativos", e);
        }
    }

    public int getTotalExamesInativos() {
        try {
            return exameDAO.getExameCount(null, false);
        } catch (DatabaseException e) {
            logger.error("Erro ao obter contagem de exames inativos", e);
            throw new DatabaseException("Erro ao obter contagem de exames inativos", e);
        }
    }

    public void adicionarExame(Exame exame, Integer funcionarioId) {
        try {
            if (funcionarioId != null && !funcionarioDAO.existsById(funcionarioId)) {
                throw new DatabaseException("ID do funcionário inválido.");
            }
            exameDAO.addExame(exame, funcionarioId);
        } catch (DatabaseException e) {
            logger.error("Erro ao adicionar exame", e);
            throw new DatabaseException("Erro ao adicionar exame.", e);
        }
    }

    public void atualizarExame(Exame exame, Integer funcionarioId) {
        try {
            if (funcionarioId != null && !funcionarioDAO.existsById(funcionarioId)) {
                throw new DatabaseException("ID do funcionário inválido.");
            }
            exameDAO.updateExame(exame, funcionarioId);
        } catch (DatabaseException e) {
            logger.error("Erro ao atualizar exame", e);
            throw new DatabaseException("Erro ao atualizar exame.", e);
        }
    }

    public void deletarExame(int id) {
        try {
            exameDAO.deleteExame(id);
        } catch (DatabaseException e) {
            logger.error("Erro ao deletar exame", e);
            throw new DatabaseException("Erro ao deletar exame.", e);
        }
    }

    public void associarFuncionario(int exameId, int funcionarioId) {
        try {
            if (!funcionarioDAO.existsById(funcionarioId)) {
                throw new DatabaseException("ID do funcionário inválido.");
            }
            exameDAO.associarFuncionario(exameId, funcionarioId);
        } catch (DatabaseException e) {
            logger.error("Erro ao associar funcionário ao exame", e);
            throw new DatabaseException("Erro ao associar funcionário ao exame.", e);
        }
    }

    public List<Exame> getExamesByIds(List<Integer> exameIds) {
        try {
            return exameDAO.getExamesByIds(exameIds);
        } catch (DatabaseException e) {
            logger.error("Erro ao obter exames por IDs", e);
            throw new DatabaseException("Erro ao obter exames por IDs.", e);
        }
    }

    public Exame findExameById(int id) {
        try {
            return exameDAO.findExameById(id);
        } catch (DatabaseException e) {
            logger.error("Erro ao buscar exame por ID", e);
            throw new DatabaseException("Erro ao buscar exame por ID", e);
        }
    }

    public List<Exame> getAllExames() {
        try {
            return exameDAO.getAllExames();
        } catch (DatabaseException e) {
            logger.error("Erro ao buscar todos os exames", e);
            throw new DatabaseException("Erro ao buscar todos os exames", e);
        }
    }
}
