package com.demo.identity.service;

import com.demo.identity.digimocker.DigiMockerConnector;
import com.demo.identity.models.CollegeIdentifier;
import com.demo.identity.models.CredentialContext;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import trinsic.TrinsicUtilities;
import trinsic.okapi.DidException;
import trinsic.services.TemplateService;
import trinsic.services.TrinsicService;
import trinsic.services.universalwallet.v1.InsertItemRequest;
import trinsic.services.universalwallet.v1.InsertItemResponse;
import trinsic.services.verifiablecredentials.templates.v1.CreateCredentialTemplateRequest;
import trinsic.services.verifiablecredentials.templates.v1.GetCredentialTemplateRequest;
import trinsic.services.verifiablecredentials.templates.v1.TemplateField;
import trinsic.services.verifiablecredentials.v1.IssueFromTemplateRequest;

@Service
public class IssueService {
  private static final String TEMPLATE_ID = "urn:template:distracted-swartz-zczmugikjsbw:collegeverificationcertificates";
  private final static String ISSUER =
      "CiVodHRwczovL3RyaW5zaWMuaWQvc2VjdXJpdHkvdjEvb2Jlcm9uEmQKK3Vybjp0cmluc2ljOndhbGxldHM6elJURXJWaVlVVnhEVDQ5dXdFUHkzYloiNXVybjp0cmluc2ljOmVjb3N5c3RlbXM6ZGlzdHJhY3RlZC1zd2FydHotemN6bXVnaWtqc2J3GjCEmwjz63eOVo8kQWDNSQ1KzvF7bCUALPcAyYvaYKDRKs3XVEjUx6dk5qU4clEyW_YiAA==";

  public InsertItemResponse issueCredential(final CredentialContext credentialContext)
      throws InvalidProtocolBufferException, ExecutionException, InterruptedException,
      DidException {
    var trinsic = new TrinsicService(TrinsicUtilities.getTrinsicServiceOptions());

    String userName = credentialContext.getCollegeIdentifier().getName();
    String userEmail = credentialContext.getCollegeIdentifier().getEmail();
    String collegeName = credentialContext.getCollegeIdentifier().getCollegeName();
    if (!DigiMockerConnector.isCollegeCredentialValid(userName, userEmail, collegeName))
    {
      throw new RuntimeException("College Credentials could not be fetched");
    }

    var credential = issueCredential(trinsic, TEMPLATE_ID, credentialContext.getCollegeIdentifier());
    trinsic.setAuthToken(credentialContext.getWalletAddress());
    var insertItemResponse =
        trinsic
            .wallet()
            .insertItem(InsertItemRequest.newBuilder().setItemJson(credential).build())
            .get();
    return insertItemResponse;
  }

  private String issueCredential(
      final TrinsicService trinsicService, final String templateId,
      final CollegeIdentifier collegeIdentifier)
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException {
    // issueCredential() {
    // Set active profile to 'clinic' so we can issue credential signed
    // with the clinic's signing keys
    trinsicService.setAuthToken(ISSUER);

    // Prepare credential values
    var valuesMap = new HashMap<String, Object>();
    valuesMap.put("Name", collegeIdentifier.getName());
    valuesMap.put("CollegeName", collegeIdentifier.getCollegeName());

    // Serialize values to JSON
    var valuesJson = new Gson().toJson(valuesMap);

    // Issue credential
    var issueResponse =
        trinsicService
            .credential()
            .issueFromTemplate(
                IssueFromTemplateRequest.newBuilder()
                    .setTemplateId(templateId)
                    .setValuesJson(valuesJson)
                    .build())
            .get();

    // }

    return issueResponse.getDocumentJson();
  }

  //Below to be used for creating new template
  private String defineTemplate(final TemplateService templateService)
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException {
    // createTemplate() {
    // Set active profile to 'clinic'
    templateService.setAuthToken(ISSUER);

    // Define fields for template
    var fields = new HashMap<String, TemplateField>();
    fields.put(
        "Name",
        TemplateField.newBuilder().setDescription("Name of the person to Issue credential for")
            .build());
    fields.put(
        "CollegeName",
        TemplateField.newBuilder()
            .setDescription("College Name where the credential requester has passed out from")
            .build());

    // Create template request
    var templateRequest =
        CreateCredentialTemplateRequest.newBuilder()
            .setName("CollegeVerificationCertificates")
            .setAllowAdditionalFields(true)
            .putAllFields(fields)
            .build();

    // Execute template creation
    var template = templateService.create(templateRequest).get();
    // }

    System.out.println(template.getData().getId());
    return template.getData().getId();
  }
}
