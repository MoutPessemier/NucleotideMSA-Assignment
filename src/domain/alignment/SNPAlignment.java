package domain.alignment;

import domain.Genome;

import java.util.ArrayList;
import java.util.Arrays;

// Represents the SNP alignment format
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
            // check each character with the reference genome to only show the differences
            for (int i = 0; i < reference.getGenomeSequence().length(); i++) {
                if (reference.getGenomeSequence().charAt(i) == genome.getGenomeSequence().charAt(i)) {
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

    @Override
    public int calculateScore() {
        int score = 0;
        for (String line : getRepresentation().split("\n")) {
            if (!line.equals("") && !line.startsWith(">")) {
                for (String character : line.split("")) {
                    if(!character.equals(".")) {
                        score++;
                    }
                }
            }
        }
        return score;
    }
}