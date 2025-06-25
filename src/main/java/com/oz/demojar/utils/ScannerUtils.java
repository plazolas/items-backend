package com.oz.demojar.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerUtils {

    public static ArrayList getResults(File file) throws FileNotFoundException {
        Scanner sc;
        ArrayList<Integer> results = new ArrayList<>();
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            return results;
        }
        int t=sc.nextInt();
        if(t>100) return results;
        for(int i=0; i<t; i++) {
            int c = sc.nextInt(), x = sc.nextInt(), y = sc.nextInt();
            if(c<1 || c>100 || x<0 || x>c || y<1 || y>100) {
                results.add(0);
                continue;
            }
            int n = c - x;
            results.add(n*y);
        }
        return results;
    }
}
