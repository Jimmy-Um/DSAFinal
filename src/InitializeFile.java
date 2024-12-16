/**
 * @author Raymond Zhu
 * @author Jimmy Um
 *
 * Contains the createNewFile method which is overriden in Compression.java and
 * FileDecompression.java
 */
public interface InitializeFile {
    /**
     * Used to initialize the input and output files in Compression.java and Decompression.java
     *
     * @param fileName name of the file
     */
    void createNewFile(String fileName);
}
