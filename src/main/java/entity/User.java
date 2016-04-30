package entity;

import java.util.*;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Image image;
    private Role role;
    private String degree;
    private List<TechSkill> techSkills;
    private List<Role> roleValues;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new NullPointerException("Attempt to set null to firstName");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Attempt to set null to lastName");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Attempt to set null to email");
        }
        this.email = email;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        if (image == null) {
            throw new NullPointerException("Attempt to set null to image");
        }
        this.image = image;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        if (degree == null) {
            throw new NullPointerException("Attempt to set null to degree");
        }
        this.degree = degree;
    }

    public List<TechSkill> getTechSkills() {
        if (techSkills == null) {
            throw new NullPointerException("Trying to getTechSkills() from a null techSkills");
        }
        return this.techSkills;
    }

    public boolean addTechSkill(TechSkill techSkill) {
        if (techSkills == null) {
            techSkills = new ArrayList<>();
        }
        return this.techSkills.add(techSkill);
    }

    public boolean removeTechSkill(TechSkill techSkill) {
        if (techSkills == null) {
            throw new NullPointerException("Trying to removeTechSkill() from a null techSkills");
        }
        return this.techSkills.remove(techSkill);
    }

    public void clearTechSkills() {
        this.techSkills.clear();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleValues() {
        return Arrays.asList(Role.values());
    }

    public enum Role {
        ADMIN,
        MANAGER,
        SENIOR,
        ENGINEER,
        GUEST
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
