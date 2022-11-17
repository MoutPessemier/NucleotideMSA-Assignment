package repositories;

import domain.Genome;
import domain.Alignment;

import java.util.ArrayList;

// Keeps track of the optimal alignment in both formats
public class AlignmentRepository {

    private Alignment optimalAlignment;

    public AlignmentRepository(Alignment optimalAlignment) {
        setOptimalAlignment(optimalAlignment);
    }

    /**
     * Writes alignment away to file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append or overwrite
     */
    public void writeAlignmentToFile(String fileName, boolean append) {
        optimalAlignment.writeAlignmentToFile(fileName, append);
    }

    /**
     * Creates an alignment from an input file
     *
     * @param fileName the name of the input file
     * @param start    the start delimiter
     * @param stop     the stop delimiter
     */
    public void createAlignmentFromFile(String fileName, String start, String stop) {
        optimalAlignment.createAlignmentFromFile(fileName, start, stop);
    }

    /**
     * Gets the optimal alignment's genome sequences
     *
     * @return a list of genome sequences
     */
    public ArrayList<Genome> getOptimalSequences() {
        return optimalAlignment.getSequences();
    }

    /**
     * Sets the standard representation of the optimal alignment
     *
     * @param optimalAlignment standard representation of optimal alignment
     */
    public void setOptimalAlignment(Alignment optimalAlignment) {
        this.optimalAlignment = optimalAlignment;
    }
}
