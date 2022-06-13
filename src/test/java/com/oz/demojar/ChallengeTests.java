package com.oz.demojar;

import com.oz.demojar.challenge.Challenge;
import com.oz.demojar.utils.BinaryTree;
import com.oz.demojar.utils.CommonUtils;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
public class ChallengeTests {

    private static final Logger log = LoggerFactory.getLogger(BinaryTreeTests.class);

    private BinaryTree tree;

    @Test
    @DisplayName("Test triplets: 3 elements must add to 0")
    public void testTriplets() {

        int[] intArray = new int[]{0, 1, 2, 3, 1};

        boolean isTriplet = Challenge.findTriplets(intArray);
        log.info(Arrays.toString(intArray) + " is triplet? " + isTriplet);

        assertFalse(isTriplet,
                Arrays.toString(intArray) + "expected is NOT a triplet");

        String arrStrOfInts = "0, -1, 2, -3, 1, 1900";
        Optional<int[]> intsOpt = CommonUtils.stringsToInts(arrStrOfInts);
        intArray = intsOpt.orElseGet(() -> new int[0]);
        isTriplet = Challenge.findTriplets(intArray);
        log.info(Arrays.toString(intArray) + " is triplet? " + isTriplet);

        assertTrue(isTriplet,
                Arrays.toString(intArray) + " is triplet");

    }

    @Test
    @DisplayName("Test for Anagrams")
    public void testAnagrams() {
        String s1 = "geeksforgeeks";
        String s2 = "geeksgeeksfor";

        boolean isAnagram = Challenge.isAnagram(s1, s2);
        assertTrue(isAnagram,
                s1 + " " + s2 + " are anagrams.");

        s2 = "Weeksgeeksfor";
        isAnagram = Challenge.isAnagram(s1, s2);
        assertFalse(isAnagram,
                s1 + " " + s2 + " are not anagrams.");
    }

    @Test
    @DisplayName("Test for chars and words")
    public void testCountCharsWords() {

        String str = "the number of Number Random that fits is a fits at random random order";
        int words = Challenge.countWords(str);
        assertEquals(words, 14, str + " has " + words + " words.");
        log.info(str, words);

        str = "sssssakkkettreeere";
        int chars = Challenge.countChars(str);
        assertEquals(chars, 6,
                str + " has " + chars + " unique chars.");
        log.info(str, words);

    }

    @Test
    @DisplayName("Test for Min Jumps")
    public void testMinJumps() {

        int[] jumpArr = new int[]{1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9};
        int jumps = Challenge.minJumps(jumpArr);
        assertEquals(3, jumps);

        jumpArr = new int[]{1, 4, 3, 2, 6, 7};
        jumps = Challenge.minJumps(jumpArr);
        assertEquals(2, jumps);

        jumpArr = new int[]{2, 3, 1, 1, 2, 4, 2, 0, 1, 1};
        jumps = Challenge.minJumps(jumpArr);
        assertEquals(5, jumps);

    }

    @Test
    @DisplayName("Java 8 exercise")
    public void generic() {
        int n = 0;

        while(n<21) {
            List<Integer> range = IntStream.range(10, n + 1)
                    .boxed()
                    .collect(Collectors.toList());
            n+= 10;
            System.out.println("rangeList:" + range);
        }


        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5", "6");
        System.out.println("Original list: " + numbers);

        List<Integer> even = numbers.stream()
                .map(Integer::valueOf)
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("processed list, only even numbers: " + even);
    }
}
