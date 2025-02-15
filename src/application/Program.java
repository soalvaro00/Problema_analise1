package application;

import Entities.Sale;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String filePath = "c:/temp/in.csv"; // Ajustar o caminho conforme necessário!!!
        List<Sale> sales = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            sales = br.lines().skip(1)
                    .map(line -> line.split(","))
                    .map(data -> new Sale(
                            Integer.parseInt(data[0].trim()),
                            Integer.parseInt(data[1].trim()),
                            data[2].trim(),
                            Integer.parseInt(data[3].trim()),
                            Double.parseDouble(data[4].trim())
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
        sales.stream()
                .filter(s -> s.getYear() == 2016)
                .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                .limit(5)
                .forEach(System.out::println);

        double totalLogan = sales.stream()
                .filter(s -> s.getSeller().equalsIgnoreCase("Logan"))
                .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                .mapToDouble(Sale::getTotal)
                .sum();

        System.out.println("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = " + totalLogan);

            sc.close();
        }
    }
