package com.demo.identity.security;

import com.demo.identity.models.IdentityUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  final Map<String, IdentityUser> localUserMap = new HashMap<>();

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    fillLocalUsers();
    return localUserMap.get(username);
  }

  private void fillLocalUsers() {
    String walletAuth_prince =
        "CiVodHRwczovL3RyaW5zaWMuaWQvc2VjdXJpdHkvdjEvb2Jlcm9uEmQKK3Vybjp0cmluc2ljOndhbGxldHM6elJnRllSbjRkSnV5QWVBYnlwZkJ4alYiNXVybjp0cmluc2ljOmVjb3N5c3RlbXM6ZGlzdHJhY3RlZC1zd2FydHotemN6bXVnaWtqc2J3GjC1pcpc0h-MVCMrj5V8X0WouwSW21W6Qbne7kuZmYRn8u127JfsUHZeqrc6E92xkwAiAA==";

    // TODO change below address, we used issuer address here
    String walletAuth_hardik =
        "CiVodHRwczovL3RyaW5zaWMuaWQvc2VjdXJpdHkvdjEvb2Jlcm9uEmQKK3Vybjp0cmluc2ljOndhbGxldHM6elJURXJWaVlVVnhEVDQ5dXdFUHkzYloiNXVybjp0cmluc2ljOmVjb3N5c3RlbXM6ZGlzdHJhY3RlZC1zd2FydHotemN6bXVnaWtqc2J3GjCEmwjz63eOVo8kQWDNSQ1KzvF7bCUALPcAyYvaYKDRKs3XVEjUx6dk5qU4clEyW_YiAA==";

    localUserMap.put("prince",
        new IdentityUser("prince", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
            walletAuth_prince));
    localUserMap.put("hardik",
        new IdentityUser("hardik", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
            walletAuth_hardik));
  }
}
