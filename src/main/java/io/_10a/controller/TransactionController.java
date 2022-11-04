package io._10a.controller;

import io._10a.entity.Transaction;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class TransactionController {

    @PersistenceContext
    EntityManager entityManager;


    public void addTransaction(Transaction transaction){
      entityManager.createNativeQuery("INSERT INTO cars.TRANSACTION (NAME, CUSTOMER_NAME, DATE, CAR_ID) VALUES (:name, :client,:date,:car)")
              .setParameter("name", transaction.getTypeEnum().toString())
              .setParameter("client", transaction.getUser())
              .setParameter("date", transaction.getDate())
              .setParameter("car", transaction.getCar())
              .executeUpdate();
    }



}
