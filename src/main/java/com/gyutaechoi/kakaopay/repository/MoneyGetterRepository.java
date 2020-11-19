package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.MoneyGetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoneyGetterRepository extends JpaRepository<MoneyGetter, Long> {

    @Query("select m from MoneyGetter m where m.moneyDrop.moneyDropNo = :moneyDropNo and m.moneyGetterUser.userNo = :userNo")
    Optional<MoneyGetter> findMoneyGetterByMoneyDropNoAndUserNo(long moneyDropNo, long userNo);

    @Query("select m from MoneyGetter m where m.moneyGetterUser.userNo = :userNo")
    Optional<MoneyGetter> findByUserNo(long userNo);
}
