package domain.team;

import domain.alignment.Alignment;
import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;
import repositories.AlignmentRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        // The names of the TeamLeader are needed, not those of the bioinformatician
        String fileName = this.firstName + this.lastName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName + ".alignment.txt", true))) {
            team.forEach(bioInformatician -> {
                try {
                    writer.write(bioInformatician.firstName + " " + bioInformatician.lastName + "\n");
                } catch (IOException e) {
                    System.out.println("Error writing name to file: " + fileName);
                }
                bioInformatician.getPersonalAlignment().writeAlignmentToFile(fileName, true);
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing alignments to the output file: " + e);
        }
    }

    /**
     * Writes all of the user's alignments scores to an output file
     */
    public void writeReportToFile() {
        // The names of the TeamLeader are needed, not those of the bioinformatician
        String fileName = this.firstName + this.lastName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName + ".score.txt", true))) {
            team.forEach(bioInformatician -> {
                try {
                    writer.write(bioInformatician.firstName + " " + bioInformatician.lastName + "\n");
                } catch (IOException e) {
                    System.out.println("Error writing name to file: " + fileName);
                }
                bioInformatician.getPersonalAlignment().writeDifferenceScoreToFile(fileName, true);
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing difference scores to the output file: " + e);
        }

    }

    /**
     * Writes the alignment to the optimal alignment in the AlignmentRepository
     *
     * @param alignment the alignment to become the optimal alignment
     */
    public void copyAlignmentToOptimalAlignment(Alignment alignment) {
        alignmentRepository.setOptimalStandardAlignment(new StandardAlignment(alignment.getSequences()));
        alignmentRepository.setOptimalSNPAlignment(new SNPAlignment(alignment.getSequences()));
    }

    /**
     * Overwrites a user's alignment with the optimal alignment
     *
     * @param user the user for which the alignment needs to be overwritten
     */
    public void overwriteAlignment(BioInformatician user) {
        StandardAlignment alignment = new StandardAlignment(alignmentRepository.getOptimalSequences());
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
