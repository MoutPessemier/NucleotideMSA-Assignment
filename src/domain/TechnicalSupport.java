package domain;

import java.time.LocalDateTime;

public class TechnicalSupport extends TeamMember {

    private LocalDateTime lastBackup;

    public TechnicalSupport(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    /**
     * Backs up all user alignments, the optimal alignment and the SNP alignment
     */
    public void backupRepository() {
        throw new UnsupportedOperationException();
    }

    /**
     * Restores a previous backup by overwriting the current optimal alignment, it's SNP alignment and all the user's personal alignments
     */
    public void reinstateBackup() {
        throw new UnsupportedOperationException();
    }

    /**
     * Empties the current optimal alignment, the SNP alignment and each user's personal alignment
     */
    public void clearRepository() {
        throw new UnsupportedOperationException();
    }
}
