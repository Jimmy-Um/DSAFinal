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
    public class FileCompress {
        /* ArrayList String type that saves all new String */
        private static ArrayList<String> dic = null;

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
}