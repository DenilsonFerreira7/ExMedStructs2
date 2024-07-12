package com.examemed.dao;

import com.examemed.exception.DatabaseException;
import com.examemed.model.Funcionario;
import com.examemed.util.DBUtils;
import com.examemed.mapper.ResultSetMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public List<Funcionario> getAllFuncionarios() {
        String query = "SELECT * FROM funcionario";
        return DBUtils.executeQuery(query, null, resultSet -> {
            List<Funcionario> funcionarios = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    funcionarios.add(ResultSetMapper.mapToFuncionario(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return funcionarios;
        });
    }

    public List<Funcionario> getAllFuncionariosOrderedByName() {
        String query = "SELECT * FROM funcionario ORDER BY nm_funcionario ASC";
        return DBUtils.executeQuery(query, null, resultSet -> {
            List<Funcionario> funcionarios = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    funcionarios.add(ResultSetMapper.mapToFuncionario(resultSet));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return funcionarios;
        });
    }

    public void addFuncionario(Funcionario funcionario) {
        String query = "INSERT INTO funcionario (cd_funcionario, nm_funcionario) VALUES (?, ?)";
        DBUtils.executeUpdate(query, preparedStatement -> {
            try {
                preparedStatement.setInt(1, funcionario.getId());
                preparedStatement.setString(2, funcionario.getName());
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        });
    }

    public void updateFuncionario(Funcionario funcionario) {
        String query = "UPDATE funcionario SET nm_funcionario = ? WHERE cd_funcionario = ?";
        DBUtils.executeUpdate(query, preparedStatement -> {
            try {
                preparedStatement.setString(1, funcionario.getName());
                preparedStatement.setInt(2, funcionario.getId());
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        });
    }

    public void deleteFuncionario(int id) {
        String query = "DELETE FROM funcionario WHERE cd_funcionario = ?";
        DBUtils.executeUpdate(query, preparedStatement -> {
            try {
                preparedStatement.setInt(1, id);
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        });
    }

    public boolean existsById(int id) {
        String query = "SELECT COUNT(*) FROM funcionario WHERE cd_funcionario = ?";
        return DBUtils.executeQuery(query, preparedStatement -> {
            try {
                preparedStatement.setInt(1, id);
            } catch (SQLException e) {
                throw new DatabaseException("Error setting query parameters", e);
            }
        }, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error processing result set", e);
            }
            return false;
        });
    }
}
