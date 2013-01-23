
public class SynchronizedExample2 {
    
    public static void main(String[] args) {
        
        TwoStrings ts = new TwoStrings();
        
        new PrintStringsThread("Hello ", "there.", ts);
        new PrintStringsThread("How are ", "you?", ts);
        new PrintStringsThread("Thank you ", "very much!", ts);
    }
    
}
