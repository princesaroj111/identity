package com.demo.identity.service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.demo.identity.exception.VerificationFailedException;
import com.demo.identity.models.IdentityUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import trinsic.TrinsicUtilities;
import trinsic.okapi.DidException;
import trinsic.services.TrinsicService;
import trinsic.services.verifiablecredentials.v1.CreateProofRequest;
import trinsic.services.verifiablecredentials.v1.VerifyProofRequest;

@Service
public class VerificationService {

  public Map getItemProof(final String itemId)
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException, JsonProcessingException {
    var trinsic = new TrinsicService(TrinsicUtilities.getTrinsicServiceOptions());
    setupTrinsicContext(trinsic);
    var createProofResponse =
        trinsic
            .credential()
            .createProof(CreateProofRequest.newBuilder().setItemId(itemId).build())
            .get();

    return new ObjectMapper().readValue(createProofResponse.getProofDocumentJson(), Map.class);
  }

  public void verifyProof(final String proof)
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException, VerificationFailedException {
    var trinsic = new TrinsicService(TrinsicUtilities.getTrinsicServiceOptions());
    setupTrinsicContext(trinsic);
    final var verifyProofResponse =
        trinsic
            .credential()
            .verifyProof(
                VerifyProofRequest.newBuilder().setProofDocumentJson(proof).build())
            .get();

    if (!verifyProofResponse.getIsValid()) {
      throw new VerificationFailedException("Proof Verification Failed");
    }
  }

  private void setupTrinsicContext(final TrinsicService trinsic) {
    final Authentication authentication = getContext().getAuthentication();
    final String walletAuth =
        ((IdentityUser) (authentication.getPrincipal())).getWalletAuth();
    trinsic.setAuthToken(walletAuth);
  }
}
