package io._10a.bean;


import io._10a.controller.CarController;
import io._10a.entity.Car;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CarBean implements Serializable {


    @EJB
    CarController carController;

    private List<Car> carList;

    private String filter;

    private Long id;
    private String brand;
    private String model;


    @PostConstruct
    public void init() {
        carList = carController.findAll();
    }


    /* GETTERY I SETTERY */

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

    public List<Car> getAllCars() {
        return carList;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


    /* Methods ----------------------------------------------------------------------------------------*/

    public void takeAction() {
        this.carList = carController.findLike(filter);
    }

    public String addCar() {
        Car newCar = new Car(brand,model);
        carController.addCar(newCar);
        init();
        return "index.xhtml?faces-redirect=true";
    }

    public String editCar(){
        Car editCar = carController.findById(id);
        editCar.setBrand(brand);
        editCar.setModel(model);
        carController.editCar(editCar);
        init();
        return "index.xhtml?faces-redirect=true";
    }

    public String deleteCar(){
        carController.deleteCar(id);
        init();
        return "index.xhtml?faces-redirect=true";
    }

    public List<Car> sortByBrandAsc(){
            return carController.getDataSortedByBrand();
    }

    /* PAGES ----------------------------------------------------------------------------------------------*/

    public String goToAddCar() {
        return "addCar.xhtml?faces-redirect=true";
    }

    public String goToEditCar() {
        return "editCar.xhtml?faces-redirect=true";
    }
   public String goToDeleteCar() {
        return "deleteCar.xhtml?faces-redirect=true";
    }

    public String confirmDeleteCar(){
        return "confirmDelete.xhtml?faces-redirect=true";
    }

    public String mainPage(){
        return "index.xhtml?faces-redirect=true";
    }

    public String goToSortByBrandPage() {
        return "sortedAscCars.xhtml?faces-redirect=true";
    }

}
