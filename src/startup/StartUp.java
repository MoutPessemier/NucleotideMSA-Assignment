package startup;

import domain.*;
import repositories.AlignmentRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StartUp {
    public static void main(String[] args) {
        // TODO: complete file
        // STEP 1.: Creating the starting alignments
        StandardAlignment startingAlignment = new StandardAlignment();
        SNPAlignment startingAlignmentSNP = new SNPAlignment();

        // STEP 2.: Read in the fasta file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/hiv.fasta"))) {
            // STEP 3.: Create genome object from the fasta file and add them to the alignment objects
            ArrayList<Genome> genomes = new ArrayList<>();
            // initial reading of the first id
            String line = reader.readLine();
            while (line != null) {
                // save the id
                String id = line;
                // read the sequence
                line = reader.readLine();
                // save the sequence
                String sequence = line;
                // add new genome object to list
                genomes.add(new Genome(id, sequence));
                // move cursor by reading the id
                line = reader.readLine();
            }
            // set the genomes in the starting alignments
            startingAlignment.setGenomes(genomes);
            startingAlignmentSNP.setGenomes(genomes);
        } catch (FileNotFoundException e) {
            System.out.println("Fasta file not found: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Something went wrong when reading the fasta file: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        }

        // STEP 4.: Print the starting alignments by calling getRepresentation()
        System.out.printf("The starting alignment in standard format is: %n%sWith a difference score of: %n%d%n%n%n", startingAlignment.getRepresentation(), startingAlignment.calculateScore());
        System.out.printf("The starting alignment in SNP format is: %n%sWith a difference score of: %n%d%n%n%n", startingAlignmentSNP.getRepresentation(), startingAlignmentSNP.calculateScore());

        // STEP 5.: Create the repository to keep track of the optimal alignments
        AlignmentRepository alignmentRepository = new AlignmentRepository(startingAlignment, startingAlignmentSNP);

        // STEP 6.: Read in the team file
        TeamLeader teamLeader;
        TechnicalSupport technicalSupport;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/team.txt"))) {
            String line = reader.readLine();
            // STEP 7.: Team creation
            ArrayList<BioInformatician> team = new ArrayList<>();
            while (line != null) {
                String[] lineComponents = line.split(" ");
                switch (lineComponents[0]) {
                    case "TeamLead":
                        teamLeader = new TeamLeader(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), alignmentRepository);
                        teamLeader.setTeam(team);
                        break;
                    case "BioInformatician":
                        BioInformatician bioInformatician = new BioInformatician(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), startingAlignment);
                        team.add(bioInformatician);
                        break;
                    case "TechnicalSupport":
                        technicalSupport = new TechnicalSupport(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]));
                        break;
                    default:
                        System.out.println("Unrecognised team role, skipping creation of team member");
                        continue;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Team file not found: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Something went wrong when reading the team file: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        }

        // STEP 7.: Display the whole team


        /* POSSIBLE SCENARIOS TO SHOW:
        let the bioinformaticians do their work
        write best alignment to repo
        update every/some bioinformatician's alignment to match repo alignment
        backup repo
        flush repo
        restore repo
        write away all scores (teamleader)
        write away individual scores (bioinformaticians)
        write away all reports (teamleader)
        write away individual reports (bioinformaticians)
        recalculate scores
        */
    }
}
