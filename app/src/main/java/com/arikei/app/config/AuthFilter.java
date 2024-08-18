package com.arikei.app.config;

import java.io.IOException;
import org.springframework.web.filter.GenericFilterBean;
import com.arikei.grpc.verify.lib.VerifyRequest;
import com.arikei.grpc.verify.lib.VerifyResponse;
import com.arikei.grpc.verify.lib.VerifyGrpc.VerifyBlockingStub;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * Authサーバへトークンの検証をリクエストする
 */
@AllArgsConstructor
public class AuthFilter extends GenericFilterBean {

  private VerifyBlockingStub verifyBlockingStub;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String requestToken =
        httpServletRequest.getHeader("Authorization").replaceFirst("Bearer", "").trim();

    VerifyRequest verifyRequest = VerifyRequest.newBuilder().setToken(requestToken).build();
    VerifyResponse verifyResponse = null;
    try {
      verifyResponse = verifyBlockingStub.verify(verifyRequest);
    } catch (StatusRuntimeException e) {
      e.printStackTrace();
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
          "The token is not valid.");
      return;
    }
    request.setAttribute("userName", verifyResponse.getUser().getName());
    request.setAttribute("userRole", verifyResponse.getUser().getRole());

    chain.doFilter(request, response);
  }
}
