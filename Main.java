import Structs.BTree;
import Structs.HashDinamico;
import Structs.arquivo;
import Structs.filme;
import Structs.ordenacaoExterna;
import Structs.Compressao.LZW;

import java.io.*;
import java.util.*;

public class Main {

  // Scanner
  static Scanner sc = new Scanner(System.in);

  // Define o método registrarFilme
  public static filme registrarFilme() throws IOException {
    // Declara as variáveis locais
    String type, name, director, cast, country, date, rating, geners, description;
    int yaer, duration;

    // Solicita que o usuário digite os dados do registro
    System.out.println("DIGITE OS DADOS DO REGISTRO: ");

    // Lê o nome do filme
    System.out.print("NOME: ");
    name = sc.nextLine();
    name = sc.nextLine();

    // Lê o tipo do filme (filme ou série)
    System.out.print("TIPO (FILME/SERIE): ");
    type = sc.nextLine();

    // Lê o nome do diretor
    System.out.print("DIRETOR: ");
    director = sc.nextLine();

    // Lê o elenco (separado por vírgula)
    System.out.print("ELENCO (SEPARE COM ','): ");
    cast = sc.nextLine();

    // Lê o país de origem
    System.out.print("PAIS: ");
    country = sc.nextLine();

    // Lê a data de lançamento
    System.out.print("DATA DE LANÇAMENTO: ");
    date = sc.nextLine();

    // Lê o ano de lançamento
    System.out.print("ANO DE LANÇAMENTO: ");
    yaer = sc.nextInt();

    // Lê a avaliação do filme
    System.out.print("AVALIAÇÃO: ");
    rating = sc.nextLine();
    rating = sc.nextLine();

    // Lê a duração do filme
    System.out.print("DURAÇÃO: ");
    duration = sc.nextInt();

    // Lê os gêneros do filme (separados por vírgula)
    System.out.print("GENEROS: ");
    geners = sc.nextLine();
    geners = sc.nextLine();

    // Lê a descrição do filme
    System.out.print("DESCRIÇÃO: ");
    description = sc.nextLine();

    // Cria um objeto filme com os dados lidos
    filme f = new filme(
        type,
        name,
        director,
        stringToArray(cast),
        country,
        date,
        yaer,
        rating,
        duration,
        stringToArray(geners),
        description);

    // Retorna o objeto filme criado
    return f;
  }

  // Define o método printFilme
  public static void printFilme(filme f) {
    // Imprime na tela os dados do filme passado como parâmetro
    System.out.println(
        "ID: " +
            f.getId() +
            "\n" +
            "NOME: " +
            f.getName() +
            "\n" +
            "TIPO: " +
            f.getType() +
            "\n" +
            "DIRETOR: " +
            f.getDirector() +
            "\n" +
            "ELENCO: " +
            Arrays.toString(f.getCast()) +
            "\n" +
            "PAIS: " +
            f.getCountry() +
            "\n" +
            "DATA DE LANÇAMENTO: " +
            f.getDateAdded() +
            "\n" +
            "ANO DE LANÇAMENTO: " +
            f.getReleaseYear() +
            "\n" +
            "AVALIAÇÂO: " +
            f.getRating() +
            "\n" +
            "DURAÇÂO: " +
            f.getDuration() +
            "\n" +
            "GENEROS: " +
            Arrays.toString(f.getGenres()) +
            "\n" +
            "DESCRIÇÃO: " +
            f.getDescription() +
            "\n");
  }

  private static String[] stringToArray(String s) {
    String arr[] = s.split(",");
    return arr;
  }

  public static void menu(int entrada, BTree arvore) {
    try {
      switch (entrada) {
        case 1:
          filme f = registrarFilme(); // Criando novo objeto
          boolean status = arquivo.salvarRegistro(f); // salvando novo registro
          arvore.insert(f);
          if (status) {
            System.out.println(
                "O REGISTRO FOI INCLUIDO COM SUCESSO COM O ID: " + f.getId() + "!"); // mensagem de sucesso ao incluir
                                                                                     // registro
          } else {
            System.out.println(
                "NÃO FOI POSSIVEL INCLUIR O REGISTRO NO BANCO DE DADOS!"); // mensagem de erro ao incluir registro
          }
          break;
        case 2:
          System.out.print("DIGITE O CAMINHO DO ARQUIVO CSV: "); // Caminho do arquivo CSV
          String path;
          path = sc.next();
          arquivo.createStruct(path, arvore); // fazer a leitura e a transformação para objeto salvanco no banco como
                                              // array de bytes
          System.out.println(
              "FORAM IMPORTADOS " +
                  (arquivo.getCont()) +
                  " REGISTROS COM SUCESSO!");
          break;
        case 3:
          int id = 0;
          boolean resp;
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA REMOVER: ");
          id = sc.nextInt(); // le id que deseja deletar
          resp = arquivo.delete(id); // detela arquivo (Adiciona a lapide como verdadeira)
          if (resp == true) {
            System.out.println("REGISTRO REMOVIDO COM SUCESSO!");
          } else {
            System.out.println("REGISTRO NÃO ENCONTRADO!");
          }
          break;
        case 4:
          int x1 = 0;
          filme aux1 = new filme();
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA ATUALIZAR: ");
          x1 = sc.nextInt();
          aux1 = registrarFilme();
          resp = arquivo.update(aux1, x1);
          if (resp == true) {
            System.out.println(
                "\nREGISTRO ATUALIZADO COM SUCESSO!" +
                    "\n" +
                    "NOVO ID: " +
                    aux1.getId());
          } else {
            System.out.println(
                "NÃO FOI POSSIVEL ATUALIZAR O REGISTRO DESEJADO!");
          }
          break;
        case 5:
          int x = 0;
          filme aux = null;
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA PESQUISAR: ");
          x = sc.nextInt();
          aux = arquivo.select(x, arvore);
          if (aux != null) {
            printFilme(aux);
          } else {
            System.out.println("REGISTRO DE ID " + x + " NÃO ENCONTRADO");
          }
          break;
        case 6:
          arvore.printBTree();
          System.out.println("\nARVORE SALVA NA: \"saidaBTree.txt\"!");
          break;
        case 7:
          System.out.println(
              "DIGITE A QUNTIDADE DE REGISTROS QUE DESEJA ORDENAR POR VEZ: ");
          int blockSize = sc.nextInt();
          System.out.println(
              "DIGITE A QUNTIDADE DE ARQUIVOS QUE SERA CRIADO: ");
          int num = sc.nextInt();
          ordenacaoExterna.balanceadaComum(blockSize, num);
          System.out.println("SAIDA SALVA NO ARQUIVO: \"saidaOrdenada.txt\"!");
          break;
        case 8:
          System.out.println(
              "QUAL TIPO DE PALAVRA CHAVE QUE VOCE DESEJA PESQUISAR:\n1 - TIPO ( Serie ou Filme )\n2 - GENEROS");
          int op = sc.nextInt();
          if (op == 1) {
            System.out.print("QUAL TIPO VOCE DESEJA PESQUISAR: ");
            String tipo = sc.nextLine();
            tipo = sc.nextLine();
            if (tipo == "Serie" ||
                tipo == "serie" ||
                tipo == "Series" ||
                tipo == "series") {
              tipo = "TV Show";
            } else if (tipo == "Filme" ||
                tipo == "filme" ||
                tipo == "Filmes" ||
                tipo == "filmes") {
              tipo = "Movie";
            }
            List<filme> list = arquivo.listType.search(tipo);
            System.out.println("\n" + list.size() + " REGISTROS ENCONTRADOS: ");
            arquivo.printList(list, 1);
          } else if (op == 2) {
            System.out.print("QUAL(IS) GENERO(S) VOCE DESEJA PESQUISAR: ");
            String genero = sc.nextLine();
            genero = sc.nextLine();
            if (genero.contains(",") == true) {
              String array[] = genero.split(",");
              for (int i = 0; i < array.length; i++) {
                List<filme> list = arquivo.listGenres.search(array[i]);
                if (list == null) {
                  System.out.println("ENCONTRADO 0 REGISTROS!");
                } else {
                  System.out.println(
                      "\n" +
                          list.size() +
                          " REGISTROS ENCONTRADOS DO GENERO " +
                          array[i] +
                          ": ");
                  arquivo.printList(list, array[i]);
                  System.out.println();
                }
              }
            } else {
              List<filme> list = arquivo.listGenres.search(genero);
              if (list == null) {
                System.out.println("ENCONTRADO 0 REGISTROS!");
              } else {
                System.out.println(
                    "\n" + list.size() + " REGISTROS ENCONTRADOS: ");
                arquivo.printList(list, genero);
              }
            }
          }

          break;

        case 9:
          System.out.println("DIGITE QUAL METODO VOCÊ QUER COMPACTAR:");
          System.out.println("1 - LZW");
          System.out.println("2 - HUFFMAN");
          int op3 = sc.nextInt();
          switch (op3) {
            case 1:
              long tempoInicial = System.currentTimeMillis();
              LZW.compress();
              System.out
                  .println("TEMPO TOTAL: \t" + (System.currentTimeMillis() - tempoInicial) + "ms");
              break;
            case 2:
              // chamar codigo para o HUFFMAN
              break;
            default:
              throw new InputMismatchException(op3 + " NÃO É VALIDO.");
          }
          break;

        case 10:
          System.out.println("DIGITE A VERSÃO DO BANCO QUE DESEJA DESCOMPACTAR:");
          int versionUser = sc.nextInt();
          long tempoInicial2 = System.currentTimeMillis();
          LZW.decompress(versionUser);
          System.out.println("TEMPO TOTAL: \t" + (System.currentTimeMillis() - tempoInicial2) + "ms");
          break;

        default:
          throw new InputMismatchException(entrada + " NÃO É VALIDO.");
      }
    } catch (Exception e) {
    }
  }

  public static void menu(int entrada, HashDinamico hash) {
    try {
      switch (entrada) {
        case 1:
          filme f = registrarFilme(); // Criando novo objeto
          boolean status = arquivo.salvarRegistro(f); // salvando novo registro
          hash.set(f);
          if (status) {
            System.out.println(
                "O REGISTRO FOI INCLUIDO COM SUCESSO COM O ID: " + f.getId() + "!"); // mensagem de sucesso ao incluir
                                                                                     // registro
          } else {
            System.out.println(
                "NÃO FOI POSSIVEL INCLUIR O REGISTRO NO BANCO DE DADOS!"); // mensagem de erro ao incluir registro
          }
          break;
        case 2:
          System.out.print("DIGITE O CAMINHO DO ARQUIVO CSV: "); // Caminho do arquivo CSV
          String path;
          path = sc.next();
          arquivo.createStruct(path, hash); // fazer a leitura e a transformação para objeto salvanco no banco como
                                            // array de bytes
          System.out.println(
              "FORAM IMPORTADOS " +
                  (arquivo.getCont()) +
                  " REGISTROS COM SUCESSO!");
          break;
        case 3:
          int id = 0;
          boolean resp;
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA REMOVER: ");
          id = sc.nextInt(); // le id que deseja deletar
          resp = arquivo.delete(id); // detela arquivo (Adiciona a lapide como verdadeira)
          hash.delete(id);
          if (resp == true) {
            System.out.println("REGISTRO REMOVIDO COM SUCESSO!");
          } else {
            System.out.println("REGISTRO NÃO ENCONTRADO!");
          }
          break;
        case 4:
          int x1 = 0;
          filme aux1 = new filme();
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA ATUALIZAR: ");
          x1 = sc.nextInt();
          aux1 = registrarFilme();
          resp = arquivo.update(aux1, x1);
          if (resp == true) {
            System.out.println(
                "\nREGISTRO ATUALIZADO COM SUCESSO!" +
                    "\n" +
                    "NOVO ID: " +
                    aux1.getId());
          } else {
            System.out.println(
                "NÃO FOI POSSIVEL ATUALIZAR O REGISTRO DESEJADO!");
          }
          break;
        case 5:
          int x = 0;
          filme aux = null;
          System.out.println("DIGITE O ID DO REGISTRO QUE DESEJA PESQUISAR: ");
          x = sc.nextInt();
          aux = arquivo.select(x, hash);
          if (aux != null) {
            printFilme(aux);
          } else {
            System.out.println("REGISTRO DE ID " + x + " NÃO ENCONTRADO");
          }
          break;
        case 6:
          hash.print();
          System.out.println("\nTABELA HASH SALVA NA: \"saidaHash.txt\"!");
          break;
        case 7:
          System.out.println(
              "DIGITE A QUNTIDADE DE REGISTROS QUE DESEJA ORDENAR POR VEZ: ");
          int blockSize = sc.nextInt();
          System.out.println(
              "DIGITE A QUNTIDADE DE ARQUIVOS QUE SERA CRIADO: ");
          int num = sc.nextInt();
          ordenacaoExterna.balanceadaComum(blockSize, num);
          System.out.println("SAIDA SALVA NO ARQUIVO: \"saidaOrdenada.txt\"!");
          break;
        case 8:
          System.out.println(
              "QUAL TIPO DE PALAVRA CHAVE QUE VOCE DESEJA PESQUISAR:\n1 - TIPO ( Serie ou Filme )\n2 - GENEROS");
          int op = sc.nextInt();
          if (op == 1) {
            System.out.print("QUAL TIPO VOCE DESEJA PESQUISAR: ");
            String tipo = sc.nextLine();
            tipo = sc.nextLine();
            if (tipo == "Serie" ||
                tipo == "serie" ||
                tipo == "Series" ||
                tipo == "series") {
              tipo = "TV Show";
            } else if (tipo == "Filme" ||
                tipo == "filme" ||
                tipo == "Filmes" ||
                tipo == "filmes") {
              tipo = "Movie";
            }
            List<filme> list = arquivo.listType.search(tipo);
            System.out.println("\n" + list.size() + " REGISTROS ENCONTRADOS: ");
            arquivo.printList(list, 1);
          } else if (op == 2) {
            System.out.print("QUAL(IS) GENERO(S) VOCE DESEJA PESQUISAR: ");
            String genero = sc.nextLine();
            genero = sc.nextLine();
            if (genero.contains(",") == true) {
              String array[] = genero.split(",");
              for (int i = 0; i < array.length; i++) {
                List<filme> list = arquivo.listGenres.search(array[i]);
                if (list == null) {
                  System.out.println("ENCONTRADO 0 REGISTROS!");
                } else {
                  System.out.println(
                      "\n" +
                          list.size() +
                          " REGISTROS ENCONTRADOS DO GENERO " +
                          array[i] +
                          ": ");
                  arquivo.printList(list, array[i]);
                  System.out.println();
                }
              }
            } else {
              List<filme> list = arquivo.listGenres.search(genero);
              if (list == null) {
                System.out.println("ENCONTRADO 0 REGISTROS!");
              } else {
                System.out.println(
                    "\n" + list.size() + " REGISTROS ENCONTRADOS: ");
                arquivo.printList(list, genero);
              }
            }
          }

          break;

        case 9:
          System.out.println("DIGITE QUAL METODO VOCÊ QUER COMPACTAR:");
          System.out.println("1 - LZW");
          System.out.println("2 - HUFFMAN");
          int op3 = sc.nextInt();
          switch (op3) {
            case 1:
              long tempoInicial = System.currentTimeMillis();
              LZW.compress();
              System.out
                  .println("TEMPO TOTAL: \t" + (System.currentTimeMillis() - tempoInicial) + "ms");
              break;
            case 2:
              // chamar codigo para o HUFFMAN
              break;
            default:
              throw new InputMismatchException(op3 + " NÃO É VALIDO.");
          }
          break;

        case 10:
          System.out.println("DIGITE A VERSÃO DO BANCO QUE DESEJA DESCOMPACTAR:");
          int versionUser = sc.nextInt();
          long tempoInicial2 = System.currentTimeMillis();
          LZW.decompress(versionUser);
          System.out.println("TEMPO TOTAL: \t" + (System.currentTimeMillis() - tempoInicial2) + "ms");
          break;

        default:
          throw new InputMismatchException(entrada + " NÃO É VALIDO.");
      }
    } catch (Exception e) {
    }
  }

  public static void main(String[] args) throws IOException {
    arquivo.inicializarArquivo();
    boolean running;
    int entrada = 0;
    try {
      System.out.println(
          "=============================================================");
      System.out.println(
          "=                   REGISTROS DA NETFLIX                    =");
      System.out.println(
          "=============================================================");
      Thread.sleep(1000);
      running = true;

      System.out.println(
          "QUE TIPO DE ARQUIVO DE INDICES VOCE DESEJA IMPLEMENTAR: ");
      System.out.println("1 - ARVORE B");
      System.out.println("2 - HASH DINAMICO");
      System.out.println("0 - SAIR");
      int tipoArquivo = sc.nextInt();
      if (tipoArquivo == 1) {
        System.out.println("DIGITE A ORDEM DA ARVORE B: ");
        int ordem = sc.nextInt();
        BTree arvore = new BTree(ordem);
        while (running) {
          try {
            System.out.println(
                "=============================================================");
            System.out.println(
                "=                   REGISTROS DA NETFLIX                    =");
            System.out.println(
                "=============================================================");
            System.out.println("1 -  INCLUIR REGISTRO MANUALMENTE");
            System.out.println("2 -  INCLUIR REGISTROS COM ARQUIVO CSV");
            System.out.println("3 -  REMOVER REGISTRO");
            System.out.println("4 -  ATUALIZAR REGISTRO");
            System.out.println("5 -  PESQUISAR REGISTRO");
            System.out.println("6 -  IMPRIMIR AQUIVO INDEXADO");
            System.out.println("7 -  ORDENAÇÃO EXTERNA");
            System.out.println("8 -  LISTA INVERTIDA");
            System.out.println("9 -  COMPACTAR BANCO");
            System.out.println("10 - DESCOMPACTAR BANCO");
            System.out.println("0 -  SAIR");
            entrada = sc.nextInt();
            if (entrada != 0)
              menu(entrada, arvore);
            else
              running = false;
            System.out.println(
                "\n---------------------------------------------------------------");
          } catch (Exception e) {
            System.err.println("ERRO NA INTERFACE DO USUARIO");
            e.printStackTrace();
          }
        }
      } else if (tipoArquivo == 2) {
        System.out.println("DIGITE O TAMANHO DOS BUCKETS: ");
        int sizeBuckets = sc.nextInt();
        HashDinamico hash = new HashDinamico(sizeBuckets);
        while (running) {
          try {
            System.out.println(
                "=============================================================");
            System.out.println(
                "=                   REGISTROS DA NETFLIX                    =");
            System.out.println(
                "=============================================================");
            System.out.println("1 -  INCLUIR REGISTRO MANUALMENTE");
            System.out.println("2 -  INCLUIR REGISTROS COM ARQUIVO CSV");
            System.out.println("3 -  REMOVER REGISTRO");
            System.out.println("4 -  ATUALIZAR REGISTRO");
            System.out.println("5 -  PESQUISAR REGISTRO");
            System.out.println("6 -  IMPRIMIR AQUIVO INDEXADO");
            System.out.println("7 -  ORDENAÇÃO EXTERNA");
            System.out.println("8 -  LISTA INVERTIDA");
            System.out.println("9 -  COMPACTAR BANCO");
            System.out.println("10 - DESCOMPACTAR BANCO");
            System.out.println("0 -  SAIR");
            entrada = sc.nextInt();
            if (entrada != 0)
              menu(entrada, hash);
            else
              running = false;
            System.out.println(
                "\n---------------------------------------------------------------");
          } catch (Exception e) {
            System.err.println("ERRO NA INTERFACE DO USUARIO");
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      System.err.println("ERRO NA INTERFACE DO USUARIO");
      e.printStackTrace();
    }
  }
}
/*
 * Comedies
 * Action & Adventure
 * Horror Movies
 * Banco/ArquivosDescompactadosLZW/DBDecompressV002.bin
 */
