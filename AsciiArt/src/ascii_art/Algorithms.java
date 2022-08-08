package ascii_art;
import java.util.HashSet;
public class Algorithms {
    private static final String[] MORSE = new String[]
            {".-","-...","-.-.","-..",".","..-.","--.",
                    "....","..",".---","-.-",".-..","--","-.",
                    "---",".--.","--.-",".-.","...","-","..-",
                    "...-",".--","-..-","-.--","--.."};

    /**
     * pigeon hole principle riddle - n+1 numbers in range 1-n
     * the method finds the duplicate value
     * @param numList list of ints
     * @return duplicate value
     */
    public static int findDuplicate(int[] numList) {
        int i = numList[0];
        int j = numList[i];
        while (j != i){i = numList[i]; j = numList[numList[j]];}
        j = 0;
        while (i != j){i = numList[i]; j = numList[j];}
        return i;}

    /**
     * this method computes the number of unique morse representation in a word set
     * @param words english typed word set
     * @return num of unique morse representation
     */
    public static int uniqueMorseRepresentations(String[] words)
    {HashSet<String> morseComb = new HashSet<>();
        for (String word: words) {
            StringBuilder morseWord = new StringBuilder();
            for (char c: word.toLowerCase().toCharArray()) morseWord.append(MORSE[c - 'a']);
            morseComb.add(morseWord.toString());}
        return morseComb.size();}

}

