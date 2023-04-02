package com.demo.identity.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class CredentialContext  implements Serializable {
  private CollegeIdentifier collegeIdentifier;
  private String walletAddress;
}
