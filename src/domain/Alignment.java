package domain;

import java.util.ArrayList;

// Represents the alignment with a list of genome sequences
public abstract class Alignment {
    // TODO: complete file

    // The list of genomes represented in this alignment
    private ArrayList<Genome> sequences;

    public Alignment(ArrayList<Genome> sequences) {
        setSequences(sequences);
    }

    /**
     * Searches the alignment for all genomes with a given sequence
     *
     * @param sequence a sequence of genome characters
     * @return a list of all identifiers
     */
    public ArrayList<String> searchForGenome(String sequence) {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches the alignment for a specific genome using it's identifier
     *
     * @param identifier a genome identifier
     * @return a specific genome
     */
    public Genome findGenome(String identifier) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces a genome sequence of a genome with a given identifier, with a new genome sequence
     *
     * @param identifier a genome identifier
     * @param sequence   a replacement sequence
     */
    public void replaceGenome(String identifier, String sequence) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces all occurrences of a character with a new character for a specific genome
     *
     * @param identifier         a genome identifier
     * @param characterToReplace the character to be replaced in the sequence
     * @param newCharacter       the character that will replace the old character in the sequence
     */
    public void replaceCharacterForGenome(String identifier, char characterToReplace, char newCharacter) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces all occurrences of a character in the alignment with a new character
     *
     * @param characterToReplace the character to be replaced in the sequence
     * @param newCharacter       the character that will replace the old character in the sequence
     */
    public void replaceAllCharacters(char characterToReplace, char newCharacter) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a genome to the alignment
     *
     * @param identifier     a genome identifier
     * @param genomeSequence a genome sequence
     */
    public void addGenome(String identifier, String genomeSequence) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a genome form the alignment
     *
     * @param identifier a genome identifier
     * @return the removed genome
     */
    public Genome removeGenome(String identifier) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of genome sequences in the alignment
     *
     * @return a list of genomes
     */
    public ArrayList<Genome> getSequences() {
        return sequences;
    }

    /**
     * Sets the sequences field equal to a given ArrayList of Genomes
     * @param sequences an ArrayList<Genome>
     */
    private void setSequences(ArrayList<Genome> sequences) {
        this.sequences = sequences;
    }

    /**
     * Gives the representation of the alignment
     *
     * @return the representation of the alignment in String format
     */
    public abstract String getRepresentation();

    /**
     * Calculates the difference score for the alignment
     *
     * @return the difference score
     */
    public abstract int calculateScore();
}
