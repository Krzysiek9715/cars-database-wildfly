package io._10a.bean;


import io._10a.controller.CarController;
import io._10a.entity.Car;
import io._10a.entity.CarDTO;
import io._10a.entity.Transaction;
import io._10a.entity.TransactionDTO;
import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CarBean implements Serializable {

    Logger logger = LoggerFactory.getLogger(CarBean.class);


    @EJB
    CarController carController;

    private List<Car> carList;
    private String filter;
    private Long id;
    //    @Size(min = 2,max = 30, message = "Set correct brand name")
    @Pattern(regexp = "[A-Za-z ]{2,}", message = "Brand can`t be empty, please input correct brand name")
    private String brand;
    //    @Size(min = 2,max = 30, message = "Set correct model name")
    @Pattern(regexp = "[A-Za-z0-9 ]{2,}", message = "Model can`t be empty, please input correct name")
    private String model;
    private String sortType;
    private List<String> sortList = List.of("Sort alhabetically by brand", "Default sort by ID");

    private List<CarDTO> carDTOList;
    private Car carToDelete;

    private Car carToEdit;


    @PostConstruct
    public void init() {
        logger.info("Start init");
        carList = carController.findAll();
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

    public List<CarDTO> getCarDTOList() {
        return carDTOList;
    }

    public void setCarDTOList(List<CarDTO> carDTOList) {
        this.carDTOList = carDTOList;
    }

    /* Methods ----------------------------------------------------------------------------------------*/

    public void takeAction() {
        this.carList = carController.findLike(filter);
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

    public String editCar() {
        carToEdit.setBrand(brand);
        carToEdit.setModel(model);
        carController.editCar(carToEdit);
        init();
        return "index.xhtml?faces-redirect=true";
    }

    public String editCarById(Long id) {
        logger.info("Id wynosi {}", id);
        carToEdit = carController.findById(id);
        logger.info("Car to edit to {}", carToEdit);
        return "editCar.xhtml?id="+id+"&faces-redirect=true";
    }


    public String deleteCar() {
        carController.deleteCar(id);
        init();
        return "index.xhtml?faces-redirect=true";
    }
    public String deleteCarById(Long id) {
        logger.info("Id wynosi {}", id);
        carToDelete = carController.findById(id);
        logger.info("Car to edit to {}", carToEdit);
        return "confirmDelete.xhtml?id="+id+"&faces-redirect=true";
    }

    public void sortByBrandAsc(String name) {
        this.carList = carController.getDataSortedByBrandAsc(name);
    }

    public List<CarDTO> getCarDTOs() {
        logger.info("Start init");
        carList = carController.findAll();
        logger.info("find cars: {}", carList.toString());
        for (Car car : carList) {
            logger.info("zaczynam petle na obiekcie {}", car);
            List<TransactionDTO> transactionDTOList = car.getTransactionList()
                    .stream()
                    .map(tra -> new TransactionDTO(tra.getTransactionId(), tra.getName(), tra.getUser(), tra.getDate()))
                    .collect(Collectors.toList());
            if (transactionDTOList.isEmpty()) {
                CarDTO carDTO = new CarDTO(car.getId(), car.getBrand(), car.getModel());
                carDTOList.add(carDTO);
                logger.info("Dodałem do listy z nullem");

            }
            logger.info("Mam takie dto transakcji {}", transactionDTOList);
            CarDTO carDTO = new CarDTO(car.getId(), car.getBrand(), car.getModel(), transactionDTOList);
            logger.info("zapiduje do carDTO {}", carDTO);
            carDTOList.add(carDTO);
            logger.info("Dodałem do listy z transakcją");
        }
        logger.info("Zwracam liste carDTO {}", carDTOList);
        return carDTOList;

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

    public String confirmDeleteCar() {
        return "confirmDelete.xhtml?faces-redirect=true";
    }


}
