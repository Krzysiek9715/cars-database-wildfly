package io._10a.entity;


public class TransactionDTO {

private Long transactionId;
private String name;
private String user;
private String date;

    public TransactionDTO(Long transactionId, String name, String user, String date) {
        this.transactionId = transactionId;
        this.name = name;
        this.user = user;
        this.date = date;
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

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
