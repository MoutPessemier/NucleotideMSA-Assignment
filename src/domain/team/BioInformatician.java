package domain.team;

import domain.Genome;
import domain.alignment.StandardAlignment;

import java.util.ArrayList;

public class BioInformatician extends TeamMember {
    // The bioinformatician's personal alignment
    private StandardAlignment personalAlignment;

    public BioInformatician(String firstName, String lastName, int yearsOfExperience, StandardAlignment personalAlignment) {
        super(firstName, lastName, yearsOfExperience);
        setPersonalAlignment(personalAlignment);
    }

    /**
     * Writes their personal alignment to an output file
     */
    public void writeDataToFile() {
        personalAlignment.writeAlignmentToFile(firstName + lastName + ".alignment.txt", false, getName(), "");
    }

    /**
     * Writes the difference score of the personal alignment to an output file
     */
    public void writeReportToFile() {
        personalAlignment.writeDifferenceScoreToFile(firstName + lastName + ".score.txt", false, getName());
    }

    /**
     * Searches the alignment for all genomes that contain a given sequence
     *
     * @param sequence a sequence of genome characters
     * @return a list of all identifiers
     */
    public ArrayList<String> searchForGenomes(String sequence) {
        return personalAlignment.searchForGenomes(sequence);
    }

    /**
     * Searches the alignment for a specific genome using it's identifier
     *
     * @param identifier a genome identifier
     * @return a specific genome or null
     */
    public Genome findGenome(String identifier) {
        return personalAlignment.findGenome(identifier);
    }

    /**
     * Replaces a genome sequence of a genome with a given identifier, with a new genome sequence
     *
     * @param identifier a genome identifier
     * @param sequence   a replacement sequence
     */
    public void replaceGenomeSequence(String identifier, String sequence) {
        personalAlignment.replaceGenomeSequence(identifier, sequence);
    }

    /**
     * Replaces all occurrences of a character with a new character for a specific genome
     *
     * @param identifier        a genome identifier
     * @param sequenceToReplace the sequence of characters to be replaced in the genome sequence
     * @param newSequence       the sequence of characters that will replace the old sequence in the genome sequence
     */
    public void replaceSequenceForGenome(String identifier, String sequenceToReplace, String newSequence) {
        personalAlignment.replaceSequenceForGenome(identifier, sequenceToReplace, newSequence);
    }

    /**
     * Replaces all occurrences of a character in the alignment with a new character
     *
     * @param sequenceToReplace the sequence of characters to be replaced in the genome sequence
     * @param newSequence       the sequence of characters that will replace the old sequence in the genome sequence
     */
    public void replaceAllSequences(String sequenceToReplace, String newSequence) {
        personalAlignment.replaceAllSequences(sequenceToReplace, newSequence);
    }

    /**
     * Adds a genome to the alignment
     *
     * @param identifier     a genome identifier
     * @param genomeSequence a genome sequence
     */
    public void addGenome(String identifier, String genomeSequence) {
        personalAlignment.addGenome(identifier, genomeSequence);
    }

    /**
     * Removes a genome form the alignment
     *
     * @param identifier a genome identifier
     * @return the removed genome
     */
    public Genome removeGenome(String identifier) {
        return personalAlignment.removeGenome(identifier);
    }

    /**
     * Calculates the difference score for the alignment
     *
     * @return the difference score
     */
    public int calculateScore() {
        return personalAlignment.calculateScore();
    }

    /**
     * Writes the alignment away to a file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append to the file or overwrite the file
     * @param start    starting delimiter
     * @param stop     stopping delimiter
     */
    public void writeAlignmentToFile(String fileName, boolean append, String start, String stop) {
        personalAlignment.writeAlignmentToFile(fileName, append, start, stop);
    }

    /**
     * Writes the difference score to a file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append to the file or overwrite the file
     */
    public void writeDifferenceScoreToFile(String fileName, boolean append, String name) {
        personalAlignment.writeDifferenceScoreToFile(fileName, append, name);
    }

    /**
     * Creates an alignment from a file (between 2 delimiters)
     *
     * @param fileName the name of the input file
     * @param start    the start delimiter
     * @param stop     the stop delimiter
     */
    public void createAlignmentFromFile(String fileName, String start, String stop) {
        personalAlignment.createAlignmentFromFile(fileName, start, stop);
    }

    /**
     * Gets this bioinformatician's alignment
     *
     * @return the bioinformatician's alignment
     */
    public StandardAlignment getPersonalAlignment() {
        return personalAlignment;
    }

    /**
     * Sets the personal alignment for this bioinformatician
     *
     * @param personalAlignment the alignment that becomes the personal alignment
     */
    public void setPersonalAlignment(StandardAlignment personalAlignment) {
        ArrayList<Genome> newList = new ArrayList<Genome>();
        personalAlignment.getSequences().forEach(genome -> {
            newList.add(new Genome(genome.getIdentifier(), genome.getGenomeSequence()));
        });
        this.personalAlignment = new StandardAlignment(newList);
    }

    /**
     * A pleasing String representation of the class BioInformatician
     *
     * @return the String representation of BioInformatician
     */
    @Override
    public String toString() {
        return "Bio-Informatician " + super.toString();
    }
}
