package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * HQL (Hibernate) class dedicated to the Users table in the DB
 *
 *
 * @author Kacper Dudzinski
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries(
        value={
                @NamedQuery(
                        name="getUsersNQ",
                        query="from User"
                )
        }
)
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Integer id;

    @Column(name="USER_ROLE")
    private String role;

    @Column(name="USER_FIRST_NAME")
    private String fName;

    @Column(name="USER_LAST_NAME")
    private String lName;

    @Column(name="USER_EMAIL")
    private String email;

    @Column(name="USER_DIVISION")
    private String division;

    @Column(name="USER_HASH")
    private String hash;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", division='" + division + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

