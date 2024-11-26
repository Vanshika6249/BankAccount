package com.bankaccount.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankaccount.entity.BankAccount;

@Repository
public interface BankRepository extends CrudRepository<BankAccount, Integer> {

}
