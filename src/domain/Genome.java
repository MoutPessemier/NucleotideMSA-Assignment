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

    /**
     * Gets the identifier of this genome
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
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
     * Gets the genome sequence of this genome
     *
     * @return the genome sequence
     */
    public String getGenomeSequence() {
        return genomeSequence;
    }

    /**
     * Sets the sequence for the Genome
     *
     * @param genomeSequence the genome sequence
     */
    public void setGenomeSequence(String genomeSequence) {
        this.genomeSequence = genomeSequence;
    }

    /**
     * Gives a nice representation of a Genome Object
     *
     * @return the string representation of this genome
     */
    @Override
    public String toString() {
        return "identifier: " + identifier + " and genome sequence: " + genomeSequence;
    }
}
