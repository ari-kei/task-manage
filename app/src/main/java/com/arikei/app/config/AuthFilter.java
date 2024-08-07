package com.arikei.app.config;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.filter.GenericFilterBean;
import com.arikei.grpc.verify.lib.VerifyGrpc;
import com.arikei.grpc.verify.lib.VerifyRequest;
import com.arikei.grpc.verify.lib.VerifyResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Authサーバへトークンの検証をリクエストする
 */
@Component
public class AuthFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String requestToken = httpServletRequest.getHeader("Authorization").replaceFirst("Bearer", "").trim();

    // TODO サーバとポートを環境毎に変更できるようにする
    final String server = "localhost";
    final int port = 8080;
    ManagedChannel channel = ManagedChannelBuilder.forAddress(server, port).usePlaintext().build();
    VerifyGrpc.VerifyBlockingStub stub = VerifyGrpc.newBlockingStub(channel);
    VerifyRequest verifyRequest = VerifyRequest.newBuilder().setToken(requestToken).build();

    VerifyResponse verifyResponse = null;
    try {
      verifyResponse = stub.verify(verifyRequest);
    } catch (StatusRuntimeException e) {
      e.printStackTrace();
      return;
    }

    RequestContextHolder.currentRequestAttributes().setAttribute("userInfo", verifyResponse.getUser(),
        RequestAttributes.SCOPE_REQUEST);

    chain.doFilter(request, response);
  }
}
