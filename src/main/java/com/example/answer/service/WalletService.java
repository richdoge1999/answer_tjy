package com.example.answer.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.answer.entity.Record;
import com.example.answer.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService extends IService<Wallet> {

    BigDecimal getBalance(String userId);

    void consume(String userId, BigDecimal valueOf);

    void refund(String userId, BigDecimal valueOf);

    List<Record> getRecords(String userId);
}
