package com.examemed.dao;

import com.examemed.exception.DatabaseException;
import com.examemed.model.Usuario;
import com.examemed.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public void save(Usuario usuario) {
        String query = "INSERT INTO usuario (nm_login, ds_senha, qt_tempo_inatividade) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setInt(3, usuario.getTempoInatividade()); // Definindo o tempo de inatividade
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar usuário", e);
        }
    }

    // Método para encontrar usuário pelo login
    public Usuario findByLogin(String login) {
        String query = "SELECT * FROM usuario WHERE nm_login = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setLogin(resultSet.getString("nm_login"));
                usuario.setSenha(resultSet.getString("ds_senha"));
                usuario.setTempoInatividade(resultSet.getInt("qt_tempo_inatividade"));
                return usuario;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário", e);
        }
        return null;
    }
}
