package com.oz.demojar.challenge;

import com.oz.demojar.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge {

    private static final Logger log = LoggerFactory.getLogger(Challenge.class);

    public static boolean findTriplets(int[] arr) {
        if (arr.length < 1 || arr.length > Math.pow(10, 4)) return false;

        for (int i = 0; i < arr.length - 2; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (arr[i] + arr[j] + arr[k] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAnagram(String a, String b) {
        if (a.length() < 1 || a.length() > 10000 ||
                b.length() < 1 || b.length() > 10000) return false;

        if (a.length() != b.length()) return false;

        List<String> alphabet = CommonUtils.getAlphabet("lower");

        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();

        Map<String, Object> alphabetMap1 = new HashMap<>();
        alphabet.forEach(ch -> alphabetMap1.put(ch, 0));
        for (char ch : charsA) {
            String k = String.valueOf(ch);
            if (alphabetMap1.containsKey(k)) {
                Integer tmp = (Integer) alphabetMap1.get(k);
                tmp++;
                Object o = tmp;
                alphabetMap1.put(k, o);
            }
        }
        log.info(CommonUtils.objMapToJson(alphabetMap1));

        Arrays.sort(charsA);
        Arrays.sort(charsB);

        for (int i = 0; i < a.length(); i++) {
            if (charsA[i] != charsB[i]) {
                return false;
            }
        }
        return true;
    }

    public static int countWords(String str) {

        String str1 = str.toLowerCase(Locale.ROOT);
        String[] words = str1.split(" ");

        HashMap<String, Integer> intMap = new HashMap<>();
        for (int i = words.length - 1; i >= 0; i--) {
            if (intMap.containsKey(words[i])) {
                int count = intMap.get(words[i]);
                intMap.put(words[i], count + 1);
            } else {
                intMap.put(words[i], 1);
            }
        }
        log.info(CommonUtils.intMapToJson(intMap));
        return words.length;

    }

    public static int countChars(String str) {
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

        log.info(CommonUtils.intMapToJson(intMap));
        return intMap.size();
    }

    public static int minJumps(int[] arr) {
        if (arr.length == 0) return 0;

        StringBuilder in = new StringBuilder("[ ");
        for (int i = 0; i < arr.length; i++) {
            if (i < (arr.length - 1)) {
                in.append(arr[i]).append(", ");
            } else {
                in.append(arr[i]);
            }
        }
        in.append(" ]");
        System.out.println(in);

        int idx = (arr[0] == 0) ? arr.length : 0;
        int step = arr[0];
        int jumps = 0;
        while (idx < arr.length) {
            System.out.println("idx value: " + idx);
            System.out.println("element: " + step);
            if (idx + step > arr.length || step == 0) {
                break;
            } else {
                System.out.println("jump: " + jumps);
                idx = idx + step;
                if (idx > arr.length) {
                    return jumps;
                } else {
                    step = arr[idx];
                }
                jumps++;
            }
        }
        return jumps;
    }

    public static void collectionTest() {
        {

            // create a list of integers
            List<Integer> number = Arrays.asList(2,3,4,5);
            System.out.println("created list: "+number);

            // demonstration of map method returns squares list
            List<Integer> square = number.stream().map(x -> x*x).
                    collect(Collectors.toList());
            System.out.println("squares: "+square);

            // create a list of String
            List<String> names =
                    Arrays.asList("Reflection","Collection","Stream");
            System.out.println("names: "+names);

            // demonstration of filter method
            List<String> filtered = names.stream().filter(s->s.startsWith("S")).
                    collect(Collectors.toList());
            System.out.println("filtered: "+filtered);

            // demonstration of sorted method
            List<String> sorted =
                    names.stream().sorted().collect(Collectors.toList());
            System.out.println("sorted: "+sorted);

            // create a list of integers
            List<Integer> numbers = Arrays.asList(2,3,4,5,2);
            System.out.println("new created list: "+numbers);

            // collect method returns a set
            Set<Integer> squareSet =
                    numbers.stream().map(x->x*x).collect(Collectors.toSet());
            System.out.println("squareSet: "+squareSet);

            // demonstration of forEach method

            number.stream().map(x->x*x).forEach(System.out::println);

            // demonstration of reduce method
            int even =
                    number.stream().filter(x->x%2==0).reduce(2,(partialResult, element) -> partialResult + element);
            System.out.println("reduced: "+even);
        }
    }


}
