import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<String> documents = new ArrayList<>();
        documents.add("the brown fox jumped over the brown dog");
        documents.add("the lazy brown dog sat in the corner");
        documents.add("the red fox bit the lazy dog");

         /* = = = = = = = = = = = = = = = = = = = = = = = = = = = = = */
        /* = = = = = = = = = = = = = = = = = = = = = = = = = = = = = */

        SearchEngine searchEngine = new SearchEngine(documents);

        System.out.print("Search: ");
        String searchTerm = reader.readLine();

        List<Integer> results = searchEngine.search(searchTerm);

        for (Integer documentID: results)
            System.out.println("Document " + documentID);

         /* = = = = = = = = = = = = = = = = = = = = = = = = = = = = = */
        /* = = = = = = = = = = = = = = = = = = = = = = = = = = = = = */
    }
}
