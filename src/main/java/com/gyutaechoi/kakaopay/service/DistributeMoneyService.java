package com.gyutaechoi.kakaopay.service;

import java.util.List;
import java.util.Random;

public interface DistributeMoneyService {

    List<Integer> distributeMoney(final int moneyToDrop, int howManyUsers, Random r);
}
