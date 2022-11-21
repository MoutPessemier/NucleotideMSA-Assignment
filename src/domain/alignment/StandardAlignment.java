package domain.alignment;

import domain.Genome;

import java.util.ArrayList;

// Represents the standard alignment format
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

    @Override
    public int calculateScore() {
        int differences = 0;
        Genome reference = genomes.get(0);
        for (Genome genome : genomes) {
            for (int i = 0; i < genome.getGenomeSequence().length(); i++) {
                if (genome.getGenomeSequence().charAt(i) != reference.getGenomeSequence().charAt(i)) {
                    differences++;
                }
            }
        }
        return differences;
    }
}