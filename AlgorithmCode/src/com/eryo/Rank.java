package com.eryo;

import java.util.Arrays;

/**
 *  author:18301092-eryo
 *  time:2020年12月2日
 */
public class Rank {

    public static String[] reRank(String[] input){

        if(input == null || input.length == 0) {
            return new String[0];
        }

        int r = 0, g = 0, b = 0;
        for(String s:input){
            if(s.equals("r")){
                r += 1;
            }else if(s.equals("g")){
                g += 1;
            }else if(s.equals("b")){
                b += 1;
            }
        }

        String[] output = new String[input.length];
        int i = 0;
        for(i = 0; i < r; i++){
            output[i] = "r";
        }
        for(; i < g; i++){
            output[i] = "g";
        }
        for (; i < b; i++){
            output[i] = "b";
        }

        return output;
    }

    public static String[] reRankTwoPointers(String[] input) {
        if(input == null || input.length == 0) {
            return new String[0];
        }
        int i = 0;
        int left = 0;
        int right = input.length - 1;
        while(i <= right) {
            if(input[i].equals("r")) {
                String t = input[i];
                input[i] = input[left];
                input[left] = t;
                i++;
                left++;
            }
            else if(input[i].equals("g")) {
                i++;
            }
            else if(input[i].equals("b")) {
                String t1 = input[i];
                input[i] = input[right];
                input[right] = t1;
                right--;
            }
        }
        return input;
    }

    public static void main(String[] args) {
        String[] input = new String[]{"r", "r", "b", "g", "b", "r", "g"};
        Arrays.asList(reRankTwoPointers(input)).forEach(e -> System.out.println(e));
        Arrays.asList(reRankTwoPointers(input)).forEach(e -> System.out.println(e));
    }
}
