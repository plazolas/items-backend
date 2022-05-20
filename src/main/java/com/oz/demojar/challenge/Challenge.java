package com.oz.demojar.challenge;

import com.oz.demojar.utils.CommonUtils;

import java.util.*;

public class Challenge {

    public static boolean findTriplets(int[] arr)
    {
        if(arr.length < 1 || arr.length > Math.pow(10, 4)) return false;

        for(int i=0;i<arr.length-2;i++) {
            for(int j=i+1;j<arr.length-1;j++) {
                for(int k=j+1;k<arr.length;k++) {
                    if(arr[i] + arr[j] + arr[k] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAnagram(String a,String b)
    {
        System.out.println(a);
        System.out.println(b);
        List<String> alphabet = CommonUtils.getAlphabet("lower");

        Map<String, Object> alphabetMap1 = new HashMap<>();
        Map<String, Object> alphabetMap2 = new HashMap<>();
        alphabet.forEach(ch -> alphabetMap1.put(ch,0));
        alphabet.forEach(ch -> alphabetMap2.put(ch,0));

        char[] chAs = a.toCharArray();
        char[] chBs = b.toCharArray();

        for(char ch : chAs) {
            String k = String.valueOf(ch);
            if(alphabetMap1.containsKey(k)) {
                Integer tmp = (Integer) alphabetMap1.get(k);
                tmp++;
                Object o = tmp;
                alphabetMap1.put(k, o);
            }
        }

        for(char ch : chBs) {
            String k = String.valueOf(ch);
            if(alphabetMap2.containsKey(k)) {
                Integer tmp = (Integer) alphabetMap2.get(k);
                tmp++;
                Object o = tmp;
                alphabetMap2.put(k, o);
            }
        }

        System.out.println(CommonUtils.objMapToJson(alphabetMap1));
        System.out.println(CommonUtils.objMapToJson(alphabetMap2));

        boolean anagram = false;
        for(char ch : chAs) {
            String k = String.valueOf(ch);
            if(alphabetMap1.get(k) == alphabetMap2.get(k)) {
                anagram = true;
            } else {
                anagram = false;
                break;
            }
        }
        return anagram;
    }

    public static void countWords (String str) {

        String str1 = str.toLowerCase(Locale.ROOT);
        String[] words = str1.split(" ");

        HashMap<String,Integer> intMap = new HashMap<>();
        for(int i = words.length - 1; i >= 0; i--) {
            if(intMap.containsKey(words[i])) {
                int count = intMap.get(words[i]);
                intMap.put(words[i], count + 1);
            } else {
                intMap.put(words[i], 1);
            }
        }
        System.out.println(CommonUtils.intMapToJson(intMap));

    }

    public static void countChars(String str) {
        System.out.println(str);
        HashMap<String, Integer> intMap = new HashMap<>();
        try {
            char[] chars = str.toCharArray();

            for (int i = 0; i < str.length(); i++) {
                int count = 0;
                for (int j = 0; j < str.length() - 1; j++) {
                    if (chars[i] == chars[j]) {
                        intMap.put(String.valueOf(chars[i]), ++count);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(CommonUtils.intMapToJson(intMap));
    }

}
