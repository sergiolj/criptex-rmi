import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.service.Dictionary;

import java.util.List;

public class DictionaryTest {

    @Test
    public void mustReturnListStringWithFilteredWords() {

        Dictionary dictionary = Dictionary.getInstance();
        Double rareness = 15.0;
        String fileName = "/icf";

        List<String> result = dictionary.readMostCommonWords(rareness, fileName);

        Assertions.assertNotNull(result, "A lista de palavras não pode ser nula.");
        Assertions.assertTrue(result.contains("poste"));
        Assertions.assertTrue(result.contains("átrio"));
        Assertions.assertTrue((result.contains("cabra")));
    }

    @Test
    public void createDictionaryFromICF(){

        Dictionary dictionary = Dictionary.getInstance();
        Double rareness = 16.0;
        String fileName = "/icf";

        dictionary.createDictionaryFromICF(rareness, fileName);
        Assertions.assertTrue(dictionary.wordExists("ATRIO"));
        Assertions.assertTrue(dictionary.wordExists("FINDA"));
        Assertions.assertTrue(dictionary.wordExists("FUCAR"));
        Assertions.assertTrue(dictionary.wordExists("OPINE"));
        Assertions.assertTrue(dictionary.wordExists("FUTIL"));
        Assertions.assertFalse(dictionary.wordExists("PORDES"));
        Assertions.assertFalse(dictionary.wordExists("PEPLO"));
        Assertions.assertFalse(dictionary.wordExists("GALHA"));
    }
}
