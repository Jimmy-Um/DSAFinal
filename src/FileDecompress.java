/**
 * @author Raymond Zhu
 * @author Jimmy Um
 *
 * This program decompresses a binary file by reconstructing the original text.
 *
 * Input: A compressed binary file (e.g., "compressed.bin").
 * Output: A decompressed text file (e.g., "decompressed.txt").
 */
import java.io.*;
import java.util.*;

public class FileDecompress {

    /**
     * Total number of index entries in the input compressed file.
     */
    private static int numTotalIndex = 0;

    /**
     * Dictionary to hold unique strings (ArrayList of Strings).
     */
    private static ArrayList<String> dic = null;

    /**
     * List to hold index entries from the compressed file (ArrayList of Shorts).
     */
    private static ArrayList<Short> entries = null;

    /**
     * Output file and its data stream.
     */
    private static FileOutputStream fos = null;
    private static DataOutputStream dos = null;

    /**
     * Input file and its data stream.
     */
    private static FileInputStream fis = null;
    private static DataInputStream dis = null;

    /**
     * Pointer to track the current position in the input file (in bytes).
     */
    private static int inputFilePointer = 0;

    /**
     * Size of the input file (in bytes).
     */
    private static int inputFileSize = 0;

    public static void main(String[] args) {
        String inputPath = "compressed.bin";
        dic = new ArrayList<String>();
        entries = new ArrayList<Short>();
        initializeOutputFile("decompressed.txt");
        initializeInputFile(inputPath);
        numTotalIndex = readFromDisInt();
        System.out.println("Number of words to construct: " + numTotalIndex);
        for (int i = 0; i < numTotalIndex; i++) {
            short e = readFromDisShort();
            entries.add(e);
        }
        buildDictionary();
        printDictionary(); // Debugging output.
        convertEntriesToWords();

        closeInputFile();
        closeOutputFile();
    }

    /**
     * Converts the list of index entries to words using the dictionary and writes them to the output file.
     */
    private static void convertEntriesToWords() {
        int index;
        for (int i = 0; i < numTotalIndex; i++) {
            index = entries.get(i);
            try {
                dos.writeBytes(dic.get(index));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ((i < numTotalIndex - 1) && !(dic.get(index).equals("\n")) &&
                    !(dic.get(entries.get(i + 1)).equals("\n"))) {
                try {
                    dos.writeBytes(" ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reads a short value from the input data stream.
     *
     * @return The short value read from the input file.
     */
    private static short readFromDisShort() {
        short n = 0;
        try {
            n = dis.readShort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputFilePointer += 2;
        return n;
    }

    /**
     * Reads an integer value from the input data stream.
     *
     * @return The integer value read from the input file.
     */
    private static int readFromDisInt() {
        int n = 0;
        try {
            n = dis.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputFilePointer += 4;
        return n;
    }

    /**
     * Reconstructs the dictionary from the compressed file.
     */
    private static void buildDictionary() {
        int len;
        byte[] bytes = new byte[256];
        while (inputFilePointer < inputFileSize) {
            try {
                len = dis.readUnsignedByte();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            inputFilePointer += 1;
            if (len > 255) {
                System.exit(0);
            }
            try {
                dis.read(bytes, 0, len);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            inputFilePointer += len;
            String s = new String(bytes, 0, len);
            dic.add(s);
        }
    }

    /**
     * Prints all strings stored in the dictionary.
     */
    private static void printDictionary() {
        System.out.println("---- printing words from Dictionary ----");
        for (String word : dic) {
            System.out.print(word + " ");
        }
        System.out.println("\n---- end of Dictionary ----");
    }

    /**
     * Initializes the input file and its data stream.
     *
     * @param fileName The name of the input file.
     */
    private static void initializeInputFile(String fileName) {
        try {
            fis = new FileInputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dis = new DataInputStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inputFileSize = (int) fis.getChannel().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the output file and its data stream.
     *
     * @param fileName The name of the output file to create.
     */
    private static void initializeOutputFile(String fileName) {
        File decompressedFile = new File(fileName);
        if (decompressedFile.delete()) {
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
    }

    /**
     * Closes the output file and its data stream.
     */
    private static void closeOutputFile() {
        try {
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the input file and its data stream.
     */
    private static void closeInputFile() {
        try {
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
