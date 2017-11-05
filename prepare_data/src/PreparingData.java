/**
 * Created by Mary
 */

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class PreparingData {

    private String newText;
    private List<String> finalText;


    public PreparingData(String text){
        //make all caps to lowercase
        String temp = text.toLowerCase();
        //remove stop words
        try{
            newText = removeStopWords(temp);

        }catch (Exception e) {
            System.out.println("Could not remove stop words");
        }
        //stemming
        StanfordLemmatizer sl = new StanfordLemmatizer();
        finalText = sl.lemmatize(newText);
        //System.out.println(finalText);
    }

    public static String removeStopWords(String textFile) throws Exception {
        final List<String> stop_Words = Arrays.asList("'s", "a", "all", "also", "an", "and", "are", "as", "at", "be", "br", "by", "film", "for", "from", "he", "i", "if", "in", "into", "is", "it", "its", "movie", "of", "on", "one", "or", "other", "out", "s", "she", "so", "some", "such", "than", "that", "the", "their", "then", "there", "these", "they", "this", "to", "two", "up", "was", "we", "what", "when", "which", "will", "with", "you");
        final CharArraySet stopSet = new CharArraySet(stop_Words, true);
        Tokenizer tokenStream = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
        tokenStream.setReader(new StringReader(textFile.trim()));
        StopFilter stopFilter = new StopFilter(tokenStream, stopSet);
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = stopFilter.addAttribute(CharTermAttribute.class);
        stopFilter.reset();
        while (stopFilter.incrementToken()) {
            String term = charTermAttribute.toString();
            sb.append(term + " ");
        }
        return sb.toString();
    }


    public List<String> getFinalText() {
        return finalText;
    }

}

