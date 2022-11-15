package repositories;

import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;

// Keeps track of the optimal alignment in both notations
public class AlignmentRepository {

    private StandardAlignment optimalStandardAlignment;
    private SNPAlignment optimalSNPAlignment;

    public AlignmentRepository(StandardAlignment optimalStandardAlignment, SNPAlignment optimalSNPAlignment) {
        setOptimalStandardAlignment(optimalStandardAlignment);
        setOptimalSNPAlignment(optimalSNPAlignment);
    }

    /**
     * Gets the standard format of the optimal alignment
     *
     * @return standard alignment of the optimal alignment
     */
    public StandardAlignment getOptimalStandardAlignment() {
        return optimalStandardAlignment;
    }

    /**
     * Sets the standard representation of the optimal alignment
     *
     * @param optimalStandardAlignment standard representation of optimal alignment
     */
    public void setOptimalStandardAlignment(StandardAlignment optimalStandardAlignment) {
        this.optimalStandardAlignment = optimalStandardAlignment;
    }

    /**
     * Gets the SNP format of the optimal alignment
     *
     * @return SNP alignment of the optimal alignment
     */
    public SNPAlignment getOptimalSNPAlignment() {
        return optimalSNPAlignment;
    }

    /**
     * Sets the SNP representation of the optimal alignment
     *
     * @param optimalSNPAlignment SNP representation of optimal alignment
     */
    public void setOptimalSNPAlignment(SNPAlignment optimalSNPAlignment) {
        this.optimalSNPAlignment = optimalSNPAlignment;
    }
}
