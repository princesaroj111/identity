package com.demo.identity.models;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class IdentityUser extends User {
  private final String walletAddress;

  public IdentityUser(final String username, final String password, final String walletAddress,
      final Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.walletAddress = walletAddress;
  }

  public String getWalletAddress() {
    return walletAddress;
  }
}
