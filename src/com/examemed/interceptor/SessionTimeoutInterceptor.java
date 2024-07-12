package com.examemed.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class SessionTimeoutInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(SessionTimeoutInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        Object user = session.get("user");
        Long lastAccessTime = (Long) session.get("lastAccessTime");
        Integer tempoInatividade = (Integer) session.get("tempoInatividade");

        if (user == null) {
            logger.warn("Usuário não está logado. Redirecionando para a página de login.");
            redirectToLogin();
            return null;
        }

        if (lastAccessTime != null && tempoInatividade != null) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastAccessTime;

            logger.info("Tempo de inatividade: " + (elapsedTime / 1000 / 60) + " minutos");

            if (elapsedTime > tempoInatividade * 60 * 1000) {
                session.clear();
                logger.warn("Sessão expirada devido à inatividade. Redirecionando para a página de login.");
                redirectToLogin();
                return null;
            }
        }

        session.put("lastAccessTime", System.currentTimeMillis());
        return invocation.invoke();
    }

    private void redirectToLogin() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.sendRedirect(ServletActionContext.getRequest().getContextPath() + "/login.jsp");
    }
}
