package org.balaji.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * The Entry Point will not redirect to any sort of Login - it will return the 401
 */
@Component
public final class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	static Log logger = LogFactory.getLog(RestAuthenticationEntryPoint.class);
	
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
    	logger.debug("--------------In the commence method of the RestAuthenticationEntryPoint class-----------");
    	// as per the HTTP protocol for basic authentication if the server wants the client to authenticate, it has to send the 401 http status along with the Authenticate header
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        
        response.addHeader("WWW-Authenticate", "Basic realm=\""+ getRealmName()+"\"");
        
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - "+authException.getMessage());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    	// TODO Auto-generated method stub
    	logger.debug("--------------In the afterPropertiesSet method of the RestAuthenticationEntryPoint class setting Realm Name-----------");
    	setRealmName("Balaji Swaroop");
    	super.afterPropertiesSet();
    }

}