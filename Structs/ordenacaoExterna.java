package Structs;

import java.io.*;
import java.util.*;

public class ordenacaoExterna {
    private static int pos = 0;// posição do ponteiro para o aquivo

    private static void swap(filme[] array, int i, int j) {
        filme temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Quick sort basico para ordernar por nome
    private static void sort(filme[] array, int esq, int dir) {
        int i = esq, j = dir;
        filme pivo = array[(dir + esq) / 2];
        while (i <= j) {
            while (array[i].getName().compareTo(pivo.getName()) < 0)
                i++;
            while (array[j].getName().compareTo(pivo.getName()) > 0)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j)
            sort(array, esq, j);
        if (i < dir)
            sort(array, i, dir);
    }

    // transformar o arquivo binario em objeto (Filme)
    private static filme getFilme() throws IOException {
        RandomAccessFile arq = new RandomAccessFile("../TP1/Banco/DB.bin", "rw");
        filme f = null;
        int tamanhoRegistro;
        boolean lapide, resp = false;

        while (resp == false) {
            if (pos < arq.length()) {
                if (pos == 0) {
                    arq.seek(4);
                } else {
                    arq.seek(pos);
                }
                tamanhoRegistro = arq.readInt();// 4 bytes
                lapide = arq.readBoolean();// 1 byte

                if (lapide == false) {
                    f = new filme(lapide, arq.readInt());

                    arq.readInt();
                    f.setType(arq.readUTF());

                    arq.readInt();
                    f.setName(arq.readUTF());

                    arq.readInt();
                    f.setDirector(arq.readUTF());

                    String s = "";
                    int aux = arq.readInt();
                    for (int i = 0; i < aux; i++) {
                        arq.readInt();
                        s = s + arq.readUTF() + ",";
                    }
                    f.setCast(s.split(","));

                    arq.readInt();
                    f.setCountry(arq.readUTF());

                    arq.readInt();
                    f.setDateAdded(arq.readUTF());

                    f.setReleaseYear(arq.readInt());

                    arq.readInt();
                    f.setRating(arq.readUTF());

                    f.setDuration(arq.readInt());

                    String s1 = "";
                    int aux1 = arq.readInt();
                    for (int i = 0; i < aux1; i++) {
                        arq.readInt();
                        s1 = s1 + arq.readUTF() + ",";
                    }
                    f.setGenres(s1.split(","));

                    arq.readInt();
                    f.setDescription(arq.readUTF());

                    pos = (int) arq.getFilePointer();
                    resp = true;
                } else {
                    arq.skipBytes(tamanhoRegistro - 1);// pular o tamanho do registro que é uma lapide - 1 byte que ja
                                                       // foi lido
                    pos = (int) arq.getFilePointer();
                }
            } else {
                arq.close();
                return null;
            }
        }
        arq.close();
        return f;
    }

    public static void balanceadaComum(int M, int N) throws IOException {
        // M -> número máximo de registros que podem ser armazenados na memória
        // principal
        // N -> número de caminhos
        String inputFile = "Banco/DB.bin"; // arquivo de entrada
        String[] tempFiles = new String[N - 1]; // arquivos temporários para armazenar dados classificados
        for (int i = 0; i < N - 1; i++) {
            tempFiles[i] = "temp_" + i + ".txt";
        }
        String outputFile = "saidas/saidaOrdenada.txt"; // arquivo de saída

        BufferedReader input = new BufferedReader(new FileReader(inputFile));
        BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

        filme[] buffer = new filme[M * N];
        int bufferSize = 0;

        while (true) {
            // Ler M * N registros do arquivo de entrada para o buffer
            for (int i = 0; i < M * N; i++) {
                filme record = getFilme();
                if (record == null) {
                    break;
                }
                buffer[i] = record;
                bufferSize++;
            }
            if (bufferSize == 0) {
                break;
            }

            // Classificar o buffer usando o algoritmo quicksort
            sort(buffer, 0, bufferSize - 1);

            // Dividir o buffer em N caminhos e gravar os dados classificados em arquivos
            // temporários
            int blockSize = bufferSize / N;
            for (int i = 0; i < N - 1; i++) {
                String tempFile = tempFiles[i];
                BufferedWriter tempOutput = new BufferedWriter(new FileWriter(tempFile));
                for (int j = 0; j < blockSize; j++) {
                    int index = i * blockSize + j;
                    tempOutput.write("ID: " + (buffer[index].getId() / 2) + "\n" + "NOME: " + buffer[index].getName()
                            + "\n" + "TIPO: " + buffer[index].getType() + "\n" + "DIRETOR: "
                            + buffer[index].getDirector() + "\n" + "ELENCO: " + Arrays.toString(buffer[index].getCast())
                            + "\n" + "PAIS: " + buffer[index].getCountry() + "\n" + "DATA DE LANÇAMENTO: "
                            + buffer[index].getDateAdded() + "\n" + "ANO DE LANÇAMENTO: "
                            + buffer[index].getReleaseYear() + "\n" + "AVALIAÇÃO: " + buffer[index].getRating() + "\n"
                            + "DURAÇÃO: " + buffer[index].getDuration() + "\n" + "GENEROS: "
                            + Arrays.toString(buffer[index].getGenres()) + "\n" + "DESCRIÇÃO: "
                            + buffer[index].getDescription() + "\n\n");
                }
                tempOutput.close();
            }

            // Gravar o restante dos dados classificados no último caminho
            BufferedWriter lastOutput = new BufferedWriter(new FileWriter(tempFiles[N - 2], true));
            for (int i = (N - 1) * blockSize; i < bufferSize; i++) {
                lastOutput.write("ID: " + (buffer[i].getId() / 2) + "\n" + "NOME: " + buffer[i].getName() + "\n"
                        + "TIPO: " + buffer[i].getType() + "\n" + "DIRETOR: " + buffer[i].getDirector() + "\n"
                        + "ELENCO: " + Arrays.toString(buffer[i].getCast()) + "\n" + "PAIS: " + buffer[i].getCountry()
                        + "\n" + "DATA DE LANÇAMENTO: " + buffer[i].getDateAdded() + "\n" + "ANO DE LANÇAMENTO: "
                        + buffer[i].getReleaseYear() + "\n" + "AVALIAÇÃO: " + buffer[i].getRating() + "\n" + "DURAÇÃO: "
                        + buffer[i].getDuration() + "\n" + "GENEROS: " + Arrays.toString(buffer[i].getGenres()) + "\n"
                        + "DESCRIÇÃO: " + buffer[i].getDescription() + "\n\n");
            }
            lastOutput.close();

            // Ler o primeiro registro de cada caminho temporário
            BufferedReader[] tempInputs = new BufferedReader[N - 1];
            for (int i = 0; i < N - 1; i++) {
                tempInputs[i] = new BufferedReader(new FileReader(tempFiles[i]));
            }
            String[] temps = new String[N - 1];
            for (int i = 0; i < N - 1; i++) {
                temps[i] = tempInputs[i].readLine();
            }

            // Mesclar os caminhos temporários usando intercalação balanceada
            while (true) {
                int minIndex = -1;
                String minValue = null;
                for (int i = 0; i < N - 1; i++) {
                    if (temps[i] != null && (minValue == null || temps[i].compareTo(minValue) < 0)) {
                        minIndex = i;
                        minValue = temps[i];
                    }
                }
                if (minIndex == -1) {
                    break;
                }
                output.write(minValue + "\n");
                temps[minIndex] = tempInputs[minIndex].readLine();
                if (temps[minIndex] == null) {
                    tempInputs[minIndex].close();
                    new File(tempFiles[minIndex]).delete();
                    temps[minIndex] = null;
                }
            }

            // Limpar o buffer
            bufferSize = 0;
        }

        input.close();
        output.close();
    }

}