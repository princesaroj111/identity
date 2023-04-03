package com.demo.identity.models;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class IdentityUser extends User {
  private final String walletAuth;

  public IdentityUser(final String username, final String password, final String walletAuth) {
    super(username, password, new ArrayList<>());
    this.walletAuth = walletAuth;
  }

  public String getWalletAuth() {
    return walletAuth;
  }
}
