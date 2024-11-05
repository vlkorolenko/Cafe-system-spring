package org.example.cafespring.controller;

import org.example.cafespring.model.Client;
import org.example.cafespring.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String showAllClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @PostMapping("/clients/add")
    public String addClient(@ModelAttribute Client client) {
        clientService.addClient(client);
        return "redirect:/clients";
    }

    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam("id") int id, Model model) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String editClient(@PathVariable("id") int id, Model model) {
        Client client = clientService.findById(id);
        model.addAttribute("client", client);
        return "edit-clients";
    }

    @PostMapping("/clients/edit")
    public String editClient(@ModelAttribute Client client) {
        clientService.updateClient(client);
        return "redirect:/clients";
    }
}
