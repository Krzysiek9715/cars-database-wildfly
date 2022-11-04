package io._10a.entity;

import org.hibernate.engine.spi.CascadeStyle;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID", nullable = false)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME", length = 8)
    private TransactionTypeEnum typeEnum;
    @Column(name = "CUSTOMER_NAME")
    private String user;
    @Column(name = "DATE")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_ID", referencedColumnName = "CAR_ID", insertable = false, updatable = false)
    protected Car car;


    public Transaction() {
    }

    public Transaction(TransactionTypeEnum typeEnum, String user, Date date, Car car) {
        this.typeEnum = typeEnum;
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

    public TransactionTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TransactionTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return  "Transaction id: " + transactionId +
                ", type: " + typeEnum +
                ", client: " + user +
                ", date: " + date;
    }
}
