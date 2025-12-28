package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.ClientDTO;
import com.miniprojet.banquedigitale.entities.Client;
import java.util.List;

public interface ClientService {
    Client save(Client client);
    Client update(Long id, Client client);
    Client get(Long id);
    List<Client> list();
    void delete(Long id);
    List<Client> search(String keyword);
    ClientDTO getDTO(Long id);
}