package entity;

public abstract class NamedEntity extends BaseEntity {
    private String name;

    public NamedEntity() {
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

    @Override
    public String toString() {
        return "name=" + name;
    }
}
