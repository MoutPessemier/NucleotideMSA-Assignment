package domain;

// Represents a member of the team, found in the team.txt file
public abstract class TeamMember {
    // Represents the first name
    private String firstName;
    // Represents the last name
    private String lastName;
    // Indicates how long the team member is part of the team
    private int yearsOfExperience;

    public TeamMember(String firstName, String lastName, int yearsOfExperience) {
        setFirstName(firstName);
        setLastName(lastName);
        setYearsOfExperience(yearsOfExperience);
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
