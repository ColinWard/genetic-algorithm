package com.colinward;

import java.util.Random;

/**
 * Created by Colin Ward on 6/23/2016.
 */
public class Chromosome {
    final char[] OUTPUT_VAL = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/'};
    StringBuffer chromo;
    int chromoLen;
    public StringBuffer decodedChromo;
    public double score;
    public int total;
    public Random rand;


    public Chromosome(StringBuffer ch, int len){
        chromo = ch;
        chromoLen = len;
    }

    public Chromosome(int target, int len){
        chromoLen = len;
        chromo = new StringBuffer(chromoLen * 4);
        decodedChromo = new StringBuffer(chromoLen * 4);
        rand = new Random();
        // Create the full buffer
        for(int i = 0; i < chromoLen; i++){
            // What's the current length
            int pos = chromo.length();

            // Generate a random binary integer
            String binString = Integer.toBinaryString(rand.nextInt(OUTPUT_VAL.length));
            int fillLen = 4 - binString.length();

            // Fill to 4
            for (int x=0;x<fillLen;x++) chromo.append('0');

            // Append the chromo
            chromo.append(binString);

        }

        // Score the new cromo
        score(target);
    }

    public void score(int target) {
        //TODO: get value from decoded chromosome
        total = findValue();
        if (total == target) score = 0;
        score = (double)1 / (target - total);
    }
}
