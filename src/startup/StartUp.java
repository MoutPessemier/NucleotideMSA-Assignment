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
        // STEP 0.: Declare needed variables
        Random rm = new Random();
        int index;

        // Step 1.: Read in the properties
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
        // STEP 2.: Creating the starting alignments
        StandardAlignment startingAlignment = new StandardAlignment();
        SNPAlignment startingSNPAlignment = new SNPAlignment();
        // STEP 3.: Read in the fasta file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/" + properties.getProperty("fastafilename")))) {
            // STEP 4.: Create genome objects from the fasta file and add them to the alignment objects
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

        // STEP 5.: Print the starting alignments by calling getRepresentation()
        System.out.printf("The starting alignment in standard format is: %n%sWith a difference score of: %n%d%n%nIn SNP format %sWith a difference score of: %n%d%n",
                startingAlignment.getRepresentation(), startingAlignment.calculateScore(), startingSNPAlignment.getRepresentation(), startingSNPAlignment.calculateScore());

        System.out.println("\n================================================\n");

        // STEP 6.: Create the repository to keep track of the optimal alignments
        AlignmentRepository alignmentRepository = new AlignmentRepository(startingAlignment, startingSNPAlignment);

        // STEP 7.: Read in the team file
        ArrayList<BioInformatician> team = new ArrayList<>();
        ArrayList<TeamLeader> leaders = new ArrayList<>();
        ArrayList<TechnicalSupport> supports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/files/" + properties.getProperty("teamfilename")))) {
            String line = reader.readLine();
            // STEP 8.: Team creation
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

        // STEP 9.: Display the whole team
        System.out.println("The team consists of: ");
        leaders.forEach(leader -> System.out.println(leader.toString()));
        team.forEach(bioInformatician -> System.out.println(bioInformatician.toString()));
        supports.forEach(support -> System.out.println(support.toString()));
        System.out.println();

        System.out.println("\n================================================\n");

        // STEP 10.: To work with different sized teams and to not hard code which bio-informatician gets to do what, the 3 scenarios in which the bio-informatician works, he will be randomly selected
        BioInformatician b = team.get(rm.nextInt(team.size()));

        // STEP 11.: A BioInformatician works on his alignment and calculates the score
        System.out.println(b.getName() + " starts working on his alignment");
        System.out.println("Difference score before operation:: " + b.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Changing character 'T' to 'C' for all alignments.");
        b.getPersonalAlignment().replaceAllCharacters('T', 'C');
        System.out.println("\nDifference score after operation:: " + b.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 12.: Write away the alignment of a BioInformatician
        System.out.println(b.getName() + " writes away his alignment to a file");
        b.writeDataToFile();

        System.out.println("\n================================================\n");

        // STEP 13.: A BioInformatician works on his alignment and calculates the score
        b = team.get(rm.nextInt(team.size()));
        System.out.println(b.getName() + " starts working on his alignment");
        System.out.println("The difference score before operations:: " + b.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Find genomes with sequence 'GACAGACCAA' in the genome sequence.");
        ArrayList<String> foundIdentifiers = b.getPersonalAlignment().searchForGenomes("GACAGACCAA");
        System.out.printf("The found genome%s:%n", foundIdentifiers.size() > 1 ? "s are" : " is");
        foundIdentifiers.forEach(System.out::println);
        index = rm.nextInt(foundIdentifiers.size());
        String randomId = foundIdentifiers.get(index);
        System.out.println("Randomly picking one and replacing the genome sequence: " + randomId);
        b.getPersonalAlignment().replaceGenomeSequence(randomId, "TTTCCTGGGGACAGACCAACGCTAAGGCCGCCCCATAAACCTGTCCGCGCGGAACGCAAATTAGCGCAGAACCACATGAGACCCAAGCATGCTTCAGACGTCCATAATGCAGGAGAGTCAGGCAGTCTGACATATTAGAAAACTACTTTTATCTTTGCGTAGCGTGCACAGTGAATAACATACCGTAAACAAATTGGAAGAAGGGCGTTTAGAAGCAGTATACCCCTGCTGATCCTCTACCCCTCAACAAGTCACGGATAACCGCGCGCTGGACTCCACTATCCGGCAGGAGGACCTACCGAAACCACGCTAGTATAGGTATAGTAGCAAAGCACGTACCGAACTGCCTAATTACGCCCAACGCCGCCCTATAAATGTAGTCCGATACCCCCGCGGGCCCCTACCGCCCTGACATACATTCTACTTTTTATCCTGATCCACCAACGAGCCGCAAACAGAGCTAAGGCAGTATCAGATTTCAAAAACCTGCCGCCAAGTAGTTGTCAATCCCCTGGGTGTAATTGGTCCCTTCACTGCTCTTGAATAACACTAAAACGCGTGCCGACCCGCCCCTGCGACACGAAACAGCTTCGACCCGCAATCAACACATTGCCTAACGAGCCGCCGGAAAAATTGAACACATGCCACGACAAATTGCGTCGAACCTCGAGTTAACACATCCCCACACGTCTGACAACGCCAATCGCGCGACCCACGCCGGAGCGACAATTGAACAAAAGTACGACCAGGGAGTTGCGGCAAGACCGTCCTCCGTACGCAAGGTGACATAGCCGATACCACAGAAATACTTCCTCATAGAGTAAAATTCAATATGGCGGCAACTGTTGCCGATATTGAGATTACGATGACGATACAATCTGACACGGATAGAGTGCCCCACAACGCAGCTACACAACCCAATATATTACCACGACAGAAATACTATTATCTCAAGTACGCCGACCTTCATCCCAAAGCGCTTCGGAGAAGGGTCACGCCATGGGTCCAAGAGTTCCACCCGCAATAGATGTGACAATGACCCAATATATCCAGTCTACACTGAAACCAGGCTAACCCGTTCAAATACCCGATGTGACTCTGCCCTGATTGAAGGGCGCGGGCCTACTTTGCAAGTATCGACAACACCGGACTCCGACAGGTACATTGCCAGAGCCAGTTGAGAATCCATGAAGTACACGGGTCCGGCGGTGCACCATAGCAATCGAACTAAATGGACCCCCAAAGGATTAGTCCTAACACTCATTACATCTGTTGATAGGGCGCATACAACCACCTGGCGGCTAATAAACATAAACGAAACAACTGCGCGGCCGCAAAGCCAAAGACTGAAGAGCAGAAAGGGCATAATACGTCGACGATGAGCGATCGCACCTCAGACTAGGCAAAAAACCACTATCATAAATCGAGTCCTGACAAGACCCTACCCGCTCGCGCAAACACTGTGAAGACATATAGGATATCTACAAAAGCACATTCCTCTTCACATACACCCACCAGGGTATCTCTTTCATACATACCATTTTCAGGCGCGGCCGGTACCGTAAATGTTCCCCTACGCTGCGAATCCTGCGTCCCCGGCGGGCCCCTCCTACAAATCGGACACACATATGACACCCGGTTCTAAGAGCTCTCCATAGGAAATGGAGCTACGTACCATCGGCTCGTCAACGGCTCTCACTGAACATAAGGCCGGATATTGAAGCTTGAGGTACCCCAGTTGCAGACCGACAATCATAGAATCAACACAGAACCTCTGACGAGGAATATAACGGCATGGCTGTAGCACTAGCATGTCCGCAGCATGCCTGCAGTAGGTCAAATACAATCATCAGGCCGCCGTTAGGCGCAAAGTCTGCAGGAACCCAAGCCAGTCCCTCGGGTAGACATCACTAGAATTTCCCCTCCTGCTATAAAAGTCGATCTGCAATTCCCGCTAGACCATCGAGCTAAACACTCGCCATGCAATAGTCTCCCACGCAATTCAAACTCAGCACTACGGGCCCCAACCCGACATCCCCGCTCGTCAGGCGCTTCGAGCGCTCCTAGCCTCGATTCCCAATGCAGGAGCCGGAGACAAACACATGATCGACAAAGCTTACTCCAAAACGATTCCTTCCACTGAACCGCGCCGCCAACTCGATTGCCAAAGCCGAACAGCTGGACAAAGCAGTAGTATAAACGGTGGCCAGCCTCAGTCACATCACTATCCTGTACCACCCCGCCTCTACAACATCGCCCGTCACTAATACCGCCCATCTACCGTCTAATTAGTCCACAAAATGACCGGCTGTGGACCCCTGTCACTCAGCTAAGCTCGTACGCCACGAAGCATGACCATCTAGACCAATGGTACGGCTCCCCCTGAGTTTTTTCCTCTTCCACCTTTCCGTTTAGCGGCTGGCCTCGATGCTACAAATATTATTATCAGCAGCTCTTATACCACCACTAGTAAATACCAACGGTATACAAGGCCGCCGGT");
        System.out.println("\nDifference score after operation:: " + b.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 14.: Write away the score of a BioInformatician
        System.out.println(b.getName() + " writes away his alignment to a file");
        b.writeReportToFile();

        System.out.println("\n================================================\n");

        // STEP 15.: A BioInformatician works on his alignment and calculates the score
        b = team.get(rm.nextInt(team.size()));
        System.out.println(b.getName() + " starts working on his alignment");
        System.out.println("The difference score before operations:: " + b.getPersonalAlignment().calculateScore() + "\n");
        System.out.println("Find the genome sequence with identifier: >1986.B.US.86.AD87");
        Genome foundGenome = b.getPersonalAlignment().findGenome(">1986.B.US.86.AD87");
        if (foundGenome != null) {
            System.out.println("The found genome: " + foundGenome.toString());
            System.out.println("For this found genome, replace all occurrences of the character 'A' with 'G");
            // This is a bit useless but to show off what my program can do, I'll still use the method
            b.getPersonalAlignment().replaceCharacterForGenome(foundGenome.getIdentifier(), 'A', 'G');
        } else {
            System.out.println("No genome found with that identifier!");
        }
        System.out.println("Removing genome with identifier >1993.C.IN.93.93IN101");
        Genome removedGenome = b.getPersonalAlignment().removeGenome(">1993.C.IN.93.93IN101");
        if (removedGenome != null) {
            System.out.println("The removed genome has properties " + removedGenome.toString());
        }
        System.out.println("Adding a new genome");
        b.getPersonalAlignment().addGenome(">1993.C.IN.93.93IN102", "TTTCTTGGGGACAGACCAACGCTGAGGCCACCCGATAAACCCGTCCGCGCGGGACCCAAATTAACGCAGCACCGCATGAGACCCAATAGTGCTTCAAACGTCCATACTGCAGGAGAGTCAGGCAGCCTGACATACTAAAAGCCTACATCTAGCTTTGCGTAGCGCGAACAGTCAATAACATACCGTAAACAGGTTGCAAGACGGGCGTTTAGAAGCAGTATACTCGTGTTGATCATCCACCCCTCAACAAATAACGGGCAACTGCGGGCTGGACTTCACCATCCGGCAAGAGGGCCTGCCGTAACGACGCTAGTATAGGTATCATAGCAAAGCACGTACTGGACAGCCTAATTGCGCCCAACGCCACCCTATAGACCTAGTCCGGTACCCCCGCGAGCCCCTACCGCCCTGGCATACATCTGAGTTTCTATCCTGGTCCACGAAGGAGCGGCACACAGAGCTAGGGCAGTAGCAGACTCCAAAAGCCCGGCGCCAAGTAGCAGTTCATCCCACGGGCGTAATAGGTTCCTTCTCGGCTCTCGAATAACACTAAAACGCGTGCTGACCCCGCCCCGCGACACGAAACAGCTTTAACCCGGCATAATCACACTGCCTAACGAGCCGCCGGAAAAATTGAACAAACGCCACAACAAATTGTGTCGAACCTCGAGCTCACACATCCTTACGCGTCCGACAGTGCCGATCGCGCGAACTACGCCATAGCGGCAATTAAGCAAATGTACGACCAGGGAGTTGCGGCAAGCCCGCCCTACATAAGCAATGTGAAACGGACAATACCAAAGATCTATTTCCTCATAGAACGAAATTTGATATGGCGGCAACTGTCGCCGATATCTACAATACGATGACGATATAATCTGACACGGATAGAGTGCCCCACAACGCAGCTCCACAACTCAATATATTACCACGACAGGGATACTACTACCTTAAGTAGGCCGACCTTTCTCGCAAAGCGCTTCGGAGAAAGGGCACGCCACGGATCCAAAAATTACGTCCGCCATGAATGTAACCACGACCCAATCTATCCAGTCTACACTGAAACCAGGCTAACCTGTTTTAATACTCGACGAGACTCTGCCCTGATCGAAAGACGTGGGCGCACTCTGTAGGTATCGACATCACTAGACTCCGACACGTAAGTTGCCAGAGTAAGTTGAGAACCCATGAAGTACCCGGGTCCGGCGGTGCACCATAGCGATCGAATTAAATGCACCCCAAGAGGATCAGTCCTAACACCCATTACATCCGTTGATAACGCGCACAAAACCATCTGGCGGCTAGAAAACATAGAGAAGACGAGTACGCGGCCGCAAAGCCAAGGAATGATGAGCAGAAAGGCCATAATACGTTGGCCACGAGCAATCGCACCCCAGGCGACGCTAGGAACCACTATCGTAAGTCGGGTTCTGACAAGACCCGACCCGCTCGCGAAAACACTATGAAAACACATAGGATATTTACAAAAGTACATTCCTCTTCGCCTACACCAACCAGAGTATATCCTTCATACACACGATGTTCAGGCGAGCCCGGTACCTTAGAGGTTCCCCTATGCTGCGAATTCTGCGTACCCAGCGGACTCCTCCTACAGATTGGATACACATATGACACCCGGTTCTAAGAGCCCTCCATAGAAAATGGAGCTACTCACTATTGGCTCGTCAACAGCTCTCACTGAACAAAAGGCCGGATATCGAAGCTTGAGGTACTCCAATAGCAACCTGACAACCATAGAATCGACGCAGCGCGTCCGACGAGGAATATCACGGCCTGGCGGTAGCACTAGCATGACCGCAGCATGACTGCAGTAGCTCAAATACTATCAACAGACCGCCGTCAGGCGCAAGTTCCGCAAGAACCCAAACTTGTCCCTAGGGTAGGCGTCAATAGAATTTCTCCTCCTGCTATAAAAATCGATCTGTAATTCCCGCTAGACCATCGAGCTAGACACTCGCGTTCCAATAGTGTCAGACGCAATTCAAACTCAGTACTACGAGCCCCAACCCGAAATACCCGCTTGTCAGGTGCTTTGACCGTTCCTAATCTCAATTCCCAATCCAGGAGCCGGAGACAAACCCATGCTCGACAAAAATTACTCCAAATCGATTCCTTCCACTGAACCCCGTCACCACCTCGATCATCAAAGGCGGACAGCTGGACAAAACAGTAGTTTAAGCGGTAGTCAGCCTCAATCACATTACTATCCTGTGCCACCCCGCCTCTACAGCATCGCCCGCCACCAATACCGCACATCTACAGCCTAACTAGTCCACGATATGATCGACTGTGGACACCTAGCAGCTAGCTAAGTTCGTACGCCACGCAGCACGACCATCTAAACCAATGGTACGGGTTCCCTTGCGTTTTGTCACCTCCCACCCTACCGTCTAGCGAATGGCCTCGCTGCTACGCATATAATTCTCGGCAACTCTCATTCCACCTCCAGTAAATACCTACTATATACAAACCCGCAGGG");
        System.out.println("\nDifference score after these operations:: " + b.getPersonalAlignment().calculateScore());

        System.out.println("\n================================================\n");

        // STEP 16.: Write away best alignment to repository
        // Here the same principle, multiple team leaders could be present, and we only want one
        TeamLeader leader = leaders.get(rm.nextInt(leaders.size()));
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

        // STEP 17.: Write away the alignment of a TeamLeader
        System.out.println(leader.getName() + " writes away the alignments for his team");
        leader.writeDataToFile();

        System.out.println("\n================================================\n");

        // STEP 18.: Write away the score of TeamLeader 1
        System.out.println(leader.getName() + " writes away the reports for his team");
        leader.writeReportToFile();

        System.out.println("\n================================================\n");

        // STEP 19.: Updating each team member to match the best alignment
        System.out.println(leader.getName() + " updates the alignments for all his team members to match the optimal alignment");
        team.forEach(leader::overwriteAlignment);

        team.forEach(bioInformatician -> System.out.printf("%s has now a difference score of %d%n", bioInformatician.getName(), bioInformatician.getPersonalAlignment().calculateScore()));

        System.out.println("\n================================================\n");

        // STEP 20.: Make a backup of the repository and show timestamp of latest backup made
        // Same principle here, we want a support but don't want to hardcode it for the scenarios where others are available as well
        TechnicalSupport support = supports.get(rm.nextInt(supports.size()));
        System.out.println(support.getName() + " starts managing the repository");
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

        System.out.println("\n================================================\n");

        /*// STEP 21.: Flushing the repository and showing that it's empty
        support.clearRepository(team);

        System.out.println("\n================================================\n");

        // STEP 22.: Restoring the repository and the team's alignments
        */
    }
}
