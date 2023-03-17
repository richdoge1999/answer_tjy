package com.example.answer.controller;


import com.example.answer.entity.Record;
import com.example.answer.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author :RichDoge
 * @Date :2023/3/17 10:30
 * @Description :用户钱包
 * @Since :version-1.0
 */

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * 查询用户钱包余额
     */
    @GetMapping("/balance/{userId}")
    public BigDecimal getBalance(@PathVariable String userId) {
        return walletService.getBalance(userId);
    }

    /**
     * 用户消费100元的接口
     */
    @PostMapping("/consume/{userId}")
    public void consume(@PathVariable String userId) {
        walletService.consume(userId, BigDecimal.valueOf(100));
    }

    /**
     * 用户退款20元接口
     */
    @PostMapping("/refund/{userId}")
    public void refund(@PathVariable String userId) {
        walletService.refund(userId, BigDecimal.valueOf(20));
    }

    /**
     * 查看交易流水
     */

    @GetMapping("/record/{userId}")
    public List<Record> getRecords(@PathVariable String userId) {
        return walletService.getRecords(userId);
    }
}
