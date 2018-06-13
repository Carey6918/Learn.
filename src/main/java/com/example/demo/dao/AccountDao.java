package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account,String >{
    Account save(Account account);
    Account findAccountByEmail(String email);
}
