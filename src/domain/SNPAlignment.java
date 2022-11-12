package domain;

import java.util.ArrayList;

// Represents the SNP style aligment
public class SNPAlignment extends Alignment {
    // TODO: complete file

    public SNPAlignment() {
        super(new ArrayList<Genome>());
    }

    public SNPAlignment(ArrayList<Genome> sequences) {
        super(sequences);
    }

    /**
     * Gets the SNP representation of the alignment
     *
     * @return the SNP representation
     */
    @Override
    public String getRepresentation() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the difference score for the SNP alignment
     *
     * @return the difference score
     */
    @Override
    public int calculateScore() {
        throw new UnsupportedOperationException();
    }
}
