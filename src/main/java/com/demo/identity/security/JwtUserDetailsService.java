package com.demo.identity.security;

import com.demo.identity.models.IdentityUser;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  private String walletAuth =
      "CiVodHRwczovL3RyaW5zaWMuaWQvc2VjdXJpdHkvdjEvb2Jlcm9uEmQKK3Vybjp0cmluc2ljOndhbGxldHM6elJnRllSbjRkSnV5QWVBYnlwZkJ4alYiNXVybjp0cmluc2ljOmVjb3N5c3RlbXM6ZGlzdHJhY3RlZC1zd2FydHotemN6bXVnaWtqc2J3GjC1pcpc0h-MVCMrj5V8X0WouwSW21W6Qbne7kuZmYRn8u127JfsUHZeqrc6E92xkwAiAA==";

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    if ("prince".equals(username)) {
      return new IdentityUser("prince",
          "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", walletAuth,
          new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
