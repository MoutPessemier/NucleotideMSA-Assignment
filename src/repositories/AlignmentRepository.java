package repositories;

import domain.Genome;
import domain.alignment.Alignment;
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
     */
    public void writeAlignmentToFile(String fileName, boolean append) {
        optimalStandardAlignment.writeAlignmentToFile(fileName, append);
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
     * Sets the standard representation of the optimal alignment
     *
     * @param optimalStandardAlignment standard representation of optimal alignment
     */
    public void setOptimalStandardAlignment(StandardAlignment optimalStandardAlignment) {
        this.optimalStandardAlignment = optimalStandardAlignment;
    }

    public void setOptimalSNPAlignment(SNPAlignment optimalSNPAlignment) {
        this.optimalSNPAlignment = optimalSNPAlignment;
    }
}
