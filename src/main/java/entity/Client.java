package entity;

public class Client extends BaseEntity {
    private String country;
    private String city;
    private String address;
    private String telephone;
    private String email;
    private String bankAccountNumber;
    private ClientType clientType;
    private String clientName;
    private String ein;
    private String firstName;
    private String lastName;
    private String ssn;

    public Client() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null) {
            throw new NullPointerException("Attempt to set null to country");
        }
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null) {
            throw new NullPointerException("Attempt to set null to city");
        }
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new NullPointerException("Attempt to set null to address");
        }
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone == null) {
            throw new NullPointerException("Attempt to set null to telephone");
        }
        this.telephone = telephone;
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


    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        if (bankAccountNumber == null) {
            throw new NullPointerException("Attempt to set null to bankAccountNumber");
        }
        this.bankAccountNumber = bankAccountNumber;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        if (clientName == null) {
            throw new NullPointerException("Attempt to set null to clientName");
        }
        this.clientName = clientName;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        if (ssn == null) {
            throw new NullPointerException("Attempt to set null to ssn");
        }
        this.ssn = ssn;
    }

    public enum ClientType {
        LEGAL, INDIVIDUAL
    }
}
