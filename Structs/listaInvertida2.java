package Structs;

import java.util.*;

public class listaInvertida2 {
    private Map<String, List<filme>> index;

    // Construtor
    public listaInvertida2() {
        index = new HashMap<>();
    }

    // Método para adicionar um filme à lista invertida
    public void insert(filme filme) {
        String[] generos = filme.getGenres();

        // Percorre os gêneros do filme e os adiciona no índice
        for (String genero : generos) {
            // Verifica se o gênero já existe no índice, se não, cria uma nova lista vazia
            if (!index.containsKey(genero)) {
                index.put(genero, new ArrayList<>());
            }

            // Adiciona o filme à lista associada ao gênero
            index.get(genero).add(filme);
        }
    }

    // Método para remover um filme da lista invertida
    public void remove(filme filme) {
        String[] generos = filme.getGenres();

        // Percorre os gêneros do filme e os remove do índice
        for (String genero : generos) {
            // Verifica se o gênero existe no índice
            if (index.containsKey(genero)) {
                // Remove o filme da lista associada ao gênero
                index.get(genero).remove(filme);

                // Verifica se a lista associada ao gênero ficou vazia após a remoção do filme
                // Se sim, remove o gênero do índice
                if (index.get(genero).isEmpty()) {
                    index.remove(genero);
                }
            }
        }
    }

    // Método para realizar uma consulta na lista invertida
    public List<filme> search(String genero) {
        // Verifica se o gênero existe no índice
        if (index.containsKey(genero)) {
            // Retorna a lista de filmes associada ao gênero
            return index.get(genero);
        } else {
            // Se o gênero não existe, retorna null
            return null;
        }
    }
}
