import java.util.*;

public class SearchEngine {

    final private TreeMap<String, List<Integer>> index;

    public SearchEngine(ArrayList<String> documents){
        this.index = buildIndex(documents);
    }

    public List<Integer> search (String searchTerm){
        return index.get(searchTerm);
    }

    // 'buildIndex' builds the index and returns a map containing the dictionary and postings
    private TreeMap<String, List<Integer>> buildIndex(ArrayList<String> documents){
        TreeMap<String, List<DocWeightPair>> index = new TreeMap<>();

        TreeMap<String, ArrayList<DocOccPair>> occurrences = collectOccurrences(collectTerms(documents));

        for (String term: occurrences.keySet()){
            for (DocOccPair pair: occurrences.get(term)){
                // tf  = # occurrences in document / # words in document
                double tf  = calculate_tf(pair.getNumOccurrences(), documents.get(pair.document).length());
                // idf = # documents / # documents that contains the term
                double idf = calculate_idf(documents.size(), occurrences.get(term).size());

                if (index.containsKey(term))
                    // add a new pair of (document, weight) to the posting
                    index.get(term).add(new DocWeightPair(pair.document, tf * idf));
                else
                    // create entry for 'term'
                    index.put(term, new ArrayList<>(List.of(new DocWeightPair(pair.document, tf * idf))));
            }
            // one could use a LinkedList to insert elements in order and prevent sorting
            Collections.sort(index.get(term));
        }
        // storing postings as [(1,tf_idf), (2,tf_idf), (3,tf_idf), ...] is unnecessary,
        // perform a clean build and store as [1, 2, 3, ...] instead
        return clean(index);
    }

    private float calculate_tf(Integer numOccurrences, int numWords){
        return ((float) numOccurrences) / numWords;
    }

    private double calculate_idf(int numDocs, int numDocsContaining){
        return Math.log((float) numDocs / numDocsContaining);
    }

    // returns a clean version of the index since the scores 'tf_idf' are now redundant
    private TreeMap<String, List<Integer>> clean (TreeMap<String, List<DocWeightPair>> index){
        TreeMap<String, List<Integer>> cleanBuild = new TreeMap<>();
        for (String term: index.keySet()){
            cleanBuild.put(term, new ArrayList<>());
            for (DocWeightPair pair: index.get(term)){
                // pair.document + 1 since they are zero-indexed
                cleanBuild.get(term).add(pair.document + 1);
            }
        }
        return cleanBuild;
    }

    // collects and merges the postings i.e. entries will be stored as:
    // { term, [(1,3), (2,8), (3,5), ...] } instead of:
    // { term, [1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3] }
    private TreeMap<String, ArrayList<DocOccPair>> collectOccurrences(TreeMap<Integer, String[]> files){
        TreeMap<String, ArrayList<DocOccPair>> occurrences = new TreeMap<>();
        for (Integer document: files.keySet()){
            for (String term: files.get(document)){
                if (!occurrences.containsKey(term))
                    occurrences.put(term, new ArrayList<>(List.of(new DocOccPair(document, 1))));
                else{
                    int pairIndex = getIndexOfPair(occurrences.get(term), document);
                    if (pairIndex != -1){
                        DocOccPair pair = occurrences.get(term).get(pairIndex);
                        pair.setNumOccurrences(pair.getNumOccurrences() + 1);
                    }
                    else
                        occurrences.get(term).add(new DocOccPair(document, 1));
                }
            }
        }
        return occurrences;
    }

    private int getIndexOfPair(ArrayList<DocOccPair> pairs, Integer document){
        for (DocOccPair pair : pairs) {
            if (pair.document.equals(document))
                return pairs.indexOf(pair);
        }
        return -1;
    }

    // returns Map<documentID, terms[]> i.e. all the words/terms in each document
    // paired with the documentID
    private TreeMap<Integer, String[]> collectTerms(ArrayList<String> documents){
        TreeMap<Integer, String[]> files = new TreeMap<>();
        for (int i = 0; i < documents.size(); i++){
            files.put(i, documents.get(i).split(" "));
        }
        return files;
    }
}