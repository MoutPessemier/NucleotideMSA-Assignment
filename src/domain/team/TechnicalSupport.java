package domain.team;

import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;
import repositories.AlignmentRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TechnicalSupport extends TeamMember {
    // The Date and Time of the last backup
    private LocalDateTime lastBackup;
    // The AlignmentRepository on which the TechnicalSupport needs to work
    private AlignmentRepository alignmentRepository;
    // the team of bioinformaticians working on the alignments
    private ArrayList<BioInformatician> team;


    public TechnicalSupport(String firstName, String lastName, int yearsOfExperience, AlignmentRepository alignmentRepository) {
        super(firstName, lastName, yearsOfExperience);
        setAlignmentRepository(alignmentRepository);
    }

    /**
     * Backs up all user alignments, the optimal alignment and the SNP alignment
     */
    public void backupRepository() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/backup.txt"))) {
            // indicate where this alignment starts
            writer.write("Optimal Alignment");
            writer.write(System.lineSeparator());
            // only write away 1 of the optimal alignments because they are the same anyway
            alignmentRepository.getOptimalStandardAlignment().writeAlignmentToFile("backup.txt", true);
            // delimit where this alignment stops
            writer.write("--stop optimal--");
            writer.write(System.lineSeparator());
            writer.write(System.lineSeparator());
            team.forEach(bioInformatician -> {
                try {
                    writer.write(bioInformatician.firstName + " " + bioInformatician.lastName);
                    bioInformatician.getPersonalAlignment().writeAlignmentToFile("backup.txt", true);
                    writer.write("--stop " + bioInformatician.firstName + bioInformatician.lastName);
                } catch (IOException e) {
                    System.out.println("Something went wrong when backing up the alignment for " + bioInformatician.firstName + " " + bioInformatician.lastName + ": " + e);
                }
            });
            setLastBackup(LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the backup file: " + e);
        }
    }

    /**
     * Restores a previous backup by overwriting the current optimal alignment, it's SNP alignment and all the user's personal alignments
     */
    public void reinstateBackup() {
        alignmentRepository.getOptimalStandardAlignment().createAlignmentFromFile("backup.txt", "Optimal Alignment", "--stop optimal--");
        alignmentRepository.getOptimalSNPAlignment().createAlignmentFromFile("backup.txt", "Optimal Alignment", "--stop optimal--");
        team.forEach(bioInformatician -> {
            bioInformatician.getPersonalAlignment().createAlignmentFromFile("backup.txt", bioInformatician.firstName + " " + bioInformatician.lastName,
                    "--stop " + bioInformatician.firstName + bioInformatician.lastName);
        });
    }

    /**
     * Empties the current optimal alignment, the SNP alignment and each user's personal alignment
     */
    public void clearRepository() {
        alignmentRepository.setOptimalStandardAlignment(new StandardAlignment());
        alignmentRepository.setOptimalSNPAlignment(new SNPAlignment());
        team.forEach(bioInformatician -> {
            bioInformatician.setPersonalAlignment(new StandardAlignment());
        });
    }

    /**
     * Sets the date and time of the lastBackup variable
     *
     * @param lastBackup the date and time of the last back up
     */
    public void setLastBackup(LocalDateTime lastBackup) {
        this.lastBackup = lastBackup;
    }

    /**
     * Sets the AlignmentRepository for a TechnicalSupport
     *
     * @param alignmentRepository the alignment repository
     */
    public void setAlignmentRepository(AlignmentRepository alignmentRepository) {
        this.alignmentRepository = alignmentRepository;
    }

    /**
     * Sets the team for a TechnicalSupport
     *
     * @param team the list of bioinformaticians in the team
     */
    public void setTeam(ArrayList<BioInformatician> team) {
        this.team = team;
    }

    /**
     * A pleasing String representation of the class TechnicalSupport
     *
     * @return the String representation of TechnicalSupport
     */
    @Override
    public String toString() {
        return "Technical support " + super.toString();
    }
}
