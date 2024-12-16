/**
 * @author Jimmy Um
 * @author Raymond Zhu
 *
 * This program compresses a text file by encoding its content and saving it into a compressed binary file.
 *
 * Input: A text file (e.g., "input.txt") containing the content to compress.
 * Output: A binary file (e.g., "compressed.bin") containing the compressed data.
 */
import java.io.*;
import java.util.*;

public class Compression implements InitializeFile {

    /**
     * ArrayList to store unique strings (dictionary).
     */
    private static ArrayList<String> dic = null;

    /**
     * Number of words in the input file.
     */
    private static int numWordsInInputFile = 0;

    /**
     * Number of lines in the input file.
     */
    private static int numLinesInInputFile = 0;

    /**
     * Output file and its data stream.
     */
    private static FileOutputStream fos = null;
    private static DataOutputStream dos = null;

    public static void main(String[] args) {
        Compression c = new Compression();

        String inputPath = "input.txt";
        Scanner scInputFile = null;
        Scanner scInputLine = null;
        int currentLines = 0;
        dic = new ArrayList<String>();
        calculateNumberOfWords(inputPath);
        c.createNewFile("compressed.bin");
        try {
            scInputFile = new Scanner(new File(inputPath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scInputFile.hasNextLine()) {
            scInputLine = new Scanner(scInputFile.nextLine());
            while (scInputLine.hasNext()) {

                String s = scInputLine.next();
                int index = getIndexFromDic(s);
                if (index == dic.size()) {
                    dic.add(s);
                    Dictionary.addDic(s, index);
                }
                appendIndexToCompressedFile(index);
            }
            currentLines++;
            if (currentLines < numLinesInInputFile) {
                int index = getIndexFromDic("\n");
                if (index == dic.size()) {
                    dic.add("\n");
                }
                appendIndexToCompressedFile(index);
            }
        }
        printDictionary();
        System.out.print(dic.size());
        scInputLine.close();
        scInputFile.close();
        appendDicToCompressedFile();
        closeOutputFile();
    }

    public Compression() {}

    /**
     * Prints all strings stored in the dictionary.
     *
     * Time Complexity:
     * Big O: O(dic.size()) - O(n)
     * Big Omega: Ω(dic.size()) - Ω(n)
     */
    private static void printDictionary() {
        System.out.println("---- printing words from Dictionary ----");
        for (int i = 0; i < dic.size(); i++) {
            System.out.print(dic.get(i) + " ");
        }
        System.out.println("\n---- end of Dictionary ----");
    }

    /**
     * Returns the index of a given string in the dictionary.
     * If not found, returns the size of the dictionary.
     *
     * @param s The string to search for in the dictionary.
     * @return The index of the string in the dictionary, or the size of the dictionary if not found.
     *
     * Time Complexity:
     * Big O: O(dic.size()) - O(n)
     * Big Omega: Ω(1)
     */
    private static int getIndexFromDic(String s) {
        int i;

        for (i = 0; i < dic.size(); i++) {
            if (s.equals(dic.get(i))) {
                System.out.println("match found for: " + s);
                break;
            }
        }
        return i;
    }

    /**
     * Calculates the number of words and lines in the input file.
     *
     * @param fileName The name of the input file.
     */
    private static void calculateNumberOfWords(String fileName) {
        Scanner scInputFile = null;
        Scanner scInputLine = null;

        try {
            scInputFile = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scInputFile.hasNextLine()) {
            scInputLine = new Scanner(scInputFile.nextLine());
            while (scInputLine.hasNext()) {
                String s = scInputLine.next();
                numWordsInInputFile++;
            }
            numWordsInInputFile++;
            numLinesInInputFile++;
        }
        numWordsInInputFile--;
        scInputLine.close();
        scInputFile.close();
    }

    /**
     * Initializes the output file and its data stream.
     *
     * @param fileName The name of the output file to create.
     */
    @Override
    public void createNewFile(String fileName) {
        File compressedFile = new File(fileName);
        if (compressedFile.delete()) {
            System.out.println(fileName + " exists, deleting now.");
        } else {
            System.out.println(fileName + " does not exist, creating a new file");

        }
        try {
            fos = new FileOutputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dos = new DataOutputStream(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dos.writeInt(numWordsInInputFile); // First 4 bytes indicate the number of words in the input file.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends the index of a word to the compressed file.
     *
     * @param index The index of the word to append.
     */
    private static void appendIndexToCompressedFile(int index) {
        try {
            dos.writeShort(index); // Each index entry occupies 2 bytes.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends the dictionary to the compressed file.
     */
    private static void appendDicToCompressedFile() {
        int i;
        String s;
        for (i = 0; i < dic.size(); i++) {
            s = dic.get(i);
            try {
                dos.writeByte(s.length());
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                dos.writeBytes(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the output file and its associated data stream.
     */
    private static void closeOutputFile() {
        try {
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
