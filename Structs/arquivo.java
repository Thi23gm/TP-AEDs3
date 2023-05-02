package Structs;

import java.io.*;
import java.util.*;

public class arquivo {

    public static listaInvertida listType = new listaInvertida();
    public static listaInvertida2 listGenres = new listaInvertida2();

    /*
     * Método responsável por inicializar o arquivo do banco de dados.
     * 
     * @throws IOException - exceção de entrada/saída que pode ser lançada durante a
     * manipulação do arquivo.
     */
    public static void inicializarArquivo() throws IOException {
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// Abre o arquivo para escrita.
        arq.seek(0);// Move o ponteiro para o início do arquivo.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// Cria um objeto ByteArrayOutputStream que permite
                                                                 // gravar dados na memória.
        DataOutputStream dos = new DataOutputStream(baos);// Cria um objeto DataOutputStream que permite escrever
                                                          // primitivos em um fluxo de saída.
        dos.writeInt(0);// Escreve um inteiro indicando que nenhum registro foi inserido ainda.
        arq.write(baos.toByteArray());// Grava os dados no arquivo.
        arq.close();
    }

    private static filme stringCSVToFilme(String linha, filme f) throws IOException {
        String arr[] = linha.split(",");
        int i = 2, cont = 0;

        // Set Type
        if (arr[i] != null && arr[i] != "") {
            f.setType(arr[i].trim().replaceAll("\"", ""));
            i++;
        }
        // Set null se n houver Type
        else {
            f.setType(null);
            i++;
        }

        // Set Name
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    String subString = Arrays.toString(subarray);
                    i = cont + 1;
                    f.setName(subString);
                } else {
                    f.setName(arr[i].trim().replaceAll("\"", ""));
                    i++;
                }
            } else {
                f.setName(arr[i].trim().replaceAll("\"", ""));
                i++;
            }
        }
        // Set null se n houver Name
        else {
            f.setName(null);
            i++;
        }

        // Set Director
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    String subString = Arrays.toString(subarray);
                    i = cont + 1;
                    f.setDirector(subString);
                } else {
                    f.setDirector(arr[i].trim().replaceAll("\"", ""));
                    i++;
                }
            } else {
                f.setDirector(arr[i].trim().replaceAll("\"", ""));
                i++;
            }
        }
        // Set null se n houver Director
        else {
            f.setDirector(null);
            i++;
        }

        // Set Cast
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    i = cont + 1;
                    f.setCast(subarray);
                } else {
                    String subarray[] = { arr[i].trim().replaceAll("\"", "") };
                    f.setCast(subarray);
                    i++;
                }
            } else {
                String subarray[] = { arr[i].trim().replaceAll("\"", "") };
                f.setCast(subarray);
                i++;
            }
        }
        // Set null se n houver Cast
        else {
            f.setCast(null);
            i++;
        }

        // Set Country
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    String subString = Arrays.toString(subarray);
                    i = cont + 1;
                    f.setCountry(subString);
                } else {
                    f.setCountry(arr[i].trim().replaceAll("\"", ""));
                    i++;
                }
            } else {
                f.setCountry(arr[i].trim().replaceAll("\"", ""));
                i++;
            }
        }
        // Set null se n houver Country
        else {
            f.setCountry(null);
            i++;
        }

        // Set Data
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    String subString = Arrays.toString(subarray);
                    i = cont + 1;
                    f.setDateAdded(subString);
                } else {
                    f.setDateAdded(arr[i].trim().replaceAll("\"", ""));
                    i++;
                }
            } else {
                f.setDateAdded(arr[i].trim().replaceAll("\"", ""));
                i++;
            }
        }
        // Set null se n houver Data
        else {
            f.setDateAdded(null);
            i++;
        }

        // Set Yaer
        if (arr[i] != null && arr[i] != "") {
            f.setReleaseYear(Integer.parseInt(arr[i].trim().replaceAll("\"", "")));
            i++;
        }
        // Set null se n houver Yaer
        else {
            f.setReleaseYear(-1);
            i++;
        }

        // Set Rating
        if (arr[i] != null && arr[i] != "") {
            f.setRating(arr[i].trim().replaceAll("\"", ""));
            i++;
        }
        // Set null se n houver Rating
        else {
            f.setRating(null);
            i++;
        }

        // Set Duration
        if (arr[i] != null && arr[i] != "") {
            f.setDuration(Integer.parseInt(arr[i]));
            i++;
        }
        // Set null se n houver Duration
        else {
            f.setDuration(-1);
            i++;
        }

        // Set Genres
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray2[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray2, 0, subarray2.length);
                    i = cont + 1;
                    f.setGenres(subarray2);
                } else {
                    String subarray2[] = { arr[i].trim().replaceAll("\"", "") };
                    f.setGenres(subarray2);
                    i++;
                }
            } else {
                String subarray2[] = { arr[i].trim().replaceAll("\"", "") };
                f.setGenres(subarray2);
                i++;
            }
        }
        // Set null se n houver Cast
        else {
            f.setGenres(null);
            i++;
        }

        // Set Description
        if (arr[i] != null && arr[i] != "") {
            if (arr[i].contains("\"")) {
                cont = i + 1;
                if (cont <= arr.length - 1 && arr[cont] != null) {
                    while (arr[cont].contains("\"") == false) {
                        cont++;
                    }
                    String subarray[] = new String[cont - i + 1];
                    System.arraycopy(arr, i, subarray, 0, subarray.length);
                    String subString = Arrays.toString(subarray);
                    i = cont;
                    f.setDescription(subString);
                } else {
                    f.setDescription(arr[i].trim().replaceAll("\"", ""));
                }
            } else {
                f.setDescription(arr[i].trim().replaceAll("\"", ""));
            }
        }
        // Set null se n houver Duration
        else {
            f.setDescription(null);
        }
        return f;
    }

    public static byte[] toByteArray(filme f) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeBoolean(f.getLapide());// Escrevendo Lapide
        dos.writeInt(f.getId());// Escrevendo id

        if (f.getType() != null) {
            dos.writeInt(f.getType().length());// Escrevendo o tamanho do Type
            dos.writeUTF(f.getType());// Escrevendo Type
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        if (f.getName() != null) {
            dos.writeInt(f.getName().length());// Escrevendo o tamanho do Name
            dos.writeUTF(f.getName());// Escrevendo Name
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        if (f.getDirector() != null) {
            dos.writeInt(f.getDirector().length());// Escrevendo o tamanho do Diretor
            dos.writeUTF(f.getDirector());// Escrevendo Diretor
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        String[] cast = f.getCast();
        if (cast != null) {
            dos.writeInt(f.getCast().length);// Escrevendo a quantidade do Cast
            for (int i = 0; i < cast.length; i++) {
                if (cast[i] != null) {
                    dos.writeInt(cast[i].length());// Escrevendo o tamanho do Cast
                    dos.writeUTF(cast[i]);// Escrevendo o Cast
                } else {
                    dos.writeInt(0);
                    dos.writeUTF("");
                }
            }
        } else {
            dos.writeInt(0);
        }

        if (f.getCountry() != null) {
            dos.writeInt(f.getCountry().length());// Escrevendo o tamanho do Country
            dos.writeUTF(f.getCountry());// Escrevendo Country
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        if (f.getDateAdded() != null) {
            dos.writeInt(f.getDateAdded().length());// Escrevendo o tamanho da Data
            dos.writeUTF(f.getDateAdded());// Escrevendo a Data
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        dos.writeInt(f.getReleaseYear());// Escrevendo o tamanho do Yaer

        if (f.getRating() != null) {
            dos.writeInt(f.getRating().length());// Escrevendo o tamanho do Rating
            dos.writeUTF(f.getRating());// Escrevendo o Rating
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        dos.writeInt(f.getDuration());// Escrevendo a Duração

        String[] genres = f.getGenres();
        if (genres != null) {
            dos.writeInt(f.getGenres().length);// Escrevendo a quantidade do Genres
            for (int i = 0; i < genres.length; i++) {
                if (genres[i] != null) {
                    dos.writeInt(genres[i].length());// Escrevendo o tamanho do Genres
                    dos.writeUTF(genres[i]);// Escrevendo o Genres
                } else {
                    dos.writeInt(0);
                    dos.writeUTF("");
                }
            }
        } else {
            dos.writeInt(0);
        }

        // Escrevendo a Descrição
        if (f.getDescription() != null) {
            dos.writeInt(f.getDescription().length());// Escrevendo o tamanho da Descrição
            dos.writeUTF(f.getDescription());// Escrevendo a Descrição
        } else {
            dos.writeInt(0);
            dos.writeUTF("");
        }

        dos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        dos.close();
        return bytes;
    }

    public static Boolean salvarRegistro(filme f) throws IOException {
        boolean resp = true;
        byte[] ba;
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// Abre o arquivo para escrita.
        arq.seek(arq.length());// coloca o ponteiro na ultima posição do aquivo
        ba = toByteArray(f); // transforma o filme em um array de bytes
        arq.writeInt(ba.length); // Tamano do registro em bytes
        arq.write(ba); // escreve os bytes no banco

        arq.seek(0);// colocar o ponteiro no inicio do arquivo
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(f.getId());// pegar o ultimo id registrado
        arq.write(baos.toByteArray());// escrever no cabeçalho o ultimo id registrado
        arq.close();
        listType.insert(f);
        listGenres.insert(f);
        return resp; // retorna status
    }

    public static Boolean salvarRegistro(filme f, BTree arvore) throws IOException {
        arvore.insert(f);
        boolean resp = true;
        byte[] ba;
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// Abre o arquivo para escrita.
        arq.seek(arq.length());// coloca o ponteiro na ultima posição do aquivo
        ba = toByteArray(f); // transforma o filme em um array de bytes
        arq.writeInt(ba.length); // Tamano do registro em bytes
        arq.write(ba); // escreve os bytes no banco

        arq.seek(0);// colocar o ponteiro no inicio do arquivo
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(f.getId());// pegar o ultimo id registrado
        arq.write(baos.toByteArray());// escrever no cabeçalho o ultimo id registrado
        arq.close();
        listType.insert(f);
        listGenres.insert(f);
        return resp; // retorna status
    }

    public static void importCSV(String path) throws IOException {
        String linha = "";
        FileReader isr = new FileReader("Banco/bancoTeste.csv");// Abre o arquivo para escrita e leitura.
        BufferedReader br = new BufferedReader(isr);
        while ((linha = br.readLine()) != null) {
            filme f = new filme();
            f = stringCSVToFilme(linha, f);// lê linha por linha transformando o CVS em objeto
            salvarRegistro(f);// pega o objeto, transforma em bytes e salva no Banco
        }
        br.close();
    }

    public static int getCont() throws IOException {
        FileInputStream arquivo = new FileInputStream("Banco/DB.bin");// Abre o arquivo para leitura.
        DataInputStream leitor = new DataInputStream(arquivo);

        int valor = leitor.readInt();// lê 4 bytes de um inteiro (Cabeçalho) da quantidade de registros q contem no
                                     // banco
        leitor.close();
        arquivo.close();
        return (valor) + 1;
    }

    public static filme select(int x) throws IOException {
        int tamanhoRegistro, id;
        boolean lapide;
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// Abre o arquivo para leitura.

        arq.seek(4);// pular os 4 bytes do cabeçalho do registro

        filme f = null;

        while (arq.getFilePointer() < arq.length()) {
            tamanhoRegistro = arq.readInt();// 4 bytes
            lapide = arq.readBoolean();// 1 byte
            id = arq.readInt();// 4 bytes

            if (lapide == false) {
                if (x == id) {
                    f = new filme(lapide, id);// Cria um novo objeto Filme para pegar o registro no Banco

                    arq.readInt();// tamanho do Type
                    f.setType(arq.readUTF());// salvando o Type

                    arq.readInt();// tamanho do Name
                    f.setName(arq.readUTF());// salvando o Nome

                    arq.readInt();// tamanho do Director
                    f.setDirector(arq.readUTF()); // Sanvando o diretor

                    String s = "";
                    int aux = arq.readInt();// quantidade de pessoas do elenco
                    for (int i = 0; i < aux; i++) {
                        arq.readInt();// tamanho do nome de cada pessoa do elenco
                        s = s + arq.readUTF() + ",";
                    }
                    f.setCast(s.split(","));// salvando cada pessoa do elenco

                    arq.readInt();// tamanho do Country
                    f.setCountry(arq.readUTF()); // salvando o Country

                    arq.readInt();// tamanho da Data
                    f.setDateAdded(arq.readUTF());// salvando a data

                    f.setReleaseYear(arq.readInt());// salvando o ano de lançamento

                    arq.readInt();// tamanho do rating
                    f.setRating(arq.readUTF());// sanlvando o rating

                    f.setDuration(arq.readInt());// savlando a duração

                    String s1 = "";
                    int aux1 = arq.readInt();// quntidade de generos que tem o registro
                    for (int i = 0; i < aux1; i++) {
                        arq.readInt();// tamanho de cada genero
                        s1 = s1 + arq.readUTF() + ",";
                    }
                    f.setGenres(s1.split(","));// salvando cada genero

                    arq.readInt();// tamanho da descrição
                    f.setDescription(arq.readUTF());// salvando a descrição
                    break;
                } else {
                    arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja
                                                       // foi lido
                }
            } else if (id == x) {
                arq.close();
                return null;
            } else {
                arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja foi
                                                   // lido
            }
        }
        arq.close();

        return f;
    }

    public static filme select(int x, BTree arvore) throws IOException {
        filme f = null;
        f = arvore.search(x);
        return f;
    }

    public static filme select(int x, HashDinamico hash) throws IOException {
        filme f = null;
        f = hash.get(x);
        return f;
    }

    public static boolean update(filme f, int x) throws IOException {
        boolean resp = false, aux;
        aux = delete(x);// deletar arquivo anterior
        if (aux == true) {
            resp = salvarRegistro(f);// salvar novo arquivo
        }
        return resp;// retorna status
    }

    public static boolean delete(int x) throws IOException {
        int tamanhoRegistro, id;
        boolean lapide;
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// abrir arquivo para leitura.

        arq.seek(4);// pular os 4 bytes do cabeçalho do registro

        boolean resp = false;

        while (arq.getFilePointer() < arq.length()) {
            tamanhoRegistro = arq.readInt();// 4 bytes
            lapide = arq.readBoolean();// 1 byte
            id = arq.readInt();// 4 bytes

            if (lapide == false) {
                if (x == id) {
                    resp = true;// criando boolean como verdadeiro
                    arq.seek(arq.getFilePointer() - 5);// colocando ponteiro na posição da lapide
                    arq.writeBoolean(resp);// escrevendo na lapide como verdadeiro
                    break;
                } else {
                    arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja
                                                       // foi lido
                }
            } else {
                arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja foi
                                                   // lido
            }
        }
        arq.close();
        filme f = select(x);
        listType.remove(f);
        listGenres.remove(f);

        return resp;
    }

    public static void listarRegistros() throws IOException {
        int tamanhoRegistro, id;
        boolean lapide;
        RandomAccessFile arq = new RandomAccessFile("Banco/DB.bin", "rw");// arbrir arquivo para leitura.

        arq.seek(4);// pular os 4 bytes do cabeçalho do registro

        filme f = null;

        while (arq.getFilePointer() < arq.length()) {
            tamanhoRegistro = arq.readInt();// 4 bytes
            lapide = arq.readBoolean();// 1 byte
            id = arq.readInt();// 4 bytes

            if (lapide == false) {
                f = new filme(lapide, id);

                arq.readInt();// tamanho do Type
                f.setType(arq.readUTF());// salvando o Type

                arq.readInt();// tamanho do Name
                f.setName(arq.readUTF());// salvando o Nome

                arq.readInt();// tamanho do Director
                f.setDirector(arq.readUTF()); // Sanvando o diretor

                String s = "";
                int aux = arq.readInt();// quantidade de pessoas do elenco
                for (int i = 0; i < aux; i++) {
                    arq.readInt();// tamanho do nome de cada pessoa do elenco
                    s = s + arq.readUTF() + ",";
                }
                f.setCast(s.split(","));// salvando cada pessoa do elenco

                arq.readInt();// tamanho do Country
                f.setCountry(arq.readUTF()); // salvando o Country

                arq.readInt();// tamanho da Data
                f.setDateAdded(arq.readUTF());// salvando a data

                f.setReleaseYear(arq.readInt());// salvando o ano de lançamento

                arq.readInt();// tamanho do rating
                f.setRating(arq.readUTF());// sanlvando o rating

                f.setDuration(arq.readInt());// savlando a duração

                String s1 = "";
                int aux1 = arq.readInt();// quntidade de generos que tem o registro
                for (int i = 0; i < aux1; i++) {
                    arq.readInt();// tamanho de cada genero
                    s1 = s1 + arq.readUTF() + ",";
                }
                f.setGenres(s1.split(","));// salvando cada genero

                arq.readInt();// tamanho da descrição
                f.setDescription(arq.readUTF());// salvando a descrição

                BufferedWriter bw = new BufferedWriter(new FileWriter("saidas/saida.txt", true));
                bw.append((f.getId()) + "\n" + "NOME: " + f.getName() + "\n" + "TIPO: " + f.getType() + "\n"
                        + "DIRETOR: " + f.getDirector() + "\n" + "ELENCO: " + Arrays.toString(f.getCast()) + "\n"
                        + "PAIS: " + f.getCountry() + "\n" + "DATA DE LANÇAMENTO: " + f.getDateAdded() + "\n"
                        + "ANO DE LANÇAMENTO: " + f.getReleaseYear() + "\n" + "AVALIAÇÃO: " + f.getRating() + "\n"
                        + "DURAÇÃO: " + f.getDuration() + "\n" + "GENEROS: " + Arrays.toString(f.getGenres()) + "\n"
                        + "DESCRIÇÃO: " + f.getDescription() + "\n\n");
                bw.close();// escrever na saida os dados dos filmes
            } else {
                arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja foi
                                                   // lido
            }
        }
        arq.close();
    }

    public static void createStruct(String path, BTree arvore) throws IOException {
        String linha = "";
        FileReader isr = new FileReader("Banco/NetFlix.csv");// Abre o arquivo para escrita e leitura.
        BufferedReader br = new BufferedReader(isr);
        while ((linha = br.readLine()) != null) {
            filme f = new filme();
            f = stringCSVToFilme(linha, f);// lê linha por linha transformando o CVS em objeto
            salvarRegistro(f);// pega o objeto, transforma em bytes e salva no Banco
            arvore.insert(f);
        }
        br.close();
    }

    public static void createStruct(String path, HashDinamico hash) throws IOException {
        String linha = "";
        FileReader isr = new FileReader("Banco/NetFlix.csv");// Abre o arquivo para escrita e leitura.
        BufferedReader br = new BufferedReader(isr);
        while ((linha = br.readLine()) != null) {
            filme f = new filme();
            f = stringCSVToFilme(linha, f);// lê linha por linha transformando o CVS em objeto
            salvarRegistro(f);// pega o objeto, transforma em bytes e salva no Banco
            hash.set(f);
        }
        br.close();
    }

    public static void printList(List<filme> list, int num) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("saidas/saidaListaInvertida1.txt"));
        if (num == 1) {
            writer.write(list.get(0).getType() + ":\n");
        }
        writer.newLine();
        for (filme filme : list) {
            writer.write(filme.getName());
            writer.newLine();
        }
        writer.newLine();
        writer.close();
    }

    public static void printList(List<filme> list, String genero) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("saidas/saidaListaInvertida2.txt", true));
        writer.append(genero + ": ");
        writer.newLine();
        for (filme filme : list) {
            writer.append(filme.getName());
            writer.newLine();
        }
        writer.newLine();
        writer.close();
    }

}