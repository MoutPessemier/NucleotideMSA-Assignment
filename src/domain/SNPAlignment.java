package domain;

import java.util.ArrayList;

// Represents the SNP style aligment
public class SNPAlignment extends Alignment {

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
        StringBuilder stringBuilder = new StringBuilder();
        Genome reference = genomes.get(0);
        genomes.forEach(genome -> {
            stringBuilder.append(genome.getIdentifier())
                    .append(System.getProperty("line.separator"));
            for (int i = 0; i < reference.getGenomeSequence().length(); i++) {
                if(reference.getGenomeSequence().charAt(i) == genome.getGenomeSequence().charAt(i)) {
                    stringBuilder.append(".");
                } else {
                    stringBuilder.append(genome.getGenomeSequence().charAt(i));
                }
            }
            stringBuilder.append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));
        });
        return stringBuilder.toString();
    }
}
