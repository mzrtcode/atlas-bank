package com.mzrt.atlas_bank.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpingDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {
}
