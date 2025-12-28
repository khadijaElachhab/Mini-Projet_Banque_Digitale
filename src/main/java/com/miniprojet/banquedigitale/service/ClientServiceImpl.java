package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.ClientDTO;
import com.miniprojet.banquedigitale.entities.Client;
import com.miniprojet.banquedigitale.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repo;

    @Override
    public Client save(Client client) {
        if (client.getEmail() != null && repo.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        return repo.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));

        if (client.getEmail() != null &&
                !client.getEmail().equals(existing.getEmail()) &&
                repo.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        existing.setNom(client.getNom());
        existing.setEmail(client.getEmail());
        return repo.save(existing);
    }

    @Override
    public Client get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
    }

    @Override
    public List<Client> list() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Client non trouvé");
        }
        repo.deleteById(id);
    }

    @Override
    public List<Client> search(String keyword) {
        return repo.search(keyword);
    }

    @Override
    public ClientDTO getDTO(Long id) {
        Client c = get(id);
        ClientDTO dto = new ClientDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setEmail(c.getEmail());
        dto.setNombreComptes(c.getComptes().size());
        return dto;
    }
}