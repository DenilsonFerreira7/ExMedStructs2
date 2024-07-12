package com.examemed.mapper;

import com.examemed.model.Funcionario;
import com.examemed.model.Exame;
import com.examemed.model.ExameRealizado;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    public static Funcionario mapToFuncionario(ResultSet resultSet) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(resultSet.getInt("cd_funcionario"));
        funcionario.setName(resultSet.getString("nm_funcionario"));
        return funcionario;
    }

    public static Exame mapToExame(ResultSet resultSet) throws SQLException {
        Exame exame = new Exame();
        exame.setId(resultSet.getInt("cd_exame"));
        exame.setName(resultSet.getString("nm_exame"));
        exame.setActive(resultSet.getBoolean("ic_ativo"));
        exame.setDetail(resultSet.getString("ds_detalhe_exame"));
        exame.setDetail1(resultSet.getString("ds_detalhe_exame1"));
        exame.setFuncionarioId(resultSet.getObject("cd_funcionario", Integer.class));
        exame.setCreatedAt(resultSet.getTimestamp("created_at"));
        exame.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return exame;
    }

    public static ExameRealizado mapToExameRealizado(ResultSet rs) throws SQLException {
        ExameRealizado exameRealizado = new ExameRealizado();
        exameRealizado.setFuncionarioId(rs.getInt("cd_funcionario"));
        exameRealizado.setFuncionarioNome(rs.getString("nm_funcionario"));
        exameRealizado.setExameId(rs.getInt("cd_exame"));
        exameRealizado.setDataRealizacao(rs.getDate("dt_realizacao"));
        return exameRealizado;
    }
}
