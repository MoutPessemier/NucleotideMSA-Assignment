package repositories;

import domain.Genome;
import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;

import java.util.ArrayList;

// Keeps track of the optimal alignment in both formats
public class AlignmentRepository {

    private StandardAlignment optimalStandardAlignment;
    private SNPAlignment optimalSNPAlignment;

    public AlignmentRepository(StandardAlignment optimalAlignment, SNPAlignment optimalSNPAlignment) {
        setOptimalStandardAlignment(optimalAlignment);
        setOptimalSNPAlignment(optimalSNPAlignment);
    }

    /**
     * Writes alignment away to file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append or overwrite
     * @param start    starting delimiter
     * @param stop     stopping delimiter
     */
    public void writeAlignmentToFile(String fileName, boolean append, String start, String stop) {
        optimalStandardAlignment.writeAlignmentToFile(fileName, append, start, stop);
    }

    /**
     * Creates an alignment from an input file
     *
     * @param fileName the name of the input file
     * @param start    the start delimiter
     * @param stop     the stop delimiter
     */
    public void createAlignmentFromFile(String fileName, String start, String stop) {
        optimalStandardAlignment.createAlignmentFromFile(fileName, start, stop);
        optimalSNPAlignment.createAlignmentFromFile(fileName, start, stop);
    }

    /**
     * Gets the optimal alignment's genome sequences
     *
     * @return a list of genome sequences
     */
    public ArrayList<Genome> getOptimalSequences() {
        return optimalStandardAlignment.getSequences();
    }

    /**
     * Gets the difference score of the optimal alignment
     *
     * @return difference score of the optimal alignment
     */
    public int getOptimalDifferenceScore() {
        return optimalStandardAlignment.calculateScore();
    }

    /**
     * Sets the standard representation of the optimal alignment
     *
     * @param optimalStandardAlignment standard representation of the optimal alignment
     */
    public void setOptimalStandardAlignment(StandardAlignment optimalStandardAlignment) {
        this.optimalStandardAlignment = optimalStandardAlignment;
    }

    /**
     * Sets the SNP representation of the optimal alignment
     *
     * @param optimalSNPAlignment SNP representation of the optimal alignment
     */
    public void setOptimalSNPAlignment(SNPAlignment optimalSNPAlignment) {
        this.optimalSNPAlignment = optimalSNPAlignment;
    }
}
