package com.colinward;

import java.util.Random;

/**
 * Created by Colin Ward on 6/23/2016.
 */
public class Chromosome {  // <= 1001; >= 1010
    final char[] OUTPUT_VAL = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/','^'};
    StringBuffer chromo;
    int chromoLen;
    public StringBuffer decodedChromo;
    public double score;
    public double crossoverRate;
    public double mutationRate;
    public double total;
    public double target;
    public Random rand;

    public Chromosome(double tar, int len, double crossRate, double mutRate){
        target = tar;
        chromoLen = len;
        crossoverRate = crossRate;
        mutationRate = mutRate;
        chromo = new StringBuffer(chromoLen * 4);
        decodedChromo = new StringBuffer(chromoLen * 4);
        rand = new Random();
        for(int i = 0; i < chromoLen; i++){
            String binString = Integer.toBinaryString(rand.nextInt(OUTPUT_VAL.length));
            double fillLen = 4 - binString.length();

            for (int j = 0; j < fillLen; j++)
                chromo.append('0');
            chromo.append(binString);
        }
        score();
    }

    public void score() {
        total = findValue();
        if (total == target) score = 0;
        score = 1.0 / (target - total);
    }

    public String decodeChromo() {
        decodedChromo.setLength(0);

        for (int i = 0; i < chromo.length(); i += 4) {
            int val = Integer.parseInt(chromo.substring(i,i+4), 2);
            if (val < OUTPUT_VAL.length) decodedChromo.append(OUTPUT_VAL[val]);
        }
        return decodedChromo.toString();
    }

    public void crossOver(Chromosome crossChrom) {

        if (rand.nextDouble() > crossoverRate)
            return;

        int crossPos = rand.nextInt(chromo.length());
        for (int i = crossPos; i < chromo.length(); i++) {
            char temp = chromo.charAt(i);
            chromo.setCharAt(i, crossChrom.chromo.charAt(i));
            crossChrom.chromo.setCharAt(i, temp);
        }
    }

    public void mutate() {
        for (int i = 0; i < chromo.length(); i++) {
            if (rand.nextDouble() <= mutationRate)
                chromo.setCharAt(i, (chromo.charAt(i) == '0' ? '1' : '0'));
        }
    }

    public  boolean isValid() {
        String decoded = decodeChromo();

        boolean b = true;
        for (int i = 0; i < decoded.length(); i++) {
            char ch = decoded.charAt(i);

            if (b == !Character.isDigit(ch))
                return false;

            if (i > 0 && ch == '0' && decoded.charAt(i - 1) == '/')
                return false;
            b = !b;
        }
        if (!Character.isDigit(decoded.charAt(decoded.length() - 1)))
            return false;
        return true;
    }

    public final double findValue() {
        String decoded = decodeChromo();
        double total = 0;

        int iter = 0;
        while (iter<decoded.length()) {
            char ch = decoded.charAt(iter);
            if (Character.isDigit(ch)) {
                total = ch - '0';
                iter++;
                break;
            } else {
                iter++;
            }
        }
        if (iter == decoded.length())
            return 0;

        boolean num = false;
        char operator = ' ';
        while (iter < decoded.length()) {
            char ch = decoded.charAt(iter);
            if (num && !Character.isDigit(ch)) {
                iter++;
                continue;
            }
            if (!num && Character.isDigit(ch)) {
                iter++;
                continue;
            }
            if (num) {
                switch (operator) {
                    case '+' : {
                        total += (ch-'0');
                        break;
                    }
                    case '-' : {
                        total -= (ch-'0');
                        break;
                    }
                    case '*' : {
                        total *= (ch-'0');
                        break;
                    }
                    case '/' : {
                        if (ch!='0')
                            total /= (ch-'0');
                        break;
                    }
                    case '^' : {
                        if (ch!='0')
                            total = Math.pow(total,(double)(ch-'0'));
                        break;
                    }
                }
            } else {
                operator = ch;
            }
            iter++;
            num =! num;
        }

        return total;
    }
}
