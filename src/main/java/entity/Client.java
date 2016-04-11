package entity;

public abstract class Client extends BaseEntity {
    private String country;
    private String city;
    private String address;
    private String telephone;
    private String email;
    private String bankAccountNumber;

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
}
