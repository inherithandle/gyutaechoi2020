package com.gyutaechoi.kakaopay.entity;

import javax.persistence.*;

@Entity
public class MoneyGetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MoneyGetterNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moneyDropNo")
    private MoneyDrop moneyDrop;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moneyGetterUserNo")
    private KakaoPayUser moneyGetterUser;

    @Column(nullable = false)
    private Integer amount; // 받은 금액

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyGetter that = (MoneyGetter) o;

        if (!getMoneyGetterNo().equals(that.getMoneyGetterNo())) return false;
        return getMoneyGetterUser().getUserNo().equals(that.getMoneyGetterUser().getUserNo());
    }

    @Override
    public int hashCode() {
        int result = getMoneyGetterNo().hashCode();
        result = 31 * result + getMoneyGetterUser().hashCode();
        return result;
    }

    public MoneyDrop getMoneyDrop() {
        return moneyDrop;
    }

    public void setMoneyDrop(MoneyDrop moneyDrop) {
        this.moneyDrop = moneyDrop;
    }

    public Long getMoneyGetterNo() {
        return MoneyGetterNo;
    }

    public void setMoneyGetterNo(Long moneyGetterNo) {
        MoneyGetterNo = moneyGetterNo;
    }

    public KakaoPayUser getMoneyGetterUser() {
        return moneyGetterUser;
    }

    public void setMoneyGetterUser(KakaoPayUser moneyGetterUser) {
        this.moneyGetterUser = moneyGetterUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
