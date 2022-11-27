package startup;

import domain.Genome;
import domain.alignment.SNPAlignment;
import domain.alignment.StandardAlignment;
import domain.team.BioInformatician;
import domain.team.TeamLeader;
import domain.team.TechnicalSupport;
import repositories.AlignmentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class StartUp {
    public static void main(String[] args) {
        Random rm = new Random();
        // Step 0.: Read in the properties
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/config.properties")) {
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
        StandardAlignment startingAlignment = new StandardAlignment();
        SNPAlignment startingSNPAlignment = new SNPAlignment();
        // STEP 2.: Read in the fasta file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/" + properties.getProperty("fastafilename")))) {
            // STEP 3.: Create genome objects from the fasta file and add them to the alignment objects
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
            startingSNPAlignment.setGenomes(genomes);
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
        System.out.printf("The starting alignment in standard format is: %n%sWith a difference score of: %n%d%n%nIn SNP format %sWith a difference score of: %n%d%n",
                startingAlignment.getRepresentation(), startingAlignment.calculateScore(), startingSNPAlignment.getRepresentation(), startingSNPAlignment.calculateScore());

        System.out.println("\n================================================\n");

        // STEP 5.: Create the repository to keep track of the optimal alignments
        AlignmentRepository alignmentRepository = new AlignmentRepository(startingAlignment, startingSNPAlignment);

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
        System.out.println();

        System.out.println("\n================================================\n");

        //TODO: make this less hard coded or explain why this part is hard coded

        // STEP 8.: Mac works on his alignment and calculates the score
        BioInformatician b1 = team.get(0);
        System.out.println(b1.getName() + " starts working on his alignment");
        System.out.println("Difference score before operation:: " + b1.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Changing character 'C' to 'T' for all alignments.");
        b1.getPersonalAlignment().replaceAllCharacters('C', 'T');
        System.out.println("\nDifference score after operation:: " + b1.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 9.: Werner works on his alignment and calculates the score
        BioInformatician b2 = team.get(1);
        System.out.println(b2.getName() + " starts working on his alignment");
        System.out.println("The difference score before operations:: " + b2.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Find genomes with sequence 'GACAGACCAA' in the genome sequence.");
        ArrayList<String> foundIdentifiers = b2.getPersonalAlignment().searchForGenomes("GACAGACCAA");
        System.out.printf("The found genome%s:%n", foundIdentifiers.size() > 1 ? "s are" : " is");
        foundIdentifiers.forEach(System.out::println);
        int index = rm.nextInt(foundIdentifiers.size());
        String randomId = foundIdentifiers.get(index);
        System.out.println("Randomly picking one and replacing the genome sequence: " + randomId);
        b2.getPersonalAlignment().replaceGenomeSequence(randomId, "TTTCCTGGGGACAGACCAACGCTAAGGCCGCCCCATAAACCTGTCCGCGCGGAACGCAAATTAGCGCAGAACCACATGAGACCCAAGCATGCTTCAGACGTCCATAATGCAGGAGAGTCAGGCAGTCTGACATATTAGAAAACTACTTTTATCTTTGCGTAGCGTGCACAGTGAATAACATACCGTAAACAAATTGGAAGAAGGGCGTTTAGAAGCAGTATACCCCTGCTGATCCTCTACCCCTCAACAAGTCACGGATAACCGCGCGCTGGACTCCACTATCCGGCAGGAGGACCTACCGAAACCACGCTAGTATAGGTATAGTAGCAAAGCACGTACCGAACTGCCTAATTACGCCCAACGCCGCCCTATAAATGTAGTCCGATACCCCCGCGGGCCCCTACCGCCCTGACATACATTCTACTTTTTATCCTGATCCACCAACGAGCCGCAAACAGAGCTAAGGCAGTATCAGATTTCAAAAACCTGCCGCCAAGTAGTTGTCAATCCCCTGGGTGTAATTGGTCCCTTCACTGCTCTTGAATAACACTAAAACGCGTGCCGACCCGCCCCTGCGACACGAAACAGCTTCGACCCGCAATCAACACATTGCCTAACGAGCCGCCGGAAAAATTGAACACATGCCACGACAAATTGCGTCGAACCTCGAGTTAACACATCCCCACACGTCTGACAACGCCAATCGCGCGACCCACGCCGGAGCGACAATTGAACAAAAGTACGACCAGGGAGTTGCGGCAAGACCGTCCTCCGTACGCAAGGTGACATAGCCGATACCACAGAAATACTTCCTCATAGAGTAAAATTCAATATGGCGGCAACTGTTGCCGATATTGAGATTACGATGACGATACAATCTGACACGGATAGAGTGCCCCACAACGCAGCTACACAACCCAATATATTACCACGACAGAAATACTATTATCTCAAGTACGCCGACCTTCATCCCAAAGCGCTTCGGAGAAGGGTCACGCCATGGGTCCAAGAGTTCCACCCGCAATAGATGTGACAATGACCCAATATATCCAGTCTACACTGAAACCAGGCTAACCCGTTCAAATACCCGATGTGACTCTGCCCTGATTGAAGGGCGCGGGCCTACTTTGCAAGTATCGACAACACCGGACTCCGACAGGTACATTGCCAGAGCCAGTTGAGAATCCATGAAGTACACGGGTCCGGCGGTGCACCATAGCAATCGAACTAAATGGACCCCCAAAGGATTAGTCCTAACACTCATTACATCTGTTGATAGGGCGCATACAACCACCTGGCGGCTAATAAACATAAACGAAACAACTGCGCGGCCGCAAAGCCAAAGACTGAAGAGCAGAAAGGGCATAATACGTCGACGATGAGCGATCGCACCTCAGACTAGGCAAAAAACCACTATCATAAATCGAGTCCTGACAAGACCCTACCCGCTCGCGCAAACACTGTGAAGACATATAGGATATCTACAAAAGCACATTCCTCTTCACATACACCCACCAGGGTATCTCTTTCATACATACCATTTTCAGGCGCGGCCGGTACCGTAAATGTTCCCCTACGCTGCGAATCCTGCGTCCCCGGCGGGCCCCTCCTACAAATCGGACACACATATGACACCCGGTTCTAAGAGCTCTCCATAGGAAATGGAGCTACGTACCATCGGCTCGTCAACGGCTCTCACTGAACATAAGGCCGGATATTGAAGCTTGAGGTACCCCAGTTGCAGACCGACAATCATAGAATCAACACAGAACCTCTGACGAGGAATATAACGGCATGGCTGTAGCACTAGCATGTCCGCAGCATGCCTGCAGTAGGTCAAATACAATCATCAGGCCGCCGTTAGGCGCAAAGTCTGCAGGAACCCAAGCCAGTCCCTCGGGTAGACATCACTAGAATTTCCCCTCCTGCTATAAAAGTCGATCTGCAATTCCCGCTAGACCATCGAGCTAAACACTCGCCATGCAATAGTCTCCCACGCAATTCAAACTCAGCACTACGGGCCCCAACCCGACATCCCCGCTCGTCAGGCGCTTCGAGCGCTCCTAGCCTCGATTCCCAATGCAGGAGCCGGAGACAAACACATGATCGACAAAGCTTACTCCAAAACGATTCCTTCCACTGAACCGCGCCGCCAACTCGATTGCCAAAGCCGAACAGCTGGACAAAGCAGTAGTATAAACGGTGGCCAGCCTCAGTCACATCACTATCCTGTACCACCCCGCCTCTACAACATCGCCCGTCACTAATACCGCCCATCTACCGTCTAATTAGTCCACAAAATGACCGGCTGTGGACCCCTGTCACTCAGCTAAGCTCGTACGCCACGAAGCATGACCATCTAGACCAATGGTACGGCTCCCCCTGAGTTTTTTCCTCTTCCACCTTTCCGTTTAGCGGCTGGCCTCGATGCTACAAATATTATTATCAGCAGCTCTTATACCACCACTAGTAAATACCAACGGTATACAAGGCCGCCGGT");
        System.out.println("\nDifference score after operation:: " + b2.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 10.: Yves works on his alignment and calculates the score
        BioInformatician b3 = team.get(2);
        System.out.println(b3.getName() + " starts working on his alignment");
        System.out.println("The difference score before operations:: " + b3.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Find the genome sequence with identifier: >1986.B.US.86.AD87");
        Genome foundGenome = b3.getPersonalAlignment().findGenome(">1986.B.US.86.AD87");
        if (foundGenome != null) {
            System.out.println("The found genome: " + foundGenome.toString());
            System.out.println("For this found genome, replace all occurrences of the character 'A' with 'G");
            // This is a bit useless but to show off what my program can do, I'll still use the method
            b3.getPersonalAlignment().replaceCharacterForGenome(foundGenome.getIdentifier(), 'A', 'G');
        } else {
            System.out.println("No genome found with that identifier!");
        }
        System.out.println("Removing genome with identifier >1993.C.IN.93.93IN101");
        Genome removedGenome = b3.getPersonalAlignment().removeGenome(">1993.C.IN.93.93IN101");
        if (removedGenome != null) {
            System.out.println("The removed genome has properties " + removedGenome.toString());
        }
        System.out.println("Adding a new genome");
        b3.getPersonalAlignment().addGenome(">1993.C.IN.93.93IN102", "TTTCTTGGGGACAGACCAACGCTGAGGCCACCCGATAAACCCGTCCGCGCGGGACCCAAATTAACGCAGCACCGCATGAGACCCAATAGTGCTTCAAACGTCCATACTGCAGGAGAGTCAGGCAGCCTGACATACTAAAAGCCTACATCTAGCTTTGCGTAGCGCGAACAGTCAATAACATACCGTAAACAGGTTGCAAGACGGGCGTTTAGAAGCAGTATACTCGTGTTGATCATCCACCCCTCAACAAATAACGGGCAACTGCGGGCTGGACTTCACCATCCGGCAAGAGGGCCTGCCGTAACGACGCTAGTATAGGTATCATAGCAAAGCACGTACTGGACAGCCTAATTGCGCCCAACGCCACCCTATAGACCTAGTCCGGTACCCCCGCGAGCCCCTACCGCCCTGGCATACATCTGAGTTTCTATCCTGGTCCACGAAGGAGCGGCACACAGAGCTAGGGCAGTAGCAGACTCCAAAAGCCCGGCGCCAAGTAGCAGTTCATCCCACGGGCGTAATAGGTTCCTTCTCGGCTCTCGAATAACACTAAAACGCGTGCTGACCCCGCCCCGCGACACGAAACAGCTTTAACCCGGCATAATCACACTGCCTAACGAGCCGCCGGAAAAATTGAACAAACGCCACAACAAATTGTGTCGAACCTCGAGCTCACACATCCTTACGCGTCCGACAGTGCCGATCGCGCGAACTACGCCATAGCGGCAATTAAGCAAATGTACGACCAGGGAGTTGCGGCAAGCCCGCCCTACATAAGCAATGTGAAACGGACAATACCAAAGATCTATTTCCTCATAGAACGAAATTTGATATGGCGGCAACTGTCGCCGATATCTACAATACGATGACGATATAATCTGACACGGATAGAGTGCCCCACAACGCAGCTCCACAACTCAATATATTACCACGACAGGGATACTACTACCTTAAGTAGGCCGACCTTTCTCGCAAAGCGCTTCGGAGAAAGGGCACGCCACGGATCCAAAAATTACGTCCGCCATGAATGTAACCACGACCCAATCTATCCAGTCTACACTGAAACCAGGCTAACCTGTTTTAATACTCGACGAGACTCTGCCCTGATCGAAAGACGTGGGCGCACTCTGTAGGTATCGACATCACTAGACTCCGACACGTAAGTTGCCAGAGTAAGTTGAGAACCCATGAAGTACCCGGGTCCGGCGGTGCACCATAGCGATCGAATTAAATGCACCCCAAGAGGATCAGTCCTAACACCCATTACATCCGTTGATAACGCGCACAAAACCATCTGGCGGCTAGAAAACATAGAGAAGACGAGTACGCGGCCGCAAAGCCAAGGAATGATGAGCAGAAAGGCCATAATACGTTGGCCACGAGCAATCGCACCCCAGGCGACGCTAGGAACCACTATCGTAAGTCGGGTTCTGACAAGACCCGACCCGCTCGCGAAAACACTATGAAAACACATAGGATATTTACAAAAGTACATTCCTCTTCGCCTACACCAACCAGAGTATATCCTTCATACACACGATGTTCAGGCGAGCCCGGTACCTTAGAGGTTCCCCTATGCTGCGAATTCTGCGTACCCAGCGGACTCCTCCTACAGATTGGATACACATATGACACCCGGTTCTAAGAGCCCTCCATAGAAAATGGAGCTACTCACTATTGGCTCGTCAACAGCTCTCACTGAACAAAAGGCCGGATATCGAAGCTTGAGGTACTCCAATAGCAACCTGACAACCATAGAATCGACGCAGCGCGTCCGACGAGGAATATCACGGCCTGGCGGTAGCACTAGCATGACCGCAGCATGACTGCAGTAGCTCAAATACTATCAACAGACCGCCGTCAGGCGCAAGTTCCGCAAGAACCCAAACTTGTCCCTAGGGTAGGCGTCAATAGAATTTCTCCTCCTGCTATAAAAATCGATCTGTAATTCCCGCTAGACCATCGAGCTAGACACTCGCGTTCCAATAGTGTCAGACGCAATTCAAACTCAGTACTACGAGCCCCAACCCGAAATACCCGCTTGTCAGGTGCTTTGACCGTTCCTAATCTCAATTCCCAATCCAGGAGCCGGAGACAAACCCATGCTCGACAAAAATTACTCCAAATCGATTCCTTCCACTGAACCCCGTCACCACCTCGATCATCAAAGGCGGACAGCTGGACAAAACAGTAGTTTAAGCGGTAGTCAGCCTCAATCACATTACTATCCTGTGCCACCCCGCCTCTACAGCATCGCCCGCCACCAATACCGCACATCTACAGCCTAACTAGTCCACGATATGATCGACTGTGGACACCTAGCAGCTAGCTAAGTTCGTACGCCACGCAGCACGACCATCTAAACCAATGGTACGGGTTCCCTTGCGTTTTGTCACCTCCCACCCTACCGTCTAGCGAATGGCCTCGCTGCTACGCATATAATTCTCGGCAACTCTCATTCCACCTCCAGTAAATACCTACTATATACAAACCCGCAGGG");
        System.out.println("\nDifference score after these operations:: " + b3.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 11.: Write away best alignment to repository
        TeamLeader leader = leaders.get(0);
        System.out.println(leader.getName() + " starts managing the team");
        StandardAlignment bestAlignment = startingAlignment;
        System.out.println("Difference score of the best alignment:: " + leader.getOptimalDifferenceScore());
        System.out.println("Calculating best alignment...");
        for (BioInformatician bioInofrmatician : team) {
            if (bestAlignment.calculateScore() > bioInofrmatician.getPersonalAlignment().calculateScore()) {
                bestAlignment = bioInofrmatician.getPersonalAlignment();
            }
        }
        System.out.println("The lowest difference score found is " + bestAlignment.calculateScore());
        System.out.println("Writing best alignment to repository");
        leader.copyAlignmentToOptimalAlignment(bestAlignment);
        System.out.println("Difference score of the best alignment:: " + leader.getOptimalDifferenceScore());

        System.out.println("\n================================================\n");

        // STEP 12.: Make a backup of the repository and show timestamp of latest backup made
        TechnicalSupport support = supports.get(0);
        if (support.getLastBackup() != null) {
            System.out.println("The last backup was at " + support.getLastBackup().toString());
        } else {
            System.out.println("No backup has been made yet");
        }
        System.out.println("Backing up the repository");
        support.backupRepository(team);
        if (support.getLastBackup() != null) {
            System.out.println("Backup successfully made at " + support.getLastBackup().toString());
        } else {
            System.out.println("Something went wrong when backing up, no backup has been made yet.");
        }

        // STEP 13.: Flushing the repository and showing that it's empty
        // STEP 14.: Restoring the repository and the team's alignments
        // STEP 15.: Updating each team member to match the best alignment
        // STEP 16.: Write away the alignment of BioInformatician 1
        // STEP 17.: Write away the alignment of TeamLeader 1
        // STEP 18.: Write away the score of BioInformatician 2
        // STEP 19.: Write away the score of TeamLeader 1
    }
}
