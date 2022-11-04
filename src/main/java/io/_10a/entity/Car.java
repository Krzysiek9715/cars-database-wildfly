package io._10a.entity;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "CARS", schema = "cars")
public class Car {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAR_ID", nullable = false)
    private Long id;

    @Column(name = "BRAND")
    @Pattern(regexp = "[A-Za-z ]{2,}",message = "Brand can`t be empty, please input correct brand name")
    private String brand;

    @Column(name = "MODEL")
    @Pattern(regexp = "[A-Za-z0-9 ]{2,}",message = "Model can`t be empty, please input correct name")
    private String model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    protected List<Transaction> transactionList;


    public Car() {
    }

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
