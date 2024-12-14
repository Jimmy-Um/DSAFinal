/*
* input:
* The input to this program is a text file that the use would like to compress. In this example, the file is
"input.txt"
*
*
* output:
* The compressed file of "input.txt". In this example, the compressed file is "compressed.bin"
*/

import java.io.*;
import java.util.*;
public class Compression {
        /* ArrayList String type that saves all new String */
        private static ArrayList<String> dic = null;
        /* how many words in the input file */
        private static int numWordsInInputFile = 0;
        /* how many lines in the input file */
        private static int numLinesInInputFile = 0;
        /* Output File and Data Stream */
        private static FileOutputStream fos = null;
        private static DataOutputStream dos = null;

        /* print all String in Dictionary */
        private static void printDictionary() {
            System.out.println("---- printing words from Dictionary ----");
            for(int i = 0; i < dic.size(); i++) {
                System.out.print(dic.get(i) + " ");
            }
            System.out.println("\n---- end of Dictionary ----");
        }
        /* return matched index. return value ranges from 0 to dic.size() - 1 if there is a match.
         * return dic.size() means there is no match */
        private static int getIndexFromDic(String s) {
            int i;

            for(i = 0; i < dic.size(); i++) {
                if (s.equals(dic.get(i))) {
                    System.out.println("match found for: " + s);
                    break;
                }
            }
            return i;
        }
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
        private static void initializeOutputFile(String fileName) {
            File compressedFile = new File(fileName);
            if (compressedFile.delete() == true) {
                System.out.println(fileName + " exists. deleting now. We will crate new one");
            }
            else {
                System.out.println(fileName + " does not exist. We will create new one");

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
            /* first 4-bytes is number of words in input file */
            try {
                dos.writeInt(numWordsInInputFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}