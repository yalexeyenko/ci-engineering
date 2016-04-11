package entity;

public class Legal extends Client {
    private String name;
    private String ein;

    public Legal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Attempt to set null to name");
        }
        this.name = name;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        if (ein == null) {
            throw new NullPointerException("Attempt to set null to ein");
        }
        this.ein = ein;
    }

    @Override
    public String toString() {
        return "Legal{" +
                "name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
