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
        ;
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
