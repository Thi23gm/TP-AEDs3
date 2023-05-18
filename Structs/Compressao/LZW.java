package Structs.Compressao;

import java.util.*;
import java.io.*;

public class LZW {
    // Definindo a versão inicial do algoritmo de compressão LZW
    private static int version = 1;

    // Método para compressão de um arquivo utilizando o algoritmo LZW
    public static void compress() {
        // Definindo o nome e localização do arquivo compactado que será gerado
        String compressedFileName = String.format("Banco/ArquivosCompactadosLZW/DBCompacV%03d.lzw", version);

        try (RandomAccessFile raf = new RandomAccessFile(new File("Banco/DB.bin"), "r")) {
            // Inicializando o dicionário com os 256 possíveis valores de um byte (0 a 255)
            Map<String, Integer> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put(String.valueOf((char) i), i);
            }

            // Definindo a string vazia como a string atual
            String current = "";

            // Lendo o primeiro byte do arquivo original
            int byteRead = raf.read();

            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(compressedFileName)))) {
                // Enquanto ainda houver bytes no arquivo original
                while (byteRead != -1) {
                    // Enquanto ainda houver bytes no arquivo original
                    String next = current + (char) byteRead;

                    // Enquanto ainda houver bytes no arquivo original
                    if (dictionary.containsKey(next)) {
                        current = next;
                    } else { // Enquanto ainda houver bytes no arquivo original
                        dos.writeInt(dictionary.get(current));
                        dictionary.put(next, dictionary.size());
                        current = String.valueOf((char) byteRead);
                    }
                    // Lendo o próximo byte do arquivo original
                    byteRead = raf.read();
                }

                // Se ainda houver uma string atual, escreve o seu código no arquivo compactado
                if (!current.equals("")) {
                    dos.writeInt(dictionary.get(current));
                }

                // fechar RandomAcessFile do banco original
                raf.close();
            } catch (IOException e) {
                System.err.println("NÃO FOI POSSIVEL ESCREVER NOARQUIVO COMPACTADO!");
            }
            // Incrementando a versão do algoritmo LZW
            version++;
        } catch (IOException e) {
            System.err.println("NÃO FOI POSSIVEL ABRIR O BANCO!");
        }

        // Imprimindo informações sobre o arquivo compactado gerado
        System.out.println("ARQUIVO COMPACTADO COM SUCESSO: " + compressedFileName);
        File originalFile = new File("Banco/DB.bin");
        File compressedFile = new File(compressedFileName);
        int lengthOriginalFile = (int) originalFile.length();
        int lengthCompressFile = (int) compressedFile.length();
        System.out.println("TAMANHO ORIGINAL   : \t" + lengthOriginalFile +
                " bytes");
        System.out.println("TAMANHO COMPACTADO : \t" + lengthCompressFile +
                " bytes");
        System.out.println(
                "TAXA DE COMPACTAÇÃO: \t" + (float) lengthOriginalFile / lengthCompressFile +
                        " vezes menor");

    }

    // Método para descompressão de um arquivo utilizando o algoritmo LZW
    public static void decompress(int versionUser) {
        // salvando o nome do arquivo compactado
        String compressedFileName = String.format("Banco/ArquivosCompactadosLZW/DBCompacV%03d.lzw", versionUser);
        // salvando o nome do arquivo que ira descompactar o arquivo compactado
        String decompressedFileName = String.format("Banco/ArquivosDescompactadosLZW/DBDecompressV%03d.bin",
                versionUser);
        try (DataInputStream dis = new DataInputStream(new FileInputStream(new File(compressedFileName)))) {
            // Inicializando o dicionário com os 256 possíveis valores de um byte (0 a 255)
            Map<Integer, String> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put(i, String.valueOf((char) i));
            }
            // lendo o codigo atual
            int currentCode = dis.readInt();
            // pegando a string do codigo no dicionario
            String current = dictionary.get(currentCode);
            // criando arquivo para descompactar
            try (FileWriter fw = new FileWriter(new File(decompressedFileName))) {
                int nextCode;
                String next;
                // enquanto o arquivo comprimido for disponivel
                while (dis.available() > 0) {
                    nextCode = dis.readInt();
                    if (dictionary.containsKey(nextCode)) {
                        next = dictionary.get(nextCode);
                    } else if (nextCode == dictionary.size()) {
                        next = current + current.charAt(0);
                    } else {
                        throw new IllegalStateException("CODIGO LZW INVALIDO: " + nextCode);
                    }
                    // escreve no arquivo a string recuperada
                    fw.write(current);
                    dictionary.put(dictionary.size(), current + next.charAt(0));
                    current = next;
                }
                fw.write(current);
            } catch (IOException e) {
                System.err.println("ERRO AO COMPACTAR O ARQUIVO.");
            }
        } catch (IOException e) {
            System.err.println("ERRO AO LER O ARQUIVO COMPACTADO.");
        }

        // Imprimindo informações sobre o arquivo compactado gerado
        System.out.println("ARQUIVO COMPACTADO COM SUCESSO: " + decompressedFileName);
        File originalFile = new File("Banco/DB.bin");
        File decompressedFile = new File(decompressedFileName);
        int lengthOriginalFile = (int) originalFile.length();
        int lengthDecompressFile = (int) decompressedFile.length();
        System.out.println("TAMANHO ORIGINAL: \t" + lengthOriginalFile +
                " bytes");
        System.out.println("TAMANHO DESCOMPACTADO: \t" + lengthDecompressFile +
                " bytes");
    }
}