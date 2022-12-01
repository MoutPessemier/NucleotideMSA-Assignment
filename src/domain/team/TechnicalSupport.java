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

    public TechnicalSupport(String firstName, String lastName, int yearsOfExperience, AlignmentRepository alignmentRepository) {
        super(firstName, lastName, yearsOfExperience);
        setAlignmentRepository(alignmentRepository);
    }

    /**
     * Backs up all user alignments and the optimal alignment
     */
    public void backupRepository() {
        alignmentRepository.backupRepository();
        setLastBackup(LocalDateTime.now());
    }

    /**
     * Restores a previous backup by overwriting the current optimal alignment and all the user's personal alignments
     */
    public void reinstateBackup() {
        alignmentRepository.reinstateBackup();
    }

    /**
     * Empties the current optimal alignment and each user's personal alignment
     */
    public void clearRepository() {
        alignmentRepository.clearRepository();
    }

    /**
     * Gets the date and time of when te last backup has been made
     *
     * @return when the last backup has been made
     */
    public LocalDateTime getLastBackup() {
        return lastBackup;
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
     * A pleasing String representation of the class TechnicalSupport
     *
     * @return the String representation of TechnicalSupport
     */
    @Override
    public String toString() {
        return "Technical Support " + super.toString();
    }
}
