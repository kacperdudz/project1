package models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * HQL (Hibernate) class dedicated to the Reimbursements table in the DB
 *
 *
 * @author Kacper Dudzinski
 *
 **/
@NamedQueries(
        value={
                @NamedQuery(
                        name="getReimbNQById",
                        query="select r from Reimbursement r " +
                                "inner join r.employee " +
                                "where user_id=:id " + "order by r.timeCreated desc"
                ),
                @NamedQuery(
                        name="getReimbNQ",
                        query="select r from Reimbursement r " +
                                "inner join r.employee " +
                                "order by r.timeCreated desc"
                ),
        }
)
@Entity
@Table(name="REIMBURSEMENTS")
public class Reimbursement {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="REIMB_ID")
    private Integer id;

    @Column(name="REIMB_STATUS")
    private String status;

    @Column(name="REIMB_TITLE")
    private String title;

    @Column(name="REIMB_DESC")
    private String desc;

    @Column(name="REIMB_AMT")
    private Integer amt;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="EMPLOYEE", referencedColumnName="USER_ID", columnDefinition="INT")
    private User employee;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="MANAGER", referencedColumnName="USER_ID", columnDefinition="INT")
    private User manager;

    @Column(name="REIMB_TIME_CREATED")
    private LocalDateTime timeCreated;

    @Column(name="REIMB_TIME_RESOLVED")
    private LocalDateTime timeResolved;

//    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//    @JoinColumn(name="id")
//    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getManager() { return manager; }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeResolved() {
        return timeResolved;
    }

    public void setTimeResolved(LocalDateTime timeResolved) {
        this.timeResolved = timeResolved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", employee=" + employee +
                ", manager=" + manager +
                ", timeCreated=" + timeCreated +
                ", timeResolved=" + timeResolved +
                '}';
    }
}
