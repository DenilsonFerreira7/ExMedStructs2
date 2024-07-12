package com.examemed.dao;

import com.examemed.exception.DatabaseException;
import com.examemed.model.Exame;
import com.examemed.util.DBUtils;
import com.examemed.mapper.ResultSetMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExameDAO {

    public List<Exame> getExames(Integer searchId, Boolean activeFilter, int offset, int limit) {
        List<Exame> exames = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM exame WHERE cd_funcionario IS NULL");

        if (searchId != null) {
            query.append(" AND cd_exame = ?");
        }
        if (activeFilter != null) {
            query.append(" AND ic_ativo = ?");
        }

        query.append(" LIMIT ? OFFSET ?");

        return DBUtils.executeQuery(query.toString(), preparedStatement -> {
            int paramIndex = 1;
            try {
                if (searchId != null) {
                    preparedStatement.setInt(paramIndex++, searchId);
                }
                if (activeFilter != null) {
                    preparedStatement.setBoolean(paramIndex++, activeFilter);
                }
                preparedStatement.setInt(paramIndex++, limit);
                preparedStatement.setInt(paramIndex, offset);
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            try {
                while (resultSet.next()) {
                    exames.add(ResultSetMapper.mapToExame(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return exames;
        });
    }

    public int getExameCount(Integer searchId, Boolean activeFilter) {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM exame WHERE cd_funcionario IS NULL");

        if (searchId != null) {
            query.append(" AND cd_exame = ?");
        }
        if (activeFilter != null) {
            query.append(" AND ic_ativo = ?");
        }

        return DBUtils.executeQuery(query.toString(), preparedStatement -> {
            int paramIndex = 1;
            try {
                if (searchId != null) {
                    preparedStatement.setInt(paramIndex++, searchId);
                }
                if (activeFilter != null) {
                    preparedStatement.setBoolean(paramIndex++, activeFilter);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return 0;
        });
    }

    public void addExame(Exame exame, Integer funcionarioId) {
        String sql = "INSERT INTO exame (nm_exame, ic_ativo, ds_detalhe_exame, ds_detalhe_exame1, cd_funcionario) VALUES (?, ?, ?, ?, ?)";
        DBUtils.executeUpdate(sql, preparedStatement -> {
            try {
                preparedStatement.setString(1, exame.getName());
                preparedStatement.setBoolean(2, exame.isActive());
                preparedStatement.setString(3, exame.getDetail());
                preparedStatement.setString(4, exame.getDetail1());
                if (funcionarioId != null) {
                    preparedStatement.setInt(5, funcionarioId);
                } else {
                    preparedStatement.setNull(5, Types.INTEGER);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        });
    }

    public void associarFuncionario(int exameId, int funcionarioId) {
        DBUtils.executeTransaction(connection -> {
            try (PreparedStatement stmtExame = connection.prepareStatement(
                         "UPDATE exame SET cd_funcionario = ? WHERE cd_exame = ?");
                 PreparedStatement stmtExameRealizado = connection.prepareStatement(
                         "INSERT INTO exame_realizado (cd_funcionario, cd_exame, dt_realizacao) VALUES (?, ?, NOW())")) {

                stmtExame.setInt(1, funcionarioId);
                stmtExame.setInt(2, exameId);
                stmtExame.executeUpdate();

                stmtExameRealizado.setInt(1, funcionarioId);
                stmtExameRealizado.setInt(2, exameId);
                stmtExameRealizado.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Erro ao associar funcionário ao exame", e);
            }
        });
    }

    public void updateExame(Exame exame, Integer funcionarioId) {
        DBUtils.executeTransaction(connection -> {
            try (PreparedStatement stmtExame = connection.prepareStatement(
                         "UPDATE exame SET nm_exame = ?, ic_ativo = ?, ds_detalhe_exame = ?, ds_detalhe_exame1 = ?, cd_funcionario = ? WHERE cd_exame = ?");
                 PreparedStatement stmtExameRealizado = connection.prepareStatement(
                         "INSERT INTO exame_realizado (cd_funcionario, cd_exame, dt_realizacao) VALUES (?, ?, NOW())")) {

                stmtExame.setString(1, exame.getName());
                stmtExame.setBoolean(2, exame.isActive());
                stmtExame.setString(3, exame.getDetail());
                stmtExame.setString(4, exame.getDetail1());
                if (funcionarioId != null) {
                    stmtExame.setInt(5, funcionarioId);
                } else {
                    stmtExame.setNull(5, Types.INTEGER);
                }
                stmtExame.setInt(6, exame.getId());
                stmtExame.executeUpdate();

                if (funcionarioId != null) {
                    stmtExameRealizado.setInt(1, funcionarioId);
                    stmtExameRealizado.setInt(2, exame.getId());
                    stmtExameRealizado.executeUpdate();
                }

            } catch (SQLException e) {
                throw new DatabaseException("Erro ao atualizar exame", e);
            }
        });
    }

    public void deleteExame(int id) {
        String query = "DELETE FROM exame WHERE cd_exame = ?";
        DBUtils.executeUpdate(query, preparedStatement -> {
            try {
                preparedStatement.setInt(1, id);
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        });
    }

    public List<Exame> getExamesByIds(List<Integer> exameIds) {
        if (exameIds == null || exameIds.isEmpty()) {
            return new ArrayList<>();
        }

        String query = "SELECT * FROM exame WHERE cd_exame IN (" + exameIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";

        return DBUtils.executeQuery(query, null, resultSet -> {
            List<Exame> exames = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    exames.add(ResultSetMapper.mapToExame(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return exames;
        });
    }

    public Exame findExameById(int id) {
        String query = "SELECT * FROM exame WHERE cd_exame = ?";
        return DBUtils.executeQuery(query, preparedStatement -> {
            try {
                preparedStatement.setInt(1, id);
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            try {
                if (resultSet.next()) {
                    return ResultSetMapper.mapToExame(resultSet);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return null;
        });
    }

    public List<Exame> getAllExames() {
        String query = "SELECT * FROM exame";
        return DBUtils.executeQuery(query, null, resultSet -> {
            List<Exame> exames = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    exames.add(ResultSetMapper.mapToExame(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return exames;
        });
    }

    public int getTotalExamesAtivos() {
        String query = "SELECT COUNT(*) FROM exame WHERE ic_ativo = 1 AND cd_funcionario IS NULL";
        return DBUtils.executeQuery(query, null, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return 0;
        });
    }

    public int getTotalExamesInativos() {
        String query = "SELECT COUNT(*) FROM exame WHERE ic_ativo = 0 AND cd_funcionario IS NULL";
        return DBUtils.executeQuery(query, null, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return 0;
        });
    }
}
