package domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BioInformatician extends TeamMember {
    // The bioinformatician's personal alignment
    private Alignment personalAlignment;

    public BioInformatician(String firstName, String lastName, int yearsOfExperience, Alignment personalAlignment) {
        super(firstName, lastName, yearsOfExperience);
        setPersonalAlignment(personalAlignment);
    }

    /**
     * Writes their personal alignment to an output file
     */
    public void writeDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + firstName + lastName + ".alignment.txt"))) {
            personalAlignment.genomes.forEach(genome -> {
                try {
                    writer.write(genome.getIdentifier());
                    writer.write(System.lineSeparator());
                    writer.write(genome.getGenomeSequence());
                    writer.write(System.lineSeparator());
                } catch (IOException e) {
                    System.out.println("Error writing to file for identifier: " + genome.getIdentifier());
                    // We want the program to stop if we can't write the whole alignment to a file
                    System.exit(1);
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    /**
     * Writes the difference score of the personal alignment to an output file
     */
    public void writeReportToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + firstName + lastName + ".score.txt"))) {
            writer.write("Difference score: " + personalAlignment.calculateScore());
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    /**
     * Gets this bioinformatician's alignment
     *
     * @return the bioinformatician's alignment
     */
    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }

    /**
     * Sets the personal alignment for this bioinformatician
     *
     * @param personalAlignment the alignment that becomes the personal alignment
     */
    public void setPersonalAlignment(Alignment personalAlignment) {
        this.personalAlignment = personalAlignment;
    }

    /**
     * A pleasing String representation of the class BioInformatician
     *
     * @return the String representation of BioInformatician
     */
    @Override
    public String toString() {
        return "Bio-Informatician " + super.toString();
    }
}
