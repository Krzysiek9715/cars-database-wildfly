package io._10a.bean;


import io._10a.controller.CarController;
import io._10a.entity.Car;
import org.omnifaces.cdi.Param;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class tmpCarBeanEdit implements Serializable {

    @EJB
    CarController carController;

    @Inject @Param(name = "id")
    private Long editedId;

    private Car car;

    @PostConstruct
    public void init(){
        car = carController.findById(editedId);
    }

    public Long getEditedId() {
        return editedId;
    }

    public void setEditedId(Long editedId) {
        this.editedId = editedId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


    public String save(){
        carController.editCar(car);
        return "/index?faces-redirect=true";
    }
}
