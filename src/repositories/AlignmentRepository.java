package repositories;

import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;
import domain.team.BioInformatician;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Keeps track of the optimal alignment in both formats
public class AlignmentRepository {

    private StandardAlignment optimalStandardAlignment;
    private SNPAlignment optimalSNPAlignment;
    private ArrayList<BioInformatician> team;

    public AlignmentRepository(StandardAlignment optimalAlignment, SNPAlignment optimalSNPAlignment) {
        setOptimalStandardAlignment(optimalAlignment);
        setOptimalSNPAlignment(optimalSNPAlignment);
    }

    /**
     * Backs up all user alignments and the optimal alignment
     */
    public void backupRepository() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src" + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + "backup.txt"))) {
            // only write away 1 of the optimal alignments because they are the same anyway
            optimalStandardAlignment.writeAlignmentToFile("backup.txt", true, "Optimal Alignment", "--stop optimal--");
            team.forEach(bioInformatician -> {
                bioInformatician.writeAlignmentToFile("backup.txt", true, bioInformatician.getName(), "--stop " + bioInformatician.getName() + "--");
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the backup file: " + e);
        }
    }

    /**
     * Restores a previous backup by overwriting the current optimal alignment and all the user's personal alignments
     */
    public void reinstateBackup() {
        optimalStandardAlignment.createAlignmentFromFile("backup.txt", "Optimal Alignment", "--stop optimal--");
        team.forEach(bioInformatician -> {
            bioInformatician.createAlignmentFromFile("backup.txt", bioInformatician.getName(),
                    "--stop " + bioInformatician.getName() + "--");
        });
    }

    /**
     * Empties the current optimal alignment and each user's personal alignment
     */
    public void clearRepository() {
        setOptimalStandardAlignment(new StandardAlignment());
        setOptimalSNPAlignment(new SNPAlignment());
        team.forEach(bioInformatician -> {
            bioInformatician.setPersonalAlignment(new StandardAlignment());
        });
    }

    /**
     * Overwrites the user's personal alignment with the optimal alignment
     *
     * @param user the user whose personal alignment gets overridden
     */
    public void overwriteAlignmentForUser(BioInformatician user) {
        user.setPersonalAlignment(new StandardAlignment(optimalStandardAlignment.getSequences()));
    }

    /**
     * Gets the difference score of the optimal alignment
     *
     * @return difference score of the optimal alignment
     */
    public int getOptimalDifferenceScore() {
        return optimalStandardAlignment.calculateScore();
    }

    /**
     * Sets the standard representation of the optimal alignment
     *
     * @param optimalStandardAlignment standard representation of the optimal alignment
     */
    public void setOptimalStandardAlignment(StandardAlignment optimalStandardAlignment) {
        this.optimalStandardAlignment = optimalStandardAlignment;
    }

    /**
     * Sets the SNP representation of the optimal alignment
     *
     * @param optimalSNPAlignment SNP representation of the optimal alignment
     */
    public void setOptimalSNPAlignment(SNPAlignment optimalSNPAlignment) {
        this.optimalSNPAlignment = optimalSNPAlignment;
    }

    /**
     * Sets the team
     *
     * @param team the team of BioInformaticians
     */
    public void setTeam(ArrayList<BioInformatician> team) {
        this.team = team;
    }
}
