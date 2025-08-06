package com.greenbook.account.controller;

import com.greenbook.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {


    @Autowired
    AccountService accountService;
    /**
     * 扣减账户余额
     * @return
     */
    @GetMapping("/debit")
    public String debit(@RequestParam("userId") String userId,
                        @RequestParam("money") int money){
        accountService.debit(userId, money);
        return "account debit success";
    }
}
