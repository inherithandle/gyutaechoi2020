package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyDropRepository extends JpaRepository<MoneyDrop, Long> {
}
