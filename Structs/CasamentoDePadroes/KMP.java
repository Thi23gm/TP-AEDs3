package Structs.CasamentoDePadroes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import Structs.arquivo;
import Structs.filme;

public class KMP {

    private static RandomAccessFile arq;

    public static filme getFilme() throws IOException {
        if (arq == null) {
            arq = new RandomAccessFile("Banco/DB.bin", "rw");// arbrir arquivo para leitura.
        }

        int tamanhoRegistro, id;
        boolean lapide;

        if (arq.getFilePointer() == 0) {
            arq.seek(4);// pular os 4 bytes do cabeçalho do registro
        }

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
                return f;
            } else {
                arq.skipBytes(tamanhoRegistro - 5);// pular o tamanho do registro que é uma lapide - 5 bytes que ja foi
                                                   // lido
            }
        }
        return null;
    }

    private static String listToString(String[] arr) {
        String resp = "";
        for (int i = 0; i < arr.length; i++) {
            resp += arr[i].trim();
        }
        return resp;
    }

    private static boolean kmp(String texto, String padrao) {
        int[] prefixo = calcularPrefixo(padrao);
        int j = 0;
        for (int i = 0; i < texto.length(); i++) {
            while (j > 0 && texto.charAt(i) != padrao.charAt(j)) {
                j = prefixo[j - 1];
            }
            if (texto.charAt(i) == padrao.charAt(j)) {
                j++;
            }
            if (j == padrao.length()) {
                return true;
            }
        }
        return false;
    }

    private static int[] calcularPrefixo(String padrao) {
        int[] prefixo = new int[padrao.length()];
        int j = 0;
        for (int i = 1; i < padrao.length(); i++) {
            while (j > 0 && padrao.charAt(i) != padrao.charAt(j)) {
                j = prefixo[j - 1];
            }
            if (padrao.charAt(i) == padrao.charAt(j)) {
                j++;
            }
            prefixo[i] = j;
        }
        return prefixo;
    }

    public static List<filme> buscarKPM(String key) throws IOException{
        List<filme> resultados = new ArrayList<>();

        for (int i = 0; i < arquivo.getCont(); i++) {

            filme filmeAtual = getFilme();
            boolean encontrado = false;

            String nome = filmeAtual.getName();
            String tipo = filmeAtual.getType();
            String pais = filmeAtual.getCountry();
            String descricao = filmeAtual.getDescription();
            String generos = listToString(filmeAtual.getGenres());

            if(kmp(nome, key) == true){
                encontrado = true;
            }else if(kmp(tipo, key) == true){
                encontrado = true;
            }else if(kmp(pais, key) == true){
                encontrado = true;
            }else if(kmp(descricao, key) == true){
                encontrado = true;
            }else if(kmp(generos, key) == true){
                encontrado = true;
            }

            if(encontrado){
                resultados.add(filmeAtual);
            }
        }

        return resultados;
    }
}
