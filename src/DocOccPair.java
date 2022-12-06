public class DocOccPair {
    public final Integer document;
    private Integer numOccurrences;

    public DocOccPair(Integer document, Integer numOccurrences){
        this.document = document;
        this.numOccurrences = numOccurrences;
    }

    public Integer getNumOccurrences(){
        return numOccurrences;
    }

    public void setNumOccurrences(Integer numOccurrences){
        this.numOccurrences = numOccurrences;
    }
}