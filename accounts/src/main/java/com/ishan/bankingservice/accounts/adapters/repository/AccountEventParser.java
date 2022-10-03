package com.ishan.bankingservice.accounts.adapters.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.accounts.domain.AccountEvent;

public interface AccountEventParser {

  void setNext(AccountEventParser nextParser);

  AccountEvent parse(Fact fact, ObjectMapper mapper);

}
