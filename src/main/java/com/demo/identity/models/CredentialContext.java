package com.demo.identity.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CredentialContext {
  private CollegeIdentifier collegeIdentifier;
  private String walletAddress;
}
