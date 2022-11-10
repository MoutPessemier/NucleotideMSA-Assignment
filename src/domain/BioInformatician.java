package domain;

public class BioInformatician extends TeamMember {

    public BioInformatician(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    /**
     * Writes their personal alignment to an output file
     */
    public void writeDataToFile() {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes the different scores of the personal alignment to an output file
     */
    public void writeReportToFile() {
        throw new UnsupportedOperationException();
    }
}
