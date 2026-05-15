package test;

import model.Dictionary;

import java.util.List;

public class mainTest {
    public static void main(String[] args) {
        Dictionary dict = Dictionary.getInstance();
        List<String> validWords = dict.getDictionaryList();

        for(int i=0; i < validWords.size(); i++){
            System.out.println(validWords.get(i));
        }

//      for(String word: validWords) {
//         System.out.println(word);
//      }
    }
}
