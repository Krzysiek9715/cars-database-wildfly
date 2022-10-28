package io._10a.controller;


import io._10a.entity.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class CarController {


    Logger logger = LoggerFactory.getLogger(CarController.class);

    @PersistenceContext
    EntityManager entityManager;

    public List<Car> findAll(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        carRoot.fetch("transactionList", JoinType.LEFT);
        return entityManager.createQuery(criteriaQuery
                .select(carRoot).distinct(true))
                .getResultList();
    }

    public Car findById(Long id){
        logger.info("Search object with id: {}",id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Car resultCar = entityManager.createQuery(criteriaQuery.select(carRoot)
                .where(criteriaBuilder.equal(carRoot.get("id"),id)))
                .getSingleResult();
        logger.info("Find car {}", resultCar);
        return resultCar;
    }

    public List<Car> findLike(String prefix){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        carRoot.fetch("transactionList", JoinType.LEFT);
        return entityManager.createQuery(criteriaQuery.select(carRoot)
                .distinct(true)
                .where(criteriaBuilder.like(carRoot.get("brand"),prefix+"%"))).getResultList();
    }

    public void addCar(Car car){
        entityManager.persist(car);
    }

    public void editCar(Car car){
        entityManager.merge(car);
    }


    public void deleteCar(Long id){
        logger.info("Start deleting car with id {}",  id);
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Car> criteriaDelete = criteriaBuilder.createCriteriaDelete(Car.class);
        Root<Car> carRoot = criteriaDelete.from(Car.class);
        criteriaDelete.where(criteriaBuilder.equal(carRoot.get("id"), id));
        this.entityManager.createQuery(criteriaDelete).executeUpdate();
        logger.info("delete succes");
    }

    public List<Car> getDataSortedByBrandAsc(String name){
        CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        carRoot.fetch("transactionList", JoinType.LEFT);
        return entityManager.createQuery(criteriaQuery.select(carRoot).distinct(true)
                .orderBy(criteriaBuilder.asc(carRoot.get(name)))).getResultList();
//        return entityManager.createNamedQuery("Car.sortedByBrand",Car.class).getResultList();
    }

    public List<Car> getDataSortedByBrandDesc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        return entityManager.createQuery(criteriaQuery.select(carRoot)
                .orderBy(criteriaBuilder.desc(carRoot.get("brand")))).getResultList();
    }
}
