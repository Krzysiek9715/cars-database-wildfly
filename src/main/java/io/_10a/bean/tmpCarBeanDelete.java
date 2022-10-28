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
public class tmpCarBeanDelete implements Serializable {


    @EJB
    CarController carController;

    @Inject
    @Param(name = "id")
    private Long deleteId;

    private Car car;

    @PostConstruct
    public void init(){
        car = carController.findById(deleteId);
    }

    public Long getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(Long deleteId) {
        this.deleteId = deleteId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String delete(){
        carController.deleteCar(car.getId());
        return "/index?faces-redirect=true";
    }
}
