package com.devops.groupb.harbourmaster.web;


import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
public class WebOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public String showAllOrders(Model model) {
        String ordersModelName = "orders";

        List<Order> orders = orderService.findAll();

        model.addAttribute(ordersModelName, orders);

        return "pages/listOrders";
    }

}
