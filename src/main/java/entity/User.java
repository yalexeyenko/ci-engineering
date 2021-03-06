package entity;

import java.util.*;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String degree;
    private List<Role> roleValues;
    private String roleName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleValues() {
        return Arrays.asList(Role.values());
    }

    public String getRoleName() {
        return role.toString();
    }

    public enum Role {
        ADMIN{
            @Override
            public String toString() {
                return "Администратор";
            }
        },
        MANAGER{
            @Override
            public String toString() {
                return "Менеджер";
            }
        },
        SENIOR{
            @Override
            public String toString() {
                return "Главный инженер";
            }
        },
        ENGINEER{
            @Override
            public String toString() {
                return "Инженер";
            }
        },
        REGISTERED{
            @Override
            public String toString() {
                return "Зарегистрированный";
            }
        },
        UNREGISTERED{
            @Override
            public String toString() {
                return "Незарегистрированный";
            }
        };

        private String toStr;

        public String getToStr() {
            return this.toString();
        }
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
