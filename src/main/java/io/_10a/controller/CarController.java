package io._10a.controller;


import io._10a.entity.Car;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class CarController {

    @PersistenceContext
    EntityManager entityManager;

    public List<Car> findAll(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        return entityManager.createQuery(criteriaQuery.select(carRoot)).getResultList();
    }

    public Car findById(Long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        return entityManager.createQuery(criteriaQuery.select(carRoot)
                .where(criteriaBuilder.equal(carRoot.get("id"),id)))
                .getSingleResult();
    }

    public List<Car> findLike(String prefix){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        return entityManager.createQuery(criteriaQuery.select(carRoot)
                .where(criteriaBuilder.like(carRoot.get("brand"),prefix+"%"))).getResultList();
    }

    public void addCar(Car car){
        entityManager.persist(car);
    }

    public void editCar(Car car){
        entityManager.merge(car);
    }


    public void deleteCar(Long id){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Car> criteriaDelete = criteriaBuilder.createCriteriaDelete(Car.class);
        Root<Car> carRoot = criteriaDelete.from(Car.class);
        criteriaDelete.where(criteriaBuilder.equal(carRoot.get("id"), id));
        this.entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    public List<Car> getDataSortedByBrand(){
        CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        return entityManager.createQuery(criteriaQuery.select(carRoot)
                .orderBy(criteriaBuilder.asc(carRoot.get("brand")))).getResultList();
//        return entityManager.createNamedQuery("Car.sortedByBrand",Car.class).getResultList();

    }


}
