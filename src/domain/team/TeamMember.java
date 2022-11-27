package domain.team;

// Represents a member of the team, found in the team.txt file
public abstract class TeamMember {
    // Represents the first name
    protected String firstName;
    // Represents the last name
    protected String lastName;
    // Indicates how long the team member is part of the team
    protected int yearsOfExperience;

    public TeamMember(String firstName, String lastName, int yearsOfExperience) {
        setFirstName(firstName);
        setLastName(lastName);
        setYearsOfExperience(yearsOfExperience);
    }

    /**
     * Sets the first name of the team member
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the team member
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the years of experience within the team of the team member
     *
     * @param yearsOfExperience years of experience
     */
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Gets the full name of the TeamMember
     *
     * @return the concatination of firstName and lastName
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * A pleasing String representation of the class TeamMember
     *
     * @return the String representation of TeamMember
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " has " + yearsOfExperience + " years of experience in the team.";
    }
}
