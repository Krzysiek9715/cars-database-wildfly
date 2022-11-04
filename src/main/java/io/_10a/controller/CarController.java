package io._10a.controller;


import io._10a.entity.Car;
import io._10a.entity.Car_;
import io._10a.entity.Transaction;
import io._10a.entity.Transaction_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
public class CarController {


    Logger log = LoggerFactory.getLogger(CarController.class);

    @PersistenceContext
    EntityManager entityManager;

    public List<Car> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        carRoot.fetch(Car_.transactionList, JoinType.LEFT);
        return entityManager.createQuery(criteriaQuery
                        .select(carRoot).distinct(true))
                .getResultList();
    }

    public Car findById(Long id) {
        log.info("Search object with id: {}", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Car resultCar = entityManager.createQuery(criteriaQuery.select(carRoot)
                        .where(criteriaBuilder.equal(carRoot.get(Car_.id), id)))
                .getSingleResult();
        log.info("Find car {}", resultCar);
        return resultCar;
    }

    public List<Car> findLike(String prefix, String sortType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);


        @SuppressWarnings("unchecked")
        Join<Car, Transaction> carTransactionJoin = (Join<Car, Transaction>) carRoot.fetch(Car_.transactionList, JoinType.LEFT);


        Predicate predicateForBrand = criteriaBuilder.like(carRoot.get(Car_.brand), prefix + "%");
        Predicate predicateForModel = criteriaBuilder.like(carRoot.get(Car_.model), prefix + "%");
        Predicate predicateForTransactionClient = criteriaBuilder.like(carTransactionJoin.get(Transaction_.user), prefix + "%");
        Order ascId = criteriaBuilder.asc(carRoot.get(Car_.id));
        Order descId = criteriaBuilder.desc(carRoot.get(Car_.id));
        Order ascBrand = criteriaBuilder.asc(carRoot.get(Car_.brand));
        Order descBrand = criteriaBuilder.desc(carRoot.get(Car_.brand));
        Order ascModel = criteriaBuilder.asc(carRoot.get(Car_.model));
        Order descModel = criteriaBuilder.desc(carRoot.get(Car_.model));
        Order ascTransaction = criteriaBuilder.asc(carTransactionJoin.get(Transaction_.transactionId));
        Order descTransaction = criteriaBuilder.desc(carTransactionJoin.get(Transaction_.transactionId));
        Predicate finalPredicate = criteriaBuilder.or(predicateForBrand, predicateForModel, predicateForTransactionClient);

        log.info("zaczynam metode z controllera");
        if (prefix==null){
            if("Id ascending".equals(sortType)){
                log.info("sortowanie id asc spelnione, {}", sortType);
                criteriaQuery.orderBy(ascId);
            } else if ("Id descending".equals(sortType)) {
                log.info("sortowanie id desc spelnione, {}", sortType);
                criteriaQuery.orderBy(descId);
            } else if ("Brand ascending".equals(sortType)) {
                log.info("sortowanie brand asc spelnione, {}", sortType);
                criteriaQuery.orderBy(ascBrand);
            } else if ("Brand descending".equals(sortType)) {
                log.info("sortowanie brand desc spelnione, {}", sortType);
                criteriaQuery.orderBy(descBrand);
            } else if ("Model ascending".equals(sortType)) {
                log.info("sortowanie model asc spelnione, {}", sortType);
                criteriaQuery.orderBy(ascModel);
            } else if ("Model descending".equals(sortType)) {
                log.info("sortowanie model desc spelnione, {}", sortType);
                criteriaQuery.orderBy(descModel);
            } else if ("Transaction ascending".equals(sortType)) {
                log.info("sortowanie transaction asc spelnione, {}", sortType);
                criteriaQuery.orderBy(ascTransaction);
            } else if ("Transaction descending".equals(sortType)) {
                log.info("sortowanie transaction desc spelnione, {}", sortType);
                criteriaQuery.orderBy(descTransaction);
            }
        } else if ("Id ascending".equals(sortType)) {
            log.info("sortowanie id asc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(ascId);
        } else if ("Id descending".equals(sortType)) {
            log.info("sortowanie id desc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(descId);
        } else if ("Brand ascending".equals(sortType)) {
            log.info("sortowanie brand asc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(ascBrand);
        } else if ("Brand descending".equals(sortType)) {
            log.info("sortowanie brand desc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(descBrand);
        } else if ("Model ascending".equals(sortType)) {
            log.info("sortowanie model asc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(ascModel);
        } else if ("Model descending".equals(sortType)) {
            log.info("sortowanie model desc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(descModel);
        } else if ("Transaction ascending".equals(sortType)) {
            log.info("sortowanie transaction asc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(ascTransaction);
        } else if ("Transaction descending".equals(sortType)) {
            log.info("sortowanie transaction desc spelnione, {}", sortType);
            criteriaQuery.where(finalPredicate).orderBy(descTransaction);
        }

        return entityManager.createQuery(criteriaQuery.distinct(true)).getResultList();
    }

    public void addCar(Car car) {
        entityManager.persist(car);
    }

    public void editCar(Car car) {
        entityManager.merge(car);
    }


    public void deleteCar(Long id) {
        log.info("Start deleting car with id {}", id);
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Car> criteriaDelete = criteriaBuilder.createCriteriaDelete(Car.class);
        Root<Car> carRoot = criteriaDelete.from(Car.class);
        criteriaDelete.where(criteriaBuilder.equal(carRoot.get("id"), id));
        this.entityManager.createQuery(criteriaDelete).executeUpdate();
        log.info("delete succes");
    }


}
