public class DocWeightPair implements Comparable<DocWeightPair>{
    public final Integer document;
    public final Double tfidf;

    public DocWeightPair(Integer document, Double tfidf) {
        this.document = document;
        this.tfidf = tfidf;
    }

    public int compareTo(DocWeightPair docWeightPair){
        return docWeightPair.tfidf.compareTo(this.tfidf);
    }
}