package domain;

// Represents a member of the team, found in the team.txt file
public class TeamMember {
    // Specifies the role within the team
    private TeamRole role;
    // Represents the first name
    private String firstName;
    // Represents the last name
    private String lastName;
    // Indicates how long the team member is part of the team
    private int yearsOfExperience;

    public TeamMember(TeamRole role, String firstName, String lastName, int yearsOfExperience) {
        setRole(role);
        setFirstName(firstName);
        setLastName(lastName);
        setYearsOfExperience(yearsOfExperience);
    }

    /**
     * Transforms a String input string into the corresponding TeamRole
     *
     * @param role string representation of the team role
     * @return the corresponding TeamRole
     */
    public static TeamRole getRoleFromString(String role) {
        switch (role) {
            case "TeamLead":
                return TeamRole.TeamLead;
            case "Bioinformatician":
                return TeamRole.Bioinformatician;
            case "TechnicalSupport":
                return TeamRole.TechnicalSupport;
            default:
                return TeamRole.Unknown;
        }
    }

    public void setRole(TeamRole role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
