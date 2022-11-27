package domain.alignment;

import domain.Genome;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// Represents the alignment with a list of genome sequences
public abstract class Alignment {
    // The list of genomes represented in this alignment
    protected ArrayList<Genome> genomes;

    public Alignment() {
        this(new ArrayList<Genome>());
    }

    public Alignment(ArrayList<Genome> genomes) {
        setGenomes(genomes);
    }

    /**
     * Searches the alignment for all genomes that contain a given sequence
     *
     * @param sequence a sequence of genome characters
     * @return a list of all identifiers
     */
    public ArrayList<String> searchForGenomes(String sequence) {
        ArrayList<String> identifiers = new ArrayList<>();
        genomes.forEach(genome -> {
            if (genome.getGenomeSequence().contains(sequence)) {
                identifiers.add(genome.getIdentifier());
            }
        });
        return identifiers;
    }

    /**
     * Searches the alignment for a specific genome using it's identifier
     *
     * @param identifier a genome identifier
     * @return a specific genome or null
     */
    public Genome findGenome(String identifier) {
        // Searches for a genome with the given identifier
        Optional<Genome> foundGenome = genomes.stream().filter(genome -> genome.getIdentifier().equals(identifier)).findFirst();
        // if a genome is found, return the genome, else return null
        return foundGenome.orElse(null);
    }

    /**
     * Replaces a genome sequence of a genome with a given identifier, with a new genome sequence
     *
     * @param identifier a genome identifier
     * @param sequence   a replacement sequence
     */
    public void replaceGenomeSequence(String identifier, String sequence) {
        genomes.forEach(genome -> {
            if (genome.getIdentifier().equals(identifier)) {
                genome.setGenomeSequence(sequence);
            }
        });
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
     * Gives representation of the alignment
     *
     * @return the representation
     */
    public abstract String getRepresentation();


    /**
     * Calculates the difference score for the alignment
     *
     * @return the difference score
     */
    public abstract int calculateScore();

    /**
     * Writes the alignment away to a file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append to the file or overwrite the file
     * @param start    starting delimiter
     * @param stop     stopping delimiter
     */
    public void writeAlignmentToFile(String fileName, boolean append, String start, String stop) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName, append))) {
            // indicate where this alignment starts
            writer.write(start);
            writer.write(System.lineSeparator());
            genomes.forEach(genome -> {
                try {
                    writer.write(genome.getIdentifier());
                    writer.write(System.lineSeparator());
                    writer.write(genome.getGenomeSequence());
                    writer.write(System.lineSeparator());
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + fileName + " at identifier: " + genome.getIdentifier());
                }
            });
            // delimit where this alignment stops
            writer.write(stop);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    /**
     * Writes the difference score to a file
     *
     * @param fileName the name of the output file
     * @param append   if the writer should append to the file or overwrite the file
     */
    public void writeDifferenceScoreToFile(String fileName, boolean append, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/" + fileName, append))) {
            writer.write(name + "\n");
            writer.write("Difference score: " + calculateScore() + "\n\n");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the output file: " + e);
        }
    }

    /**
     * Creates an alignment from a file (between 2 delimiters)
     *
     * @param fileName the name of the input file
     * @param start    the start delimiter
     * @param stop     the stop delimiter
     */
    public void createAlignmentFromFile(String fileName, String start, String stop) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/file/" + fileName))) {
            ArrayList<Genome> genomes = new ArrayList<>();
            String alignmentName = reader.readLine();
            while (!alignmentName.equals(start)) {
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
        } catch (IOException e) {
            System.out.println("Something went wrong when reading" + fileName + ":" + e);
        }
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
}
