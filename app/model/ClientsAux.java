package model;

import java.util.List;

public class ClientsAux {
    private List<Client> clients;
    private Integer id;

    public ClientsAux(List<Client> clients, Integer id) {
        this.clients = clients;
        this.id = id;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}