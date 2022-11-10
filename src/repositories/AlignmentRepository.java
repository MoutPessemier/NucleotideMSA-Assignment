package repositories;

import domain.SNPAlignment;
import domain.StandardAlignment;

// Keeps track of the optimal alignment in both notations
public class AlignmentRepository {
    // TODO: complete file

    private StandardAlignment optimalStandardAlignment;
    private SNPAlignment optimalSNPAlignment;

    public AlignmentRepository(StandardAlignment optimalStandardAlignment, SNPAlignment optimalSNPAlignment) {
        setOptimalStandardAlignment(optimalStandardAlignment);
        setOptimalSNPAlignment(optimalSNPAlignment);
    }

    public StandardAlignment getOptimalStandardAlignment() {
        return optimalStandardAlignment;
    }

    public void setOptimalStandardAlignment(StandardAlignment optimalStandardAlignment) {
        this.optimalStandardAlignment = optimalStandardAlignment;
    }

    public SNPAlignment getOptimalSNPAlignment() {
        return optimalSNPAlignment;
    }

    public void setOptimalSNPAlignment(SNPAlignment optimalSNPAlignment) {
        this.optimalSNPAlignment = optimalSNPAlignment;
    }
}
