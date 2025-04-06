package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductRepository listaDeProdutos = null;
        UserRepository listaDeUsuarios = null;

        Connection conn = null;

        // Parâmetros de conexão
        String url = "jdbc:sqlite:database.sqlite";

        // Tentativa de conexão
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                listaDeProdutos = new ProductRepository(conn);
                listaDeUsuarios = new UserRepository(conn);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n--- CADASTRO DE PRODUTO ---\n");

                    System.out.print("Nome do produto: ");
                    String nomeProduto = scanner.nextLine();

                    System.out.print("Preço do produto: ");
                    double precoProduto = scanner.nextDouble();
                    scanner.nextLine();

                    listaDeProdutos.save(new Product(nomeProduto, precoProduto));
                    System.out.println("Produto cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE PRODUTOS ---\n");
                    List<Product> products = listaDeProdutos.findAll();

                    if (products.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Product produto : products) {
                            System.out.println("ID: " + produto.getUuid());
                            System.out.println("Nome: " + produto.getName());
                            System.out.println("Preço: R$" + produto.getPrice());
                            System.out.println();
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- CADASTRO DE USUÁRIO ---\n");

                    System.out.print("Nome completo: ");
                    String nomeUsuario = scanner.nextLine();

                    System.out.print("E-mail: ");
                    String emailUsuario = scanner.nextLine();

                    System.out.print("Senha: ");
                    String senhaUsuario = scanner.nextLine();

                    listaDeUsuarios.save(new User(nomeUsuario, emailUsuario, senhaUsuario));
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE USUÁRIOS ---\n");
                    List<User> users = listaDeUsuarios.findAll();

                    if (users.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        for (User usuario : users) {
                            System.out.println("ID: " + usuario.getUuid());
                            System.out.println("Nome: " + usuario.getName());
                            System.out.println("E-mail: " + usuario.getEmail());
                            System.out.println();
                        }
                    }
                    break;

                case 5:
                    System.out.println("\nSaindo do sistema...");
                    break;

                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }

        } while (option != 5);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}