package Structs;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class listaInvertida{
    private Map<String, List<filme>> index;

    // Construtor
    public listaInvertida() {
        index = new HashMap<>();
    }

    // Método para adicionar um filme à lista invertida
    public void insert(filme filme) {
        String tipo = filme.getType();

        // Verifica se o tipo já existe no índice, se não, cria uma nova lista vazia
        if (!index.containsKey(tipo)) {
            index.put(tipo, new ArrayList<>());
        }

        // Adiciona o filme à lista associada ao tipo
        index.get(tipo).add(filme);
    }

    public Boolean remove(filme filme) {
        boolean resp = false;
        String tipo = filme.getType();

        // Verifica se o tipo existe no índice
        if (index.containsKey(tipo)) {
            // Remove o filme da lista associada ao tipo
            index.get(tipo).remove(filme);

            // Verifica se a lista associada ao tipo ficou vazia após a remoção do filme
            // Se sim, remove o tipo do índice
            if (index.get(tipo).isEmpty()) {
                index.remove(tipo);
            }
            resp = true;
        }
        return resp;
    }

    // Método para realizar uma consulta na lista invertida
    public List<filme> search(String tipo) {
        // Verifica se o tipo existe no índice
        if (index.containsKey(tipo)) {
            // Retorna a lista de filmes associada ao tipo
            return index.get(tipo);
        } else {
            // Se o tipo não existe, retorna null
            return null;
        }
    }

    public void printAllList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("saidas/saidaAllListaInvertida.txt"))) {
            // Percorre o índice e escreve cada tipo e seus respectivos filmes no arquivo
            for (Map.Entry<String, List<filme>> entry : index.entrySet()) {
                String tipo = entry.getKey();
                List<filme> filmes = entry.getValue();
                writer.write("Tipo: " + tipo);
                writer.newLine();
                writer.write("Filmes: ");
                writer.newLine();
                for (filme filme : filmes) {
                    writer.write(filme.getName());
                    writer.newLine();
                }
                writer.newLine();
            }
            System.out.println("Lista invertida salva em arquivo: " + "\"saidaAllListaInvertida.txt\"");
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista invertida em arquivo: " + e.getMessage());
        }
    }
}

