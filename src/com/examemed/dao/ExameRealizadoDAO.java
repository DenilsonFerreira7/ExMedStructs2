package com.examemed.dao;

import com.examemed.exception.DatabaseException;
import com.examemed.mapper.ResultSetMapper;
import com.examemed.model.ExameRealizado;
import com.examemed.util.DBUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExameRealizadoDAO {

    private static final Logger logger = LogManager.getLogger(ExameRealizadoDAO.class);

    public List<ExameRealizado> getExamesRealizados(Integer searchFuncionarioId, String searchFuncionarioNome, Integer searchExameId, String searchDataRealizacao) {
        StringBuilder query = new StringBuilder("SELECT er.cd_funcionario, f.nm_funcionario, er.cd_exame, er.dt_realizacao " +
                "FROM exame_realizado er " +
                "JOIN funcionario f ON er.cd_funcionario = f.cd_funcionario " +
                "WHERE 1=1");

        if (searchFuncionarioId != null) {
            query.append(" AND er.cd_funcionario = ?");
        }
        if (searchFuncionarioNome != null && !searchFuncionarioNome.isEmpty()) {
            query.append(" AND f.nm_funcionario LIKE ?");
        }
        if (searchExameId != null) {
            query.append(" AND er.cd_exame = ?");
        }
        if (searchDataRealizacao != null && !searchDataRealizacao.isEmpty()) {
            query.append(" AND DATE(er.dt_realizacao) = ?");
        }

        return DBUtils.executeQuery(query.toString(), preparedStatement -> {
            int paramIndex = 1;
            try {
                if (searchFuncionarioId != null) {
                    preparedStatement.setInt(paramIndex++, searchFuncionarioId);
                }
                if (searchFuncionarioNome != null && !searchFuncionarioNome.isEmpty()) {
                    preparedStatement.setString(paramIndex++, "%" + searchFuncionarioNome + "%");
                }
                if (searchExameId != null) {
                    preparedStatement.setInt(paramIndex++, searchExameId);
                }
                if (searchDataRealizacao != null && !searchDataRealizacao.isEmpty()) {
                    preparedStatement.setDate(paramIndex++, java.sql.Date.valueOf(searchDataRealizacao));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            List<ExameRealizado> examesRealizados = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    examesRealizados.add(ResultSetMapper.mapToExameRealizado(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return examesRealizados;
        });
    }

    public List<ExameRealizado> getExamesRealizadosPorIntervalo(Integer searchFuncionarioId, Integer searchExameId, String startDate, String endDate) {
        String query = "SELECT er.cd_funcionario, f.nm_funcionario, er.cd_exame, er.dt_realizacao " +
                       "FROM exame_realizado er " +
                       "JOIN funcionario f ON er.cd_funcionario = f.cd_funcionario " +
                       "WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (searchFuncionarioId != null) {
            query += " AND er.cd_funcionario = ?";
            params.add(searchFuncionarioId);
        }
        if (searchExameId != null) {
            query += " AND er.cd_exame = ?";
            params.add(searchExameId);
        }
        if (startDate != null && !startDate.isEmpty()) {
            query += " AND er.dt_realizacao >= ?";
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            query += " AND er.dt_realizacao <= ?";
            params.add(endDate);
        }

        return DBUtils.executeQuery(query, preparedStatement -> {
            try {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            List<ExameRealizado> results = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    ExameRealizado exameRealizado = ResultSetMapper.mapToExameRealizado(resultSet);
                    results.add(exameRealizado);
                }
            } catch (SQLException e) {
                logger.error("Erro ao processar o resultado da query", e);
                throw new DatabaseException("Error processing result set", e);
            }
            return results;
        });
    }
}
