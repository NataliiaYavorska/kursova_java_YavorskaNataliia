package com.itvdn.cbs.Security.services;

import com.itvdn.cbs.Security.models.Orders;
import com.itvdn.cbs.Security.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Transactional
    public void register(Orders order) {
        ordersRepository.save(order);
    }
}
