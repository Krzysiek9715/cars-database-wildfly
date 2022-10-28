package io._10a.entity;

import java.util.List;

public class CarDTO {

    private Long carId;
    private String brand;
    private String model;
    private List<TransactionDTO> transactionList;

    public CarDTO(Long carId, String brand, String model) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
    }

    public CarDTO(Long carId, String brand, String model, List<TransactionDTO> transactionList) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.transactionList = transactionList;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<TransactionDTO> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionDTO> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "carId=" + carId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", transactionList=" + transactionList +
                '}';
    }
}
