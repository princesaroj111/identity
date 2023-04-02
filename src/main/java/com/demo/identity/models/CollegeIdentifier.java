package com.demo.identity.models;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollegeIdentifier implements Serializable {
  private String name;
  private String CollegeName;
}
