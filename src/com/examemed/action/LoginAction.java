package com.examemed.action;

import com.examemed.dao.UsuarioDAO;
import com.examemed.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(LoginAction.class);

    private String username;
    private String password;
    private Map<String, Object> session;

    public String execute() {
        logger.info("Tentativa de login para o usuário: " + username);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.findByLogin(username);

        if (usuario != null && BCrypt.checkpw(password, usuario.getSenha())) {
            session.put("user", usuario.getLogin()); 
            session.put("lastAccessTime", System.currentTimeMillis()); 
            session.put("tempoInatividade", usuario.getTempoInatividade());
            logger.info("Login bem-sucedido para o usuário: " + username);
            return SUCCESS;
        } else {
            addActionError("Nome de usuário ou senha inválidos.");
            logger.warn("Falha no login para o usuário: " + username);
            return INPUT; //
        }
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
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
