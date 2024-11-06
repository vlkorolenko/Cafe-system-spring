package org.example.cafespring.service;

import org.example.cafespring.model.Order;
import org.example.cafespring.model.OrderItem;
import org.example.cafespring.repository.OrderDAO;
import org.example.cafespring.repository.OrderItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;

    @Autowired
    public OrderService(OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
    }

    public List<Order> findAll() {
        List<Order> orders = orderDAO.findAll();
        for (Order order : orders) {
            List<OrderItem> items = orderItemDAO.findByOrderId(order.getOrderId());
            order.setOrderItems(items);
        }
        return orders;
    }

    public Order findById(int orderId) {
        Order order = orderDAO.findById(orderId);
        List<OrderItem> items = orderItemDAO.findByOrderId(orderId);
        order.setOrderItems(items);
        return order;
    }

    public void save(Order order) {
        // Спочатку зберігаємо замовлення, щоб отримати його ID
        int orderId = orderDAO.add(order);

        // Зберігаємо позиції замовлення з отриманим ID замовлення
        for (OrderItem item : order.getOrderItems()) {
            item.setOrderId(orderId);
            orderItemDAO.add(item);
        }
    }


    public void update(Order order) {
        orderDAO.update(order);
        for (OrderItem item : order.getOrderItems()) {
            orderItemDAO.update(item);
        }
    }

    public void delete(int orderId) {
        orderDAO.delete(orderId);
        orderItemDAO.delete(orderId);
    }
}
