package domain;

public class TeamLeader extends TeamMember {

    public TeamLeader(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    /**
     * Writes all personal alignments to an output file
     */
    public void writeDataToFile() {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes all of the user's alignments scores to an output file
     */
    public void writeReportToFile() {
        throw new UnsupportedOperationException();
    }
}
