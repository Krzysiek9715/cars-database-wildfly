package io._10a.bean;


import io._10a.controller.CarController;
import io._10a.controller.TransactionController;
import io._10a.entity.Car;
import io._10a.entity.Transaction;
import io._10a.entity.TransactionTypeEnum;
import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@Named
@ViewScoped
public class TransactionBean implements Serializable {

    Logger log = LoggerFactory.getLogger(getClass());

    @EJB
    TransactionController transactionController;

    @EJB
    CarController carController;

    @Inject @Param(name = "id")
    private Long transactionCarId;

    private TransactionTypeEnum tratypeEnum;

    @Pattern(regexp = "[0-9]{4}-{1}[0-9]{2}-{1}[0-9]{2}", message = "Wrong data format! need: yyyy-MM-dd")
    private String traBeanDate;

    private List<TransactionTypeEnum> enumList = Arrays.asList(TransactionTypeEnum.values());

    private String traBeanUser;

    private Car carToTransaction;

    @PostConstruct
    public void init(){
        carToTransaction = carController.findById(transactionCarId);
    }

    /* GETTERY I SETTERY *************************************************************************/

    public TransactionTypeEnum getTratypeEnum() {
        return tratypeEnum;
    }

    public void setTratypeEnum(TransactionTypeEnum tratypeEnum) {
        this.tratypeEnum = tratypeEnum;
    }

    public String getTraBeanDate() {
        return traBeanDate;
    }

    public void setTraBeanDate(String traBeanDate) {
        this.traBeanDate = traBeanDate;
    }

    public String getTraBeanUser() {
        return traBeanUser;
    }

    public void setTraBeanUser(String traBeanUser) {
        this.traBeanUser = traBeanUser;
    }

    public Car getCarToTransaction() {
        return carToTransaction;
    }

    public void setCarToTransaction(Car carToTransaction) {
        this.carToTransaction = carToTransaction;
    }

    public List<TransactionTypeEnum> getEnumList() {
        return enumList;
    }

    public void setEnumList(List<TransactionTypeEnum> enumList) {
        this.enumList = enumList;
    }

    /* METODY *************************************************************************/
    public String addTransaction(){
        Car carToTransaction = carController.findById(transactionCarId);
        log.info("Mam takie auto : {}", carToTransaction);
        java.sql.Date dateSql = java.sql.Date.valueOf(traBeanDate);
        Transaction transactionToAdd = new Transaction(tratypeEnum,traBeanUser, dateSql, carToTransaction);
        log.info("Mam takiego enuma : {}", transactionToAdd.getTypeEnum());
        log.info("Mam taka transakcje : {}", transactionToAdd);
        log.info("Mam taka transakcje : {}", transactionToAdd.getCar());
        transactionController.addTransaction(transactionToAdd);
        return "index.xhtml?faces-redirect=true";
    }



}
