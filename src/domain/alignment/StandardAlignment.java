package domain.alignment;

import domain.Genome;

import java.util.ArrayList;

// Represents the standard style alignment
public class StandardAlignment extends Alignment {

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
        StringBuilder stringBuilder = new StringBuilder();
        genomes.forEach(genome -> {
            stringBuilder.append(genome.getIdentifier())
                    .append(System.getProperty("line.separator"))
                    .append(genome.getGenomeSequence())
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));

        });
        return stringBuilder.toString();
    }
}
