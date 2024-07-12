package com.examemed.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AuthenticationInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AuthenticationInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        Object user = session.get("user");
        if (user == null) {
            logger.warn("User not logged in. Redirecting to login page.");
            return "login";
        } else {
            logger.info("User is logged in. Proceeding with action.");
            return invocation.invoke();
        }
    }
}
