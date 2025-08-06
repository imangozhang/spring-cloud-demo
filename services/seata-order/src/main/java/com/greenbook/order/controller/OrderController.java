package com.greenbook.order.controller;


import com.greenbook.order.bean.OrderTbl;
import com.greenbook.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @return
     */
    @GetMapping("/create")
    public String create(@RequestParam("userId") String userId,
                         @RequestParam("commodityCode") String commodityCode,
                         @RequestParam("count") int orderCount)
    {
        OrderTbl tbl = orderService.create(userId, commodityCode, orderCount);
        return "order create success = 订单id：【"+tbl.getId()+"】";
    }

}
