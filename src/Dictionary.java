/**
 * @author Raymond Zhu
 * @author Jimmy Um
 *
 * The Dictionary that is appended at the end of the .bin file
 */
import java.util.HashMap;

public abstract class Dictionary {
    static HashMap<String, Integer> dic = new HashMap<String, Integer>();
    abstract void buildDictionary();

    /**
     * Sets the Dictionary to the provided map
     *
     * @param m Given HashMap
     */
    public static void setDic(HashMap<String, Integer> m) {
        dic = m;
    }

    /**
     * Adds a key and value pair to the Dictionary
     *
     * @param K Key
     * @param V Value
     */
    public static void addDic(String K, Integer V) {
        dic.put(K, V);
    }

    /**
     * Returns the instance variable dic
     *
     * @return the Dictionary
     */
    public static HashMap<String, Integer> getDic() {
        return dic;
    }
}
