package domain.alignment;

import domain.Genome;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// Represents the alignment with a list of genome sequences
public abstract class Alignment {
    // The list of genomes represented in this alignment
    protected ArrayList<Genome> genomes;

    public Alignment(ArrayList<Genome> genomes) {
        setGenomes(genomes);
    }

    /**
     * Searches the alignment for all genomes with a given sequence
     *
     * @param sequence a sequence of genome characters
     * @return a list of all identifiers
     */
    public ArrayList<String> searchForGenome(String sequence) {
        ArrayList<String> sequences = new ArrayList<>();
        genomes.forEach(genome -> {
            if (genome.getGenomeSequence().contains(sequence)) {
                sequences.add(genome.getGenomeSequence());
            }
        });
        return sequences;
    }

    /**
     * Searches the alignment for a specific genome using it's identifier
     *
     * @param identifier a genome identifier
     * @return a specific genome
     */
    public Genome findGenome(String identifier) {
        Optional<Genome> foundGenome = genomes.stream().filter(genome -> genome.getIdentifier().equals(identifier)).findFirst();
        return foundGenome.orElse(null);
    }

    /**
     * Replaces a genome sequence of a genome with a given identifier, with a new genome sequence
     *
     * @param identifier a genome identifier
     * @param sequence   a replacement sequence
     */
    public void replaceGenomeSequence(String identifier, String sequence) {
        for (Genome genome : genomes) {
            if (genome.getIdentifier().equals(identifier)) {
                genome.setGenomeSequence(sequence);
            }
        }
    }

    /**
     * Replaces all occurrences of a character with a new character for a specific genome
     *
     * @param identifier         a genome identifier
     * @param characterToReplace the character to be replaced in the sequence
     * @param newCharacter       the character that will replace the old character in the sequence
     */
    public void replaceCharacterForGenome(String identifier, char characterToReplace, char newCharacter) {
        Optional<Genome> foundGenome = genomes.stream().filter(genome -> genome.getIdentifier().equals(identifier)).findFirst();
        foundGenome.ifPresent(genome -> genome.setGenomeSequence(genome.getGenomeSequence().replaceAll(Character.toString(characterToReplace), Character.toString(newCharacter))));
    }

    /**
     * Replaces all occurrences of a character in the alignment with a new character
     *
     * @param characterToReplace the character to be replaced in the sequence
     * @param newCharacter       the character that will replace the old character in the sequence
     */
    public void replaceAllCharacters(char characterToReplace, char newCharacter) {
        genomes.forEach(genome -> genome.setGenomeSequence(genome.getGenomeSequence().replaceAll(Character.toString(characterToReplace), Character.toString(newCharacter))));
    }

    /**
     * Adds a genome to the alignment
     *
     * @param identifier     a genome identifier
     * @param genomeSequence a genome sequence
     */
    public void addGenome(String identifier, String genomeSequence) {
        genomes.add(new Genome(identifier, genomeSequence));
    }

    /**
     * Removes a genome form the alignment
     *
     * @param identifier a genome identifier
     * @return the removed genome
     */
    public Genome removeGenome(String identifier) {
        Optional<Genome> foundGenome = genomes.stream().filter(genome -> genome.getIdentifier().equals(identifier)).findFirst();
        if (foundGenome.isPresent()) {
            genomes.remove(foundGenome.get());
            return foundGenome.get();
        }
        // no genome found
        return null;
    }

    /**
     * Gets the list of genome sequences in the alignment
     *
     * @return a list of genomes
     */
    public ArrayList<Genome> getSequences() {
        return genomes;
    }

    /**
     * Sets the sequences field equal to a given ArrayList of Genomes
     *
     * @param genomes an ArrayList<Genome>
     */
    public void setGenomes(ArrayList<Genome> genomes) {
        this.genomes = genomes;
    }

    /**
     * Gives the representation of the alignment
     *
     * @return the representation of the alignment in String format
     */
    public abstract String getRepresentation();

    /**
     * Calculates the difference score for the alignment
     *
     * @return the difference score
     */
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

    public void writeAlignmentToFile(String fileName, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName + ".alignment.txt", append))) {
            genomes.forEach(genome -> {
                try {
                    writer.write(genome.getIdentifier());
                    writer.write(System.lineSeparator());
                    writer.write(genome.getGenomeSequence());
                    writer.write(System.lineSeparator());
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + fileName + " at identifier: " + genome.getIdentifier());
                    // We want the program to stop if we can't write the whole alignment to a file
                    System.exit(1);
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    public void writeDifferenceScoreToFile(String fileName, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName + ".score.txt", append))) {
            writer.write("Difference score: " + calculateScore());
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
            System.exit(1);
        }
    }

    public void createAlignmentFromFile(String fileName, String start, String stop) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/file/" + fileName))) {
            ArrayList<Genome> genomes = new ArrayList<>();
            String alignmentName = reader.readLine();
            while(!alignmentName.equals(start)) {
                // skip until the start of that alignment is found
                alignmentName = reader.readLine();
            }
            System.out.printf("Now reading %s alignment%n", alignmentName);
            String line = reader.readLine();
            while (!line.equals(stop)) {
                String id = line;
                line = reader.readLine();
                String sequence = line;
                genomes.add(new Genome(id, sequence));
                line = reader.readLine();
            }
            setGenomes(genomes);
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Something went wrong when reading" + fileName + ":" + e);
            System.out.println("Exiting program...");
            System.exit(1);
        }
    }
}
