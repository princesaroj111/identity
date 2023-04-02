package com.demo.identity.controller;

import com.demo.identity.models.CollegeIdentifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class CredentialController {

  @PostMapping ("/issue")
  String issueCredential(@RequestBody final CollegeIdentifier collegeIdentifier){


    return null;

  }

}
