package com.ishan.bankingservice.accounts.adapters.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.accounts.domain.AccountCreated;
import com.ishan.bankingservice.accounts.domain.AccountEvent;
import com.ishan.bankingservice.accounts.domain.MoneyDeposited;

public class DefaultAccountEventParser implements AccountEventParser {

  @Override
  public void setNext(AccountEventParser nextParser) {

  }

  @Override
  public AccountEvent parse(Fact fact, ObjectMapper mapper) {
    String type = fact.getFactType();
    String event = fact.getFact();
    AccountEvent accountEvent = null;
    try {
      if ("AccountCreated".equals(type)) {
        accountEvent = mapper.readValue(event, AccountCreated.class);
      } else if ("MoneyDeposited".equals(type)) {
        accountEvent = mapper.readValue(event, MoneyDeposited.class);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return accountEvent;
  }

}
