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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + firstName + lastName + ".alignment.txt"))) {
            team.forEach(bioInformatician -> {
                bioInformatician.getPersonalAlignment().getSequences().forEach(genome -> {
                    try {
                        writer.write(genome.getIdentifier());
                        writer.write(System.lineSeparator());
                        writer.write(genome.getGenomeSequence());
                        writer.write(System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println("Error writing to file for bioinformatician: " + bioInformatician.toString() + " at identifier: " + genome.getIdentifier());
                        // We want the program to stop if we can't write the whole alignment to a file
                        System.exit(1);
                    }
                });
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    /**
     * Writes all of the user's alignments scores to an output file
     */
    public void writeReportToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + firstName + lastName + ".score.txt"))) {
            team.forEach(bioInformatician -> {
                try {
                    writer.write("Difference score: " + bioInformatician.getPersonalAlignment().calculateScore());
                } catch (IOException e) {
                    System.out.println("Error writing difference score to file for bioinformatician: " + bioInformatician.toString());
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
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
