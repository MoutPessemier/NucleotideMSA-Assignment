package domain;

// Represents one entity inside the fasta file (2 lines)
public class Genome {
    // TODO: complete file

    // The identifier of each genome
    private String identifier;
    // The genome sequence
    private String genomeSequence;

    public Genome(String identifier, String genomeSequence) {
        setIdentifier(identifier);
        setGenomeSequence(genomeSequence);
    }

    /**
     * Sets the identifier for the Genome
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Sets the sequence for the Genome
     *
     * @param genomeSequence the genome sequence
     */
    public void setGenomeSequence(String genomeSequence) {
        this.genomeSequence = genomeSequence;
    }
}
