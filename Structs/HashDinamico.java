package Structs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HashDinamico{

    private List<List<filme>> buckets;
    private int bucketSize;
    private int directoryDepth;

    public HashDinamico(int bucketSize) {
        this.bucketSize = bucketSize;
        this.directoryDepth = 1;
        this.buckets = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            this.buckets.add(new ArrayList<>());
        }
    }

    public void set(filme filme) {
        int key = filme.getId();
        int bucketIndex = calculateBucketIndex(key);
        List<filme> bucket = buckets.get(bucketIndex);
        for (int i = 0; i < bucket.size(); i++) {
            filme f = bucket.get(i);
            if (f.getId() == key) {
                bucket.set(i, filme);
                return;
            }
        }
        bucket.add(filme);
        if (bucket.size() > bucketSize) {
            splitBucket(bucketIndex);
        }
    }

    public filme get(int key) {
        int bucketIndex = calculateBucketIndex(key);
        List<filme> bucket = buckets.get(bucketIndex);
        for (filme filme : bucket) {
            if (filme.getId() == key) {
                return filme;
            }
        }
        return null;
    }

    public boolean delete(int key) {
        int bucketIndex = calculateBucketIndex(key);
        List<filme> bucket = buckets.get(bucketIndex);
        for (int i = 0; i < bucket.size(); i++) {
            filme filme = bucket.get(i);
            if (filme.getId() == key) {
                bucket.remove(i);
                return true;
            }
        }
        return false;
    }

    private int calculateBucketIndex(int key) {
        int bucketIndex = key * (int) Math.pow(2, directoryDepth - 1);
        return bucketIndex % buckets.size();
    }

    private void splitBucket(int bucketIndex) {
        List<filme> oldBucket = buckets.get(bucketIndex);
        List<filme> newBucket = new ArrayList<>();
        buckets.add(newBucket);
        for (filme filme : oldBucket) {
            int newBucketIndex = calculateBucketIndex(filme.getId());
            if (newBucketIndex != bucketIndex) {
                newBucket.add(filme);
                oldBucket.remove(filme);
            }
        }
        if (directoryDepth == Math.ceil(Math.log(buckets.size()) / Math.log(2))) {
            resizeHashTable();
        }
    }

    private void resizeHashTable() {
        List<List<filme>> oldBuckets = buckets;
        directoryDepth++;
        bucketSize *= 2;
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(new ArrayList<>());
        }
        for (List<filme> bucket : oldBuckets) {
            for (filme filme : bucket) {
                set(filme);
            }
        }
    }
    
    public void print() {
        try {
            File file = new File("saidas/saidaHash.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < buckets.size(); i++) {
                bw.write(String.format("Bucket %d:\n", i));
                List<filme> bucket = buckets.get(i);
                for (filme filme : bucket) {
                    bw.write(String.format("\t%s\n", filme.getId()));
                }
            }
            bw.close();
            fw.close();
            System.out.println("Arquivo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo!");
            e.printStackTrace();
        }
    }
}
