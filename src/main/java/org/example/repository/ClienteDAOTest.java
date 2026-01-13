package org.example.repository;

import org.example.model.Cliente;

import java.util.List;

public class ClienteDAOTest {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        try {
            System.out.println("Inserindo cliente de teste...");
            Cliente c = new Cliente("João Silva", "123.456.789-00", "joao@example.com");
            dao.insert(c);
            System.out.println("Inserido: " + c);

            System.out.println("Listando clientes:");
            List<Cliente> all = dao.findAll();
            all.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro durante teste DAO: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

