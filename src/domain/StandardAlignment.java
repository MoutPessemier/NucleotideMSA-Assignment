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

    @Override
    public String getRepresentation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int calculateScore() {
        throw new UnsupportedOperationException();
    }
}
