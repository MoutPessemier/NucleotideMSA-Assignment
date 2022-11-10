package domain;

public class Genome {
    private String identifier;
    private String genomeSequence;

    public Genome(String identifier, String genomeSequence) {
        setIdentifier(identifier);
        setGenomeSequence(genomeSequence);
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setGenomeSequence(String genomeSequence) {
        this.genomeSequence = genomeSequence;
    }
}
