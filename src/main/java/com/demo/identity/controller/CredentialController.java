package com.demo.identity.controller;

import static org.springframework.security.core.context.SecurityContextHolder.*;

import com.demo.identity.models.CollegeIdentifier;
import com.demo.identity.models.CredentialContext;
import com.demo.identity.models.IdentityUser;
import com.demo.identity.security.JwtTokenUtil;
import com.demo.identity.service.IssueService;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

  @PostMapping("/issue")
  Map<Descriptors.FieldDescriptor, Object> issueCredential(@RequestBody final CollegeIdentifier collegeIdentifier)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException {
    final Authentication authentication = getContext().getAuthentication();
    final String walletAddress =
        ((IdentityUser) (authentication.getPrincipal())).getWalletAddress();
    final CredentialContext credentialContext =
        new CredentialContext(collegeIdentifier, walletAddress);
    return issueService.issueCredential(credentialContext).getAllFields();
  }

}
