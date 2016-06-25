package com.colinward;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Colin Ward on 6/23/2016.
 */
public class Main {
    final static int NUM_CHROMOSOMES = 10;
    final static double TARGET = 1.4;
    final static int CHROMO_LEN = 9;
    final static double CROSSOVER_RATE = 0.7;
    final static double MUTATION_RATE = .001;
    final static boolean DEBUG_CHROMOSOMES = false;
    final static boolean DEBUG_STATEMENTS_VALUES = true;
    static Random rand;
    static int generation;
    static ArrayList<Chromosome> genePool;
    static ArrayList<Chromosome> newGenePool;

    public static void main(String[] args){
        genePool = new ArrayList<Chromosome>(NUM_CHROMOSOMES);
        newGenePool = new ArrayList<Chromosome>(genePool.size());
        rand = new Random();
        for(int i = 0; i < NUM_CHROMOSOMES; i++){
            genePool.add(new Chromosome(TARGET, CHROMO_LEN, CROSSOVER_RATE, MUTATION_RATE));
        }
        while(true) {
            newGenePool.clear();
            generation++;
            if(DEBUG_CHROMOSOMES){
                System.out.print("##########\nGeneration: " + generation + "\n");
                for(int i = 0; i < genePool.size() - 1; i++) {
                    if(genePool.get(i).isValid())
                        System.out.println("Score "+i+": " + genePool.get(i).score + " V:"+ genePool.get(i).findValue());
                }
            }
            if(DEBUG_STATEMENTS_VALUES){
                System.out.print("##########\nGeneration: " + generation + "\n");
                for(int i = 0; i < genePool.size() - 1; i++) {
                    if(genePool.get(i).isValid())
                        System.out.println("Statement: " + genePool.get(i).decodeChromo() + " V:"+ genePool.get(i).findValue());
                }
            }
            for(int i = genePool.size() - 1; i >= 0; i -= 2) {
                Chromosome kid1 = selectHeirToThrone(genePool);
                Chromosome kid2 = selectHeirToThrone(genePool);
                kid1.crossOver(kid2);
                kid1.mutate();
                kid2.mutate();

                kid1.score();
                kid2.score();

                if (kid1.total == TARGET && kid1.isValid()){
                    System.out.println("There have been " + generation + " generations and the solution is " + kid1.decodeChromo() + " with value " + kid1.findValue());
                    return;
                }
                if (kid2.total == TARGET && kid2.isValid()) {
                    System.out.println("There have been " + generation + " generations and the solution is " + kid2.decodeChromo() + " with value " + kid2.findValue());
                    return;
                }
                newGenePool.add(kid1);
                newGenePool.add(kid2);
                if(DEBUG_CHROMOSOMES)
                    System.out.println("Using Chromosomes with scores:" + kid1.score + " AND " + kid2.score);

            }
            genePool.addAll(newGenePool);
        }
    }

    private static Chromosome selectHeirToThrone(ArrayList<Chromosome> tributes){
        double totalFit = 0.0;
        for (int i = tributes.size() - 1; i >= 0; i--) {
            double score = (tributes.get(i)).score;
            totalFit += score;
        }
        double randCutoff = totalFit * rand.nextDouble();
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

    public static void superCoolDisplay(){
        //TODO: Display some graphs and stats cause people like that
    }
}
