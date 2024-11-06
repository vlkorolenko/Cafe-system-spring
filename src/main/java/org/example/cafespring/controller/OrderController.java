package org.example.cafespring.controller;

import org.example.cafespring.model.Order;
import org.example.cafespring.service.ClientService;
import org.example.cafespring.service.DishService;
import org.example.cafespring.service.EmployeeService;
import org.example.cafespring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final DishService dishService;

    @Autowired
    public OrderController(OrderService orderService, ClientService clientService, EmployeeService employeeService, DishService dishService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.dishService = dishService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderService.findAll();
        Map<Integer, String> clientNames = clientService.findAll().stream().collect(Collectors.toMap(client -> client.getId(), client -> client.getFull_name()));
        Map<Integer, String> employeeNames = employeeService.findAll().stream().collect(Collectors.toMap(employee -> employee.getId(), employee -> employee.getFull_name()));
        Map<Integer, String> dishNames = dishService.findAll().stream().collect(Collectors.toMap(dish -> dish.getId(), dish -> dish.getName()));

        model.addAttribute("orders", orders);
        model.addAttribute("clientNames", clientNames);
        model.addAttribute("employeeNames", employeeNames);
        model.addAttribute("dishNames", dishNames);

        return "orders";
    }

    @GetMapping("/orders/add")
    public String showAddOrderForm(Model model) {
        Order order = new Order();
        order.setOrderItems(new ArrayList<>()); // Ініціалізуємо пустий список позицій замовлення
        model.addAttribute("order", order);
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("dishes", dishService.findAll());
        return "add-order";
    }

    @PostMapping("/orders/add")
    public String addOrder(@ModelAttribute Order order) {
        // Обчислення загальної ціни
        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(item -> dishService.findDishById(item.getDishId()).getPrice().doubleValue() * item.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);

        // Зберігаємо замовлення і його позиції
        orderService.save(order);
        return "redirect:/orders";
    }


    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable int id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("dishes", dishService.findAll());
        return "edit-order";
    }

    @PostMapping("/orders/edit")
    public String editOrder(@ModelAttribute Order order) {
        // Обчислення загальної ціни
        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(item -> dishService.findDishById(item.getDishId()).getPrice().doubleValue() * item.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);
        orderService.update(order);
        return "redirect:/orders";
    }

    @PostMapping("/orders/delete")
    public String deleteOrder(@RequestParam("id") int id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
