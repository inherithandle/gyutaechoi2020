package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoneyDropRepository extends JpaRepository<MoneyDrop, Long> {

    @Query("select m from MoneyDrop m left join fetch m.moneyGetters mg left join fetch mg.moneyGetterUser u where m.token = :token")
    Optional<MoneyDrop> findMoneyDropAndMoneyGetterByToken(String token);

    Optional<MoneyDrop> findMoneyDropByToken(String token);

    @Query("select m from MoneyDrop m left join fetch m.moneyGetters mg where m.token = :token and mg.moneyGetterUser.userNo = :userNo")
    Optional<MoneyDrop> findMoneyDropByTokenAndUserNo(String token, long userNo);
}
