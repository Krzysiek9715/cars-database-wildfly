package io._10a.bean;


import io._10a.controller.CarController;
import io._10a.entity.Car;
import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CarBean implements Serializable {

    Logger logger = LoggerFactory.getLogger(CarBean.class);

    @EJB
    CarController carController;

    private List<Car> carList;
    private String filter;
    private Long id;
    @Pattern(regexp = "[A-Za-z ]{2,}", message = "Brand can`t be empty, please input correct brand name")
    private String brand;
    @Pattern(regexp = "[A-Za-z0-9 ]{2,}", message = "Model can`t be empty, please input correct name")
    private String model;
    private String sortType;
    private List<String> sortList = List.of("Sort alhabetically by brand", "Default sort by ID");

    private List<String> sortTypeList = List.of("Id ascending", "Id descending", "Brand ascending", "Brand descending", "Model ascending", "Model descending", "Transaction ascending","Transaction descending");

    private Car carToDelete;

    private Car carToEdit;


    @PostConstruct
    public void init() {
        logger.info("Start init");
        carList = carController.findAll();
        sortType = sortTypeList.get(0);
    }


    /* GETTERY I SETTERY ----------------------------------------------------------------------------------------------*/

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

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<String> getSortList() {
        return sortList;
    }

    public void setSortList(List<String> sortList) {
        this.sortList = sortList;
    }

    public Car getCarToDelete() {
        return carController.findById(id);
    }

    public void setCarToDelete() {
        this.carToDelete = carController.findById(id);
    }

    public Car getCarToEdit() {
        return carToEdit;
    }

    public void setCarToEdit(Car carToEdit) {
        this.carToEdit = carToEdit;
    }

    public List<String> getSortTypeList() {
        return sortTypeList;
    }

    public void setSortTypeList(List<String> sortTypeList) {
        this.sortTypeList = sortTypeList;
    }

    /* Methods ----------------------------------------------------------------------------------------*/

    public void takeAction() {
        this.carList = carController.findLike(filter, sortType);
    }


    public void callback() {
        Ajax.oncomplete("alert('po co sprawdzasz - Ciekawosc to pierwszy stopien do piekla')");
    }

    public String addCar() {
        Car newCar = new Car(brand, model);
        carController.addCar(newCar);
        init();
        return "index.xhtml?faces-redirect=true";
    }

    public String editCarById(Long id) {
        logger.info("Id wynosi {}", id);
        carToEdit = carController.findById(id);
        logger.info("Car to edit to {}", carToEdit);
        return "editCar.xhtml?id="+id+"&faces-redirect=true";
    }


    public String deleteCarById(Long id) {
        logger.info("Id wynosi {}", id);
        carToDelete = carController.findById(id);
        logger.info("Car to edit to {}", carToEdit);
        return "confirmDelete.xhtml?id="+id+"&faces-redirect=true";
    }

    public String addTransactionForCar(Long id){
        logger.info("id is {}", id);
        return "addTransaction.xhtml?id="+id+"&faces-redirect=true";
    }



    /* PAGES ----------------------------------------------------------------------------------------------*/

    public String goToAddCar() {
        return "addCar.xhtml?faces-redirect=true";
    }

    public String confirmDeleteCar() {
        return "confirmDelete.xhtml?faces-redirect=true";
    }


}
