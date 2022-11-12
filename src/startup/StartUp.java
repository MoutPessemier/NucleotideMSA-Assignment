package startup;

import domain.*;
import repositories.AlignmentRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StartUp {
    public static void main(String[] args) {
        // TODO: complete file
        // STEP 1.: Creating the starting alignments
        StandardAlignment startingAlignment = new StandardAlignment();
        SNPAlignment startingAlignmentSNP = new SNPAlignment();

        // STEP 2.: Read in the fasta file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/hiv.fasta"))) {
            // STEP 3.: Create genome object from the fasta file and add them to the alignment objects
            // TODO: figure out how to read 2 lines at a time to create a genome
            String line = reader.readLine();
            while (line != null) {
                // TODO: read both lines
                // create genome object
                // add genome to both alignments
                // move cursor
            }
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
        System.out.printf("The starting alignment in standard format: %s%n", startingAlignment.getRepresentation());
        System.out.printf("The starting alignment in SNP format: %s%n", startingAlignmentSNP.getRepresentation());

        // STEP 5.: Create the repository to keep track of the optimal alignments
        AlignmentRepository alignmentRepository = new AlignmentRepository(startingAlignment, startingAlignmentSNP);

        // STEP 6.: Team creation
        TeamLeader teamLeader;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/team.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] lineComponents = line.split(" ");
                switch (lineComponents[0]) {
                    case "TeamLead":
                        teamLeader = new TeamLeader(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), alignmentRepository);
                        break;
                    case "BioInformatician":
                        //TODO: replace personal alignment with made alignment
                        BioInformatician bioInformatician = new BioInformatician(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), startingAlignment);
                        // TODO: make sure teamleader is initialised
                        // teamLeader.addTeamMember(bioInformatician);
                        break;
                    case "TechnicalSupport":
                        TechnicalSupport technicalSupport = new TechnicalSupport(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]));
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
        update every bioinformatician's alignment
        backup repo
        flush repo
        restore repo
        write away all scores (teamleader)
        write away individual scores (bioinformaticians)
        write away all reports (teamleader)
        write away individual reports (bioinformaticians)
        */
    }
}
