package com.demo.identity.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

  private static final long serialVersionUID = -8091879091924046844L;
  private String jwtToken;
}