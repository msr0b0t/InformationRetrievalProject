/*
 * Created by Mary
 */

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;

public class Main {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        //positive inverted indexes
        TreeMap<String, Integer> inverted_pos = new TreeMap<>();

        //for the positive files first
        File pos = new File("/home/mary/Desktop/data/train/pos");
        File[] files = pos.listFiles();

        assert files != null;
        for (File file : files) {
            inverted_pos = makeInverted(inverted_pos, file);
        }

        //keep top k elements
        SortedSet<Map.Entry<String, Integer>> sorted_pos = entriesSortedByValues(inverted_pos);
        HashSet<Map.Entry<String, Integer>> hs_pos = new HashSet<>();
        //change number of top k elements
        for(int j = 0; j < 300; j++){
            hs_pos.add(sorted_pos.last());
            sorted_pos.remove(sorted_pos.last());
        }

        //System.out.println(hs_pos);


        //negative inverted indexes
        TreeMap<String, Integer> inverted_neg = new TreeMap<>();

        //for the negative files .......................................................................................
        File neg = new File("/home/mary/Desktop/data/train/neg");
        File[] files2 = neg.listFiles();

        assert files2 != null;
        for (File file : files2) {
            inverted_neg = makeInverted(inverted_neg, file);
        }

        SortedSet<Map.Entry<String,Integer>> sorted_neg = entriesSortedByValues(inverted_neg);
        HashSet<Map.Entry<String, Integer>> hs_neg = new HashSet<>();
        for(int j = 0; j < 300; j++){
            hs_neg.add(sorted_neg.last());
            sorted_neg.remove(sorted_neg.last());
        }

        //System.out.println(".........................................................................................");
        //System.out.println(hs_neg);


        //open files of test and compare strings to inverted indexes
        File test = new File("/home/mary/Desktop/data/test");
        File[] testFiles = test.listFiles();

        File tempPred = new File("/home/mary/Desktop/data/temp.txt");

        int positive;
        int negative;
        assert testFiles != null;
        for (File file : testFiles) {
            positive = 0;
            negative = 0;
            String content = FileUtils.readFileToString(file, "UTF-8");
            String[] contentToArray = content.split(" ");
            for (String word : contentToArray) {
                if (inverted_pos.containsKey(word)) {
                    positive++;
                }
                if (inverted_neg.containsKey(word)){
                    negative++;
                }
            }
            if (positive >= negative){
                FileUtils.writeStringToFile(tempPred, file.getName().replace(".txt", "") + " " + "1\n", "UTF-8", true);
            }else {
                FileUtils.writeStringToFile(tempPred, file.getName().replace(".txt", "") + " " + "0\n", "UTF-8", true);

            }
        }

        Sort sf = new Sort();
        sf.fileSorter("/home/mary/Desktop/data/temp.txt");

        long endTime   = System.currentTimeMillis();
        long totalTime = (endTime - startTime) /1000;
        System.out.println(totalTime);

    }







    private static TreeMap<String, Integer> makeInverted(TreeMap<String, Integer> inverted, File file) throws IOException {
        //content's value is the string of the file read
        String content = FileUtils.readFileToString(file, "UTF-8");
        //System.out.println(content);
        String[] contentToArray = content.split(" ");
        for (String word : contentToArray) {
            if (inverted.containsKey(word)) {
                inverted.put(word, inverted.get(word) + 1);
            } else {
                inverted.put(word, 1);
            }
        }
        return inverted;
    }





    private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>((e1, e2) -> {
            int res = e1.getValue().compareTo(e2.getValue());
            if (e1.getKey().equals(e2.getKey())) {
                return res; // Code will now handle equality properly
            } else {
                return res != 0 ? res : 1; // While still adding all entries
            }
        }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
