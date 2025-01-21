package com.ademarporto.ba.repository;

import com.ademarporto.ba.repository.entity.AccountRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRequestRepository extends JpaRepository<AccountRequestEntity, UUID> {

    @Query(value = """
        SELECT * FROM account_requests ac
            where ac.name = :name
              and ac.date_of_birth = :dateOfBirth
              and ac.post_code = :postCode
              and ac.house_number = :houseNumber
    """,
    nativeQuery = true)
    Optional<AccountRequestEntity> findAccountRequest(String name, LocalDate dateOfBirth, String postCode, String houseNumber);
}
