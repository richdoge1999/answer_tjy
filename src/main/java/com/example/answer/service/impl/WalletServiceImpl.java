package com.example.answer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.answer.common.TypeConstant;
import com.example.answer.entity.Record;
import com.example.answer.entity.Wallet;
import com.example.answer.mapper.WalletMapper;
import com.example.answer.service.RecordService;
import com.example.answer.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {

    @Autowired
    private RecordService recordService;

    public Wallet getWalletByUserId(String userId) {
        Wallet one = this.lambdaQuery().eq(Wallet::getUserId, userId).one();
        if (Objects.nonNull(one)) {
            return one;
        } else {
            throw new RuntimeException("该用户不存在，请检查");
        }
    }

    @Override
    public BigDecimal getBalance(String userId) {
        return getWalletByUserId(userId).getBalance();
    }

    @Override
    public void consume(String userId, BigDecimal money) {
        Wallet wallet = getWalletByUserId(userId);

        if (wallet.getBalance().compareTo(money) < 0) {
            throw new RuntimeException("余额不足，请先充值");
        }

        wallet.setBalance(wallet.getBalance().subtract(money));
        updateById(wallet);

        Record record = new Record();
        record.setId(System.currentTimeMillis());
        record.setUserId(userId);
        record.setType(TypeConstant.CONSUME);
        record.setAmount(money.negate());
        recordService.save(record);
    }

    @Override
    public void refund(String userId, BigDecimal money) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(wallet.getBalance().add(money));
        updateById(wallet);

        Record record = new Record();
        record.setId(System.currentTimeMillis());
        record.setUserId(userId);
        record.setType(TypeConstant.REFUND);
        record.setAmount(money);
        recordService.save(record);
    }

    @Override
    public List<Record> getRecords(String userId) {
        return recordService.lambdaQuery()
                .eq(Record::getUserId, userId)
                .orderByDesc(Record::getCreateTime).list();
    }
}
