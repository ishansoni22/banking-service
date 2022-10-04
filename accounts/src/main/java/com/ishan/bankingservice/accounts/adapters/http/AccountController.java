package com.ishan.bankingservice.accounts.adapters.http;

import com.ishan.bankingservice.accounts.application.AccountApplicationService;
import com.ishan.bankingservice.accounts.application.AccountResource;
import com.ishan.bankingservice.accounts.application.CreateAccountCommand;
import com.ishan.bankingservice.accounts.application.MoneyDepositCommand;
import com.ishan.bankingservice.accounts.application.MoneyTransferCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

  @Autowired
  private AccountApplicationService accountApplicationService;

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountResource> create(@RequestBody CreateAccountRequest createAccountRequest) {
    return ResponseEntity.ok(this.accountApplicationService
        .createAccount(new CreateAccountCommand(createAccountRequest.getOwnerId())));
  }

  @PostMapping(value = "/{accountId}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountResource> deposit(@PathVariable("accountId") String accountId,
      @RequestBody MoneyDepositRequest moneyDepositRequest) {
    return ResponseEntity.ok(this.accountApplicationService.deposit(
        new MoneyDepositCommand(accountId, moneyDepositRequest.getAmount())));
  }

  @PostMapping("/{accountId}/withdraw")
  public ResponseEntity<AccountResource> withdraw(@PathVariable("accountId") String accountId,
      @RequestBody MoneyWithdrawRequest moneyWithdrawRequest) {
    return ResponseEntity.ok(this.accountApplicationService.withdraw(
        new MoneyWithdrawCommand(accountId, moneyWithdrawRequest.getAmount())));
  }

  @PostMapping("/{accountId}/transfer")
  public ResponseEntity<AccountResource> transfer(@PathVariable("accountId") String accountId,
    @RequestBody MoneyTransferRequest moneyTransferRequest) {
  return ResponseEntity.ok(this.accountApplicationService.transfer(
      new MoneyTransferCommand(
          accountId,
          moneyTransferRequest.getToAccountId(),
          true,
          moneyTransferRequest.getAmount(),
          moneyTransferRequest.getReference()
      )));
  }

  @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountResource> get(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.accountApplicationService.get(accountId));
  }

}
