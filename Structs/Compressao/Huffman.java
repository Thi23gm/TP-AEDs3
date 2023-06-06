
package Structs.Compressao;

import java.io.*;
import java.util.*;

class HuffmanNode implements Comparable<HuffmanNode> {
    int data; // Valor numérico associado ao nó
    char c; // Caractere associado ao nó (somente folhas)
    HuffmanNode left; // Referência para o nó filho esquerdo
    HuffmanNode right; // Referência para o nó filho direito

    public int compareTo(HuffmanNode node) {
        return data - node.data;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public char getValue() {
        return c;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}

public class Huffman {
    private static final int BYTE_SIZE = 8; // Tamanho em bits de um byte
    private static int version = 1; // Variável de controle da versão do arquivo compactado

    public static void compress() throws IOException {
        String sourceFile = "Banco/DB.bin"; // Arquivo de origem a ser comprimido
        String compressedFile = String.format("Banco/ArquivosCompactadosHuffman/DB.CompacV%03d.huffman", version);
        // Arquivo comprimido resultante

        FileInputStream fis = null;
        BitOutputStream bos = null;

        try {
            fis = new FileInputStream(sourceFile);
            bos = new BitOutputStream(compressedFile);

            int[] frequency = new int[256]; // Frequência de ocorrência de cada byte no arquivo de origem
            int fileSize = 0; // Tamanho do arquivo de origem em bytes
            int readByte;

            // Contagem da frequência de ocorrência de cada byte no arquivo de origem
            while ((readByte = fis.read()) != -1) {
                frequency[readByte]++;
                fileSize++;
            }

            HuffmanNode root = buildHuffmanTree(frequency); // Construção da árvore de Huffman
            Map<Integer, String> huffmanCodes = generateHuffmanCodes(root); // Geração dos códigos de Huffman

            writeHeader(bos, frequency); // Escrita do cabeçalho no arquivo comprimido

            fis.close();
            fis = new FileInputStream(sourceFile);

            // Compressão do arquivo de origem usando os códigos de Huffman
            while ((readByte = fis.read()) != -1) {
                String code = huffmanCodes.get(readByte);
                for (char c : code.toCharArray()) {
                    int bit = Character.getNumericValue(c);
                    bos.writeBit(bit);
                }
            }

            bos.close();
            fis.close();

            System.out.println("ARQUIVIO COMPACTADO COM SUCESSO!");
            System.out.println("TAMANHO ORIGINAL: " + fileSize + " BYTES");
            System.out.println("TAMANHO COMPACTADO: " + new File(compressedFile).length() + " BYTES");

            version++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] readHeader(BitInputStream bis) throws IOException {
        int[] frequency = new int[256]; // Frequência de ocorrência de cada byte no arquivo original

        // Leitura do cabeçalho do arquivo comprimido (frequência de ocorrência de cada
        // byte)
        for (int i = 0; i < 256; i++) {
            int value = bis.readBits(32); // Supondo que a frequência seja lida como um inteiro de 32 bits
            frequency[i] = value;
        }

        return frequency;
    }

    private static HuffmanNode buildHuffmanTree(int[] frequency) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        // Construção da fila de prioridade com os nós iniciais (caracteres com
        // frequência maior que zero)
        for (int i = 0; i < 256; i++) {
            if (frequency[i] > 0) {
                HuffmanNode node = new HuffmanNode();
                node.c = (char) i;
                node.data = frequency[i];
                node.left = null;
                node.right = null;
                queue.add(node);
            }
        }

        // Construção da árvore de Huffman
        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();
            HuffmanNode sum = new HuffmanNode();
            sum.data = x.data + y.data;
            sum.c = '-';
            sum.left = x;
            sum.right = y;
            queue.add(sum);
        }

        return queue.poll(); // Retorna a raiz da árvore de Huffman
    }

    private static Map<Integer, String> generateHuffmanCodes(HuffmanNode root) {
        Map<Integer, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private static void generateCodes(HuffmanNode node, String code, Map<Integer, String> huffmanCodes) {
        if (node == null)
            return;

        if (node.left == null && node.right == null) {
            huffmanCodes.put((int) node.c, code);
        }

        // Geração dos códigos de Huffman percorrendo a árvore
        generateCodes(node.left, code + "0", huffmanCodes);
        generateCodes(node.right, code + "1", huffmanCodes);
    }

    private static void writeHeader(BitOutputStream bos, int[] frequency) throws IOException {
        // Escrita do cabeçalho no arquivo comprimido (frequência de ocorrência de cada
        // byte)
        for (int i = 0; i < 256; i++) {
            bos.writeInt(frequency[i], 32); // Supondo que a frequência seja escrita como um inteiro de 32 bits
        }
    }

    public static void decompress(int version) throws IOException {
        String compressedFile = String.format("Banco/ArquivosCompactadosHuffman/DB.CompacV%03d.huffman", version);
        // Arquivo comprimido a ser descompactado
        String decompressedFile = String.format("Banco/ArquivosDescompactadosHuffman/DB.DecompacV%03d.bin", version);
        // Arquivo descompactado resultante

        FileInputStream fis = null;
        BitInputStream bis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(compressedFile);
            bis = new BitInputStream(fis);
            fos = new FileOutputStream(decompressedFile);

            int[] frequency = readHeader(bis); // Leitura do cabeçalho do arquivo comprimido
            HuffmanNode root = buildHuffmanTree(frequency); // Construção da árvore de Huffman
            int remainingBits = bis.readBits(BYTE_SIZE); // Leitura do número de bits restantes não utilizados no último
                                                         // byte do arquivo comprimido

            int totalBytes = 0; // Total de bytes do arquivo descompactado
            HuffmanNode currentNode = root; // Nó atual durante a descompressão

            // Descompressão do arquivo comprimido usando a árvore de Huffman
            while (true) {
                int bit = bis.readBit();
                if (bit == -1)
                    break;

                currentNode = (bit == 0) ? currentNode.getLeft() : currentNode.getRight();

                if (currentNode.isLeaf()) {
                    fos.write(currentNode.getValue());
                    totalBytes++;
                    currentNode = root;
                }
            }

            int expectedFileSize = (int) Math.ceil(totalBytes * BYTE_SIZE - remainingBits);
            System.out.println("ARQUIVO DESCOMPACTADO COM SUCESSO!");
            System.out.println("TAMANHO ORIGINAL: " + expectedFileSize + " BYTES");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
            if (bis != null)
                bis.close();
            if (fos != null)
                fos.close();
        }
    }
}

class BitOutputStream {
    private FileOutputStream fos;
    private int buffer;
    private int bufferSize;

    public BitOutputStream(String filePath) throws IOException {
        fos = new FileOutputStream(filePath);
        buffer = 0;
        bufferSize = 0;
    }

    public void writeBit(int bit) throws IOException {
        buffer <<= 1; // Desloca o buffer para a esquerda em uma posição
        buffer |= bit; // Define o último bit do buffer com o valor fornecido
        bufferSize++; // Incrementa o tamanho do buffer

        if (bufferSize == 8) { // Se o buffer está cheio (8 bits)
            fos.write(buffer); // Escreve o buffer no arquivo de saída
            buffer = 0; // Reseta o buffer para 0
            bufferSize = 0; // Reseta o tamanho do buffer para 0
        }
    }

    public void writeInt(int value, int numBits) throws IOException {
        for (int i = numBits - 1; i >= 0; i--) {
            int bit = (value >> i) & 1; // Obtém o i-ésimo bit do valor fornecido
            writeBit(bit); // Escreve o bit no arquivo de saída
        }
    }

    public void close() throws IOException {
        if (bufferSize > 0) { // Se ainda houver bits no buffer
            buffer <<= (8 - bufferSize); // Desloca os bits restantes para a esquerda
            fos.write(buffer); // Escreve o buffer no arquivo de saída
        }
        fos.close(); // Fecha o arquivo de saída
    }
}

class BitInputStream {
    private InputStream input;
    private int currentByte;
    private int currentBit;

    public BitInputStream(InputStream in) {
        input = in;
        currentByte = 0;
        currentBit = 8;
    }

    public int readBit() throws IOException {
        if (currentBit >= 8) { // Se todos os bits do byte atual já foram lidos
            currentByte = input.read(); // Lê o próximo byte do arquivo de entrada
            if (currentByte == -1) { // Se chegou ao final do arquivo
                return -1; // Retorna -1 para indicar o fim do arquivo
            }
            currentBit = 0; // Reseta o contador de bits para 0
        }
        int bit = (currentByte >> (7 - currentBit)) & 1; // Obtém o próximo bit do byte atual
        currentBit++; // Incrementa o contador de bits
        return bit; // Retorna o bit lido (0 ou 1)
    }

    public int readBits(int numBits) throws IOException {
        int value = 0;
        for (int i = 0; i < numBits; i++) {
            int bit = readBit(); // Lê o próximo bit
            if (bit == -1) { // Se chegou ao fim do arquivo
                return -1; // Retorna -1 para indicar o fim do arquivo
            }
            value = (value << 1) | bit; // Adiciona o bit ao valor, deslocando-o para a esquerda em uma posição
        }
        return value; // Retorna o valor lido a partir dos bits
    }

    public void close() throws IOException {
        input.close(); // Fecha o arquivo de entrada
    }
}
