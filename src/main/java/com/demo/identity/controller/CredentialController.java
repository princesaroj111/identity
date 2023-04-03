package com.demo.identity.controller;

import static org.springframework.security.core.context.SecurityContextHolder.*;

import com.demo.identity.exception.VerificationFailedException;
import com.demo.identity.models.CollegeIdentifier;
import com.demo.identity.models.IdentityUser;
import com.demo.identity.security.JwtTokenUtil;
import com.demo.identity.service.IssueService;
import com.demo.identity.service.VerificationService;
import com.demo.identity.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolStringList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import trinsic.okapi.DidException;

@RestController()
public class CredentialController {

  @Autowired
  private IssueService issueService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WalletService walletService;

  @Autowired
  private VerificationService verificationService;

  @PostMapping("/issue")
  Map<Descriptors.FieldDescriptor, Object> issueCredential(
      @RequestBody final CollegeIdentifier collegeIdentifier)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException {
    final Authentication authentication = getContext().getAuthentication();
    final String walletAuth =
        ((IdentityUser) (authentication.getPrincipal())).getWalletAuth();
    return issueService.issueCredential(collegeIdentifier).getAllFields();
  }

  @GetMapping("wallet/items")
  ProtocolStringList getWalletCredentials()
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException {
    return walletService.listWalletItems();
  }

  @GetMapping("wallet/items/{itemId}")
  Map<String, Object> getCredential(@PathVariable final String itemId)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException, JsonProcessingException {
    return walletService.getWalletItem(itemId);
  }

  @GetMapping("wallet/items/{itemId}/proof")
  Map<String, Object> getVerificationProof(@PathVariable final String itemId)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      JsonProcessingException, DidException {
    return verificationService.getItemProof(itemId);
  }

  @PostMapping("/verify")
  void verifyCredential(
      @RequestBody final Map<String, Object> proof)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException, VerificationFailedException {
    verificationService.verifyProof(proof);
  }

}
