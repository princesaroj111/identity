package com.demo.identity.digimocker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DigiMockerResponse
{
    private String name;
    private String email;
    private String collegeName;
    private String url;
}
