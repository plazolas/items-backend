package com.oz.demojar.utils;

import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class CommonUtils {

    public static String readFile(String filename) {
        try {
            return new String(readAllBytes(get("src/test/resources/MusicBrainz/" + filename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        //    -? :       this part identifies if the given number is negative, the dash “–” searches for dash literally
        //               and the question mark “?” marks its presence as an optional one
        //    \d+ :      this searches for one or more digits
        //    (\.\d+)? : this part of regex is to identify float numbers. Here we're searching for one or more digits followed by a period.
        //               The question mark, in the end, signifies that this complete group is optional.
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(strNum).matches();
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?\\d+");
        return pattern.matcher(strNum).matches();
    }

    public static Optional<int[]> stringsToInts(String str) {
        List<String> list = Arrays.stream(str.split(",")).collect(Collectors.toList());
        int[] arrInts = new int[list.size()];

        int idx = 0;
        for(String s : list){
            s = s.trim();
            if (CommonUtils.isInteger(s)) {
                int i = Integer.parseInt(s);
                arrInts[idx++] = i;
            } else {
                arrInts = new int[0];
                break;
            }
        }
        // return (list.size() == idx) ? Optional.of(arrInts) : Optional.of(new int[0]);
        return Optional.of(arrInts);

//      Old Java
//        String[] strArr = str.split(",");
//        int[] arrInts = new int[strArr.length];
//        int i = 0;
//        for (String s : strArr) {
//            s = s.trim();
//            if (!CommonUtils.isInteger(s)) {
//                return Optional.of(new int[0]);
//            }
//            arrInt[i++] = Integer.parseInt(s);
//        }
//        return Optional.of(arrInts);
    }

    public static List<String> getAlphabet(String typeCase) {
        List<String> alphabet = new ArrayList<>();

        if (typeCase.contains("u") || typeCase.contains("U")) {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                alphabet.add(String.valueOf(ch));
            }
        } else {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                alphabet.add(String.valueOf(ch));
            }
        }
        return alphabet;
    }

    public static Integer[] append(Integer[] arr, int element)
    {
        List<Integer> list = new ArrayList<>(Arrays.asList(arr));
        list.add(element);

        return list.toArray(new Integer[0]);
    }

    public static String objMapToJson (Map<String, Object> inMap) {
        JSONObject json = new JSONObject();
        json.putAll( inMap );
        return json.toJSONString();
    }

    public static String intMapToJson (Map<String, Integer> inMap) {
        JSONObject json = new JSONObject();
        json.putAll( inMap );
        return json.toJSONString();
    }


}