package domain;

public class BioInformatician extends TeamMember {
    // TODO: complete file

    private Alignment personalAlignment;

    public BioInformatician(String firstName, String lastName, int yearsOfExperience, Alignment personalAlignment) {
        super(firstName, lastName, yearsOfExperience);
        setPersonalAlignment(personalAlignment);
    }

    /**
     * Writes their personal alignment to an output file
     */
    public void writeDataToFile() {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes the difference score of the personal alignment to an output file
     */
    public void writeReportToFile() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets this bioinformatician's alignment
     *
     * @return the bioinformatician's alignment
     */
    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }

    /**
     * Sets the personal alignment for this bioinformatician
     *
     * @param personalAlignment the alignment that becomes the personal alignment
     */
    public void setPersonalAlignment(Alignment personalAlignment) {
        this.personalAlignment = personalAlignment;
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
