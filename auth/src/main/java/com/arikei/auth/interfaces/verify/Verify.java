package com.arikei.auth.interfaces.verify;

import com.arikei.auth.domains.JwtGenerator;
import com.arikei.auth.domains.Verifier;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.entities.VerifyInfo;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;
import com.arikei.grpc.verify.lib.VerifyGrpc;
import com.arikei.grpc.verify.lib.VerifyRequest;
import com.arikei.grpc.verify.lib.VerifyResponse;
import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class Verify extends VerifyGrpc.VerifyImplBase {

  private final JwtKeyRepositoryIF jwtKeyRepositoryIF;
  private final UserRepositoryIF userRepositoryIF;

  public Verify(JwtKeyRepositoryIF jrIf, UserRepositoryIF urIf) {
    this.jwtKeyRepositoryIF = jrIf;
    this.userRepositoryIF = urIf;
  }

  @Override
  public void verify(VerifyRequest request, StreamObserver<VerifyResponse> responseObserver) {

    VerifyInfo verifyInfo = new VerifyInfo(request.getToken());

    Verifier verifier =
        new Verifier(new JwtGenerator(this.jwtKeyRepositoryIF), this.userRepositoryIF);
    User user = null;
    try {
      user = verifier.verify(verifyInfo);
    } catch (IllegalArgumentException e) {
      Status status = Status.newBuilder().setCode(Code.UNAUTHENTICATED.getNumber())
          .setMessage("Unauthenticated").build();
      responseObserver.onError(StatusProto.toStatusRuntimeException(status));
      return;
    }
    VerifyResponse.User responseUser =
        VerifyResponse.User.newBuilder().setName(user.getName()).setRole(user.getRole()).build();

    VerifyResponse reply = VerifyResponse.newBuilder().setUser(responseUser).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
