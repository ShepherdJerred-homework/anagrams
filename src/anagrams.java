// anagrams
// Jerred Shepherd
// O(M + N)

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Anagrams {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("anagrams.in");
        File outputFile = new File("anagrams.out");

        LinkedList<StringPair> inputPairs = getStringPairsFromFile(inputFile);
        LinkedList<Boolean> anagramResults = checkIfEachPairIsAnagram(inputPairs);
        printResultsToFile(anagramResults, outputFile);
    }

    private static LinkedList<StringPair> getStringPairsFromFile(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        LinkedList<StringPair> pairs = new LinkedList<>();

        while (scanner.hasNext()) {
            String l = scanner.nextLine().toLowerCase();
            String r = scanner.nextLine().toLowerCase();

            if (l.equals("<end>") && r.equals("<end>")) {
                break;
            }

            StringPair pair = new StringPair(l, r);
            pairs.add(pair);
        }

        return pairs;
    }

    private static LinkedList<Boolean> checkIfEachPairIsAnagram(LinkedList<StringPair> pairs) {
        LinkedList<Boolean> anagramResults = new LinkedList<>();

        for (StringPair pair : pairs) {
            HashMap<Character, Integer> characterFreqMap = new HashMap<>();

            char[] lChars = pair.l.toCharArray();
            char[] rChars = pair.r.toCharArray();

            for (char c : lChars) {
                int asciiValue = (int) c;
                if (asciiValue < 65 || asciiValue > 122 || (asciiValue > 90 && asciiValue < 97)) {
                    continue;
                }
                if (characterFreqMap.containsKey(c)) {
                    Integer n = characterFreqMap.get(c);
                    n += 1;
                    characterFreqMap.put(c, n);
                } else {
                    characterFreqMap.put(c, 1);
                }
            }

            for (char c : rChars) {
                int asciiValue = (int) c;
                if (asciiValue < 65 || asciiValue > 122 || (asciiValue > 90 && asciiValue < 97)) {
                    continue;
                }
                if (characterFreqMap.containsKey(c)) {
                    Integer n = characterFreqMap.get(c);
                    n -= 1;
                    characterFreqMap.put(c, n);
                } else {
                    characterFreqMap.put(c, -1);
                }
            }

            boolean result = true;
            for (Integer in : characterFreqMap.values()) {
                if (in != 0) {
                    result = false;
                }
            }

            anagramResults.add(result);
        }

        return anagramResults;
    }

    private static void printResultsToFile(LinkedList<Boolean> results, File outputfile) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(outputfile);
        for (Boolean result : results) {
            printWriter.println(booleanToYesOrNo(result));
            System.out.println(booleanToYesOrNo(result));
        }
        printWriter.close();
    }

    private static String booleanToYesOrNo(boolean b) {
        return b ? "YES" : "NO";
    }

    private static class StringPair {
        String l;
        String r;
        StringPair(String l, String r) {
            this.l = l;
            this.r = r;
        }
    }
}
