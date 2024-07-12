package com.examemed.service;

import com.examemed.dao.ExameRealizadoDAO;
import com.examemed.exception.DatabaseException;
import com.examemed.model.ExameRealizado;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExameRealizadoService {
    private static final Logger logger = LogManager.getLogger(ExameRealizadoService.class);
    private ExameRealizadoDAO exameRealizadoDAO = new ExameRealizadoDAO();

    public List<ExameRealizado> getExamesRealizados(Integer searchFuncionarioId, String searchFuncionarioNome, Integer searchExameId, String searchDataRealizacao) {
        try {
            return exameRealizadoDAO.getExamesRealizados(searchFuncionarioId, searchFuncionarioNome, searchExameId, searchDataRealizacao);
        } catch (DatabaseException e) {
            logger.error("Erro ao listar exames realizados", e);
            throw e;
        }
    }

    public List<ExameRealizado> getExamesRealizadosPorIntervalo(Integer searchFuncionarioId, Integer searchExameId, String startDate, String endDate) {
        try {
            return exameRealizadoDAO.getExamesRealizadosPorIntervalo(searchFuncionarioId, searchExameId, startDate, endDate);
        } catch (DatabaseException e) {
            logger.error("Erro ao listar exames realizados por intervalo", e);
            throw e;
        }
    }
}
