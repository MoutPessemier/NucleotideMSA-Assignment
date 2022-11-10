package domain;

// Represents one entity inside the fasta file (2 lines)
public class Genome {

    // The identifier of each genome
    private String identifier;
    // The genome sequence
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
