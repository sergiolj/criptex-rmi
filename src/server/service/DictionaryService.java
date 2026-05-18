package server.service;

import java.util.ArrayList;
import java.util.List;

public class DictionaryService {
    private List<String> words = new ArrayList<>();
    private int index = 0;

    public DictionaryService() {
        words.add("volei");
        words.add("atrio");
        words.add("broto");
    }

    public String getWord(int index) {
        if (index < words.size()) {
            String word = words.get(index);
            index++;
            return word;

        }else{
            return "Não há novas palavras para adivinhar";
        }
    }
}
