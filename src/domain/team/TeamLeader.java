package domain.team;

import domain.alignment.Alignment;
import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;
import repositories.AlignmentRepository;

import java.util.ArrayList;

public class TeamLeader extends TeamMember {
    // A list of all bioinformaticians under the team leader
    private ArrayList<BioInformatician> team;
    // The AlignmentRepository containing the optimal solution
    private AlignmentRepository alignmentRepository;

    public TeamLeader(String firstName, String lastName, int yearsOfExperience, AlignmentRepository alignmentRepository) {
        super(firstName, lastName, yearsOfExperience);
        setAlignmentRepository(alignmentRepository);
        setTeam(new ArrayList<>());
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
        team.forEach(bioInformatician -> {
            bioInformatician.getPersonalAlignment().writeAlignmentToFile(firstName + lastName, true);
        });
    }

    /**
     * Writes all of the user's alignments scores to an output file
     */
    public void writeReportToFile() {
        team.forEach(bioInformatician -> {
            bioInformatician.getPersonalAlignment().writeDifferenceScoreToFile(firstName + lastName, true);
        });

    }

    /**
     * Writes the alignment to the optimal alignment in the AlignmentRepository
     *
     * @param alignment the alignment to become the optimal alignment
     */
    public void copyAlignmentToOptimalAlignment(Alignment alignment) {
        alignmentRepository.setOptimalSNPAlignment(new SNPAlignment(alignment.getSequences()));
        alignmentRepository.setOptimalStandardAlignment(new StandardAlignment(alignment.getSequences()));
    }

    /**
     * Overwrites a user's alignment with the optimal alignment
     *
     * @param user the user for which the alignment needs to be overwritten
     */
    public void overwriteAlignment(BioInformatician user) {
        StandardAlignment alignment = new StandardAlignment(alignmentRepository.getOptimalStandardAlignment().getSequences());
        user.setPersonalAlignment(alignment);
    }

    /**
     * Sets the AlignmentRepository for a TeamLeader
     *
     * @param alignmentRepository the alignment repository
     */
    public void setAlignmentRepository(AlignmentRepository alignmentRepository) {
        this.alignmentRepository = alignmentRepository;
    }

    /**
     * Sets the team for the team leader
     *
     * @param team the team
     */
    public void setTeam(ArrayList<BioInformatician> team) {
        this.team = team;
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
