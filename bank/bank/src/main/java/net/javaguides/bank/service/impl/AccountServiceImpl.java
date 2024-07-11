package net.javaguides.bank.service.impl;

import jakarta.transaction.Transactional;
import net.javaguides.bank.dto.AccountDto;
import net.javaguides.bank.entity.Account;
import net.javaguides.bank.mapper.AccountMapper;
import net.javaguides.bank.repository.AccountRepository;
import net.javaguides.bank.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;



    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    @Override

    public AccountDto createAccount(AccountDto accountDto){
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository
                .findById(id).
                orElseThrow(() -> new RuntimeException("Account not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("account not exist"));
        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("account not exist"));
        if(amount > account.getBalance()){
            throw new RuntimeException("Insufficient Balance");
        }

        double total=account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);


    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<Account> accounts= accountRepository.findAll();
       return  accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("account not exist"));
        accountRepository.deleteById(id);
    }

}



