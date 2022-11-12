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

    @Override
    public String getRepresentation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int calculateScore() {
        throw new UnsupportedOperationException();
    }
}
