package com.colinward;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Colin Ward on 6/23/2016.
 */
public class Main {
    final static int NUM_CHROMOSOMES = 10;
    final static int TARGET = 42;
    final static int CHROMO_LEN = 5;
    static Random rand;

    public static void Main(String[] args){
        ArrayList<Chromosome> genePool = new ArrayList<Chromosome>(NUM_CHROMOSOMES);
        ArrayList<Chromosome> newGenePool = new ArrayList<Chromosome>(genePool.size());
        rand = new Random();
        for(int i = 0; i < NUM_CHROMOSOMES; i++){
            genePool.add(new Chromosome(TARGET, CHROMO_LEN));
        }
    }

    private Chromosome selectHeirToThrone(ArrayList<Chromosome> tributes){
        // Get the total fitness
        double totalFit = 0.0;
        for (int i = tributes.size() - 1; i >= 0; i--) {
            double score = (tributes.get(i)).score;
            totalFit += score;
        }
        double randCutoff = totalFit * rand.nextDouble();

        // Loop to find the node
        double totalCutoff = 0.0;
        for (int i = tributes.size() - 1; i >= 0; i--) {
            Chromosome node = tributes.get(i);
            totalCutoff += node.score;
            if (totalCutoff >= randCutoff){
                tributes.remove(i);
                return node;
            }
        }
        return tributes.remove(tributes.size() - 1);
    }
}
