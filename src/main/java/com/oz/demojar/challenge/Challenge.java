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
        if(a.length() < 1 || a.length() > 10000 ||
           b.length() < 1 || b.length() > 10000) return false;

        if(a.length() != b.length()) return false;

        System.out.println(a);
        System.out.println(b);
        List<String> alphabet = CommonUtils.getAlphabet("lower");

        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();

        Map<String, Object> alphabetMap1 = new HashMap<>();
        alphabet.forEach(ch -> alphabetMap1.put(ch,0));
        for(char ch : charsA) {
            String k = String.valueOf(ch);
            if(alphabetMap1.containsKey(k)) {
                Integer tmp = (Integer) alphabetMap1.get(k);
                tmp++;
                Object o = tmp;
                alphabetMap1.put(k, o);
            }
        }
        System.out.println(CommonUtils.objMapToJson(alphabetMap1));

        Arrays.sort(charsA);
        Arrays.sort(charsB);

        for(int i=0;i<a.length();i++) {
            if(charsA[i] != charsB[i]) {
                return false;
            }
        }
        return true;
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
