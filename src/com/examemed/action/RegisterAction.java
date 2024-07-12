package com.examemed.action;

import com.examemed.dao.UsuarioDAO;
import com.examemed.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(RegisterAction.class);

    private String username;
    private String password;

    public String register() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario existingUser = usuarioDAO.findByLogin(username);

        if (existingUser != null) {
            addActionError("Nome de usuário já existe.");
            return INPUT;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Usuario usuario = new Usuario();
        usuario.setLogin(username);
        usuario.setSenha(hashedPassword);
        usuario.setTempoInatividade(1); 

        try {
            usuarioDAO.save(usuario);
            addActionMessage("Registro bem-sucedido. Faça login.");
            return SUCCESS;
        } catch (Exception e) {
            logger.error("Erro ao registrar usuário", e);
            addActionError("Erro ao registrar usuário.");
            return ERROR;
        }
    }

    // Getters e Setters para username e password
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
