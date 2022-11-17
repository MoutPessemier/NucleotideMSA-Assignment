package startup;

import domain.Alignment;
import domain.Genome;
import domain.team.BioInformatician;
import domain.team.TeamLeader;
import domain.team.TechnicalSupport;
import repositories.AlignmentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class StartUp {
    public static void main(String[] args) {
        // TODO: complete file
        // Step 0.: Read in the properties
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("application.properties")) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Something went wrong when reading the properties file: " + e);
            System.out.println("Exiting program...");
            System.exit(1);
        }
        // STEP 1.: Creating the starting alignments
        Alignment startingAlignment = new Alignment();
        // STEP 2.: Read in the fasta file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/" + properties.getProperty("fastafilename")))) {
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
        System.out.printf("The starting alignment in standard format is: %n%sIn SNP format %s%nWith a difference score of: %n%d%n%n%n",
                startingAlignment.getRepresentation(), startingAlignment.getSNPRepresentation(), startingAlignment.calculateScore());

        // STEP 5.: Create the repository to keep track of the optimal alignments
        AlignmentRepository alignmentRepository = new AlignmentRepository(startingAlignment);

        // STEP 6.: Read in the team file
        ArrayList<BioInformatician> team = new ArrayList<>();
        ArrayList<TeamLeader> leaders = new ArrayList<>();
        ArrayList<TechnicalSupport> supports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/" + properties.getProperty("teamfilename")))) {
            String line = reader.readLine();
            // STEP 7.: Team creation
            while (line != null) {
                String[] lineComponents = line.split(" ");
                switch (lineComponents[0]) {
                    case "TeamLead":
                        TeamLeader teamLeader = new TeamLeader(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), alignmentRepository);
                        teamLeader.setTeam(team);
                        leaders.add(teamLeader);
                        break;
                    case "Bioinformatician":
                        BioInformatician bioInformatician = new BioInformatician(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), startingAlignment);
                        team.add(bioInformatician);
                        break;
                    case "TechnicalSupport":
                        TechnicalSupport technicalSupport = new TechnicalSupport(lineComponents[1], lineComponents[2], Integer.parseInt(lineComponents[3]), alignmentRepository);
                        supports.add(technicalSupport);
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
        System.out.println("The team consists of: ");
        leaders.forEach(leader -> System.out.println(leader.toString()));
        team.forEach(bioInformatician -> System.out.println(bioInformatician.toString()));
        supports.forEach(support -> System.out.println(support.toString()));


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
