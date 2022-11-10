package domain;

public class BioInformatician extends TeamMember {

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

    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }

    public void setPersonalAlignment(Alignment personalAlignment) {
        this.personalAlignment = personalAlignment;
    }
}
