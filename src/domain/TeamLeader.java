package domain;

import java.util.ArrayList;

public class TeamLeader extends TeamMember {
    // TODO: complete file

    private final ArrayList<BioInformatician> team;

    public TeamLeader(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
        team = new ArrayList<>();
    }

    /**
     * Adds a bioinformatician to the team
     *
     * @param member a bioinformatician
     */
    public void addTeamMember(BioInformatician member) {
        team.add(member);
    }

    /**
     * Removes a bioinformatician from the team
     *
     * @param member a bioinformatician
     */
    public void removeMemberFromTeam(BioInformatician member) {
        team.remove(member);
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

    /**
     * Writes the alignment to the optimal alignment in the AlignmentRepository
     *
     * @param alignment the alignment to become the optimal alignment
     */
    public void copyAlignmentToOptimalAlignment(Alignment alignment) {
        throw new UnsupportedOperationException();
    }

    /**
     * Overwrites a user's alignment with the optimal alignment
     *
     * @param user the user for which the alignment needs to be overwritten
     */
    public void overwriteAlignment(BioInformatician user) {
        throw new UnsupportedOperationException();
    }

    /**
     * A pleasing String representation of the class TeamLeader
     *
     * @return the String representation of TeamLeader
     */
    @Override
    public String toString() {
        return "Team leader " + super.toString();
    }
}
