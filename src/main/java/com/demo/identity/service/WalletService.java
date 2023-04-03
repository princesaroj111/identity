package com.demo.identity.service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.demo.identity.models.IdentityUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolStringList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import trinsic.TrinsicUtilities;
import trinsic.okapi.DidException;
import trinsic.services.TrinsicService;
import trinsic.services.universalwallet.v1.GetItemRequest;

@Service
public class WalletService {

  public ProtocolStringList listWalletItems()
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException {
    var trinsic = new TrinsicService(TrinsicUtilities.getTrinsicServiceOptions());
    setupTrinsicContext(trinsic);
    var walletItems = trinsic.wallet().searchWallet().get();
    return walletItems.getItemsList();
  }

  public Map<String, Object> getWalletItem(final String itemId)
      throws InvalidProtocolBufferException, DidException, ExecutionException,
      InterruptedException, JsonProcessingException {
    var trinsic = new TrinsicService(TrinsicUtilities.getTrinsicServiceOptions());
    setupTrinsicContext(trinsic);
    var getResponse =
        trinsic.wallet().getItem(GetItemRequest.newBuilder().setItemId(itemId).build()).get();
    ObjectMapper mapper = new ObjectMapper();
    Map<String,Object> map = mapper.readValue(getResponse.getItemJson(), Map.class);
    return map;
  }

  private void setupTrinsicContext(final TrinsicService trinsic) {
    final Authentication authentication = getContext().getAuthentication();
    final String walletAuth =
        ((IdentityUser) (authentication.getPrincipal())).getWalletAuth();
    trinsic.setAuthToken(walletAuth);
  }
}
