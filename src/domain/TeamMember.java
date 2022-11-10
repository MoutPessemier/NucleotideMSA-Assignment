package domain;

public class TeamMember {
    private TeamRole role;
    private String firstName;
    private String lastName;
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
