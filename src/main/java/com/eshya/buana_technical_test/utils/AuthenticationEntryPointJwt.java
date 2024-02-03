package com.eshya.buana_technical_test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointJwt.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());

    response.setContentType("application/json");
    int statusCode;
    String errorType, errorMessage;
    // Switching auth Exception by message
    if (authException.getMessage().contains("Bad credentials")) {
      statusCode = HttpServletResponse.SC_NOT_FOUND;
      errorType = "Unauthorized";
      errorMessage = "Username or password is wrong!";
    } else if (authException.getMessage().contains("Full authentication")) {
      statusCode = HttpServletResponse.SC_UNAUTHORIZED;
      errorType = "Unauthorized";
      errorMessage = "Token is invalid or expired!";
    } else {
      statusCode = HttpServletResponse.SC_NOT_FOUND;
      errorType = "Unauthorized";
      errorMessage = "Something Wrong with Token!";
    }

    response.setStatus(statusCode);

    final Map<String, Object> body = new HashMap<>();
    body.put("status", statusCode);
    body.put("error", errorType);
    body.put("message", errorMessage);
    body.put("path", request.getServletPath());
    body.put("error_Code", HttpStatus.valueOf(statusCode));

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
//    throw new ResourceNotFoundException(String.format("Username or password is wrong!"),"username or password","username or password");
  }

}

