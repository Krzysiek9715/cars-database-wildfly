package io._10a.entity;

import javax.persistence.*;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID", nullable = false)
    private Long transactionId;

    @Column(name = "NAME")
    private String name;
    @Column(name = "CUSTOMER_NAME")
    private String user;
    @Column(name = "DATE")
    private String date;

    @ManyToOne
    @JoinColumn(name = "CAR_ID", referencedColumnName = "CAR_ID", insertable = false, updatable = false)
    protected Car car;


    public Transaction() {
    }

    public Transaction(Long transactionId, String name, String user, String date, Car car) {
        this.transactionId = transactionId;
        this.name = name;
        this.user = user;
        this.date = date;
        this.car = car;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
