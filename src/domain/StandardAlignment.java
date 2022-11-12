package domain;

import java.util.ArrayList;

// Represents the standard style alignment
public class StandardAlignment extends Alignment {
    // TODO: complete file

    public StandardAlignment() {
        super(new ArrayList<Genome>());
    }

    public StandardAlignment(ArrayList<Genome> sequences) {
        super(sequences);
    }

    /**
     * Gets the standard representation of the alignment
     *
     * @return the standard representation
     */
    @Override
    public String getRepresentation() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the difference score for the standard alignment
     *
     * @return the difference score
     */
    @Override
    public int calculateScore() {
        throw new UnsupportedOperationException();
    }
}
