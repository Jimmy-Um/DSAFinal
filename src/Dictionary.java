import java.util.HashMap;

public abstract class Dictionary {
    static HashMap<String, Integer> dic = new HashMap<String, Integer>();
    abstract void buildDictionary();

    public static void setDic(HashMap<String, Integer> m) {
        dic = m;
    }

    public static void addDic(String K, Integer V) {
        dic.put(K, V);
    }

    public static HashMap<String, Integer> getDic() {
        return dic;
    }
}
