package com.mzrt.atlas_bank.repository;

import com.mzrt.atlas_bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
