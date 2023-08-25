package com.example.prog4.repository.employee.dao;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.InternalServerErrorException;
import com.example.prog4.model.utilities.DateRange;
import com.example.prog4.repository.cnaps.entity.EmployeeCnaps;
import com.example.prog4.repository.employee.entity.Employee;
import com.example.prog4.repository.employee.entity.enums.Sex;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class EmployeeManagerDao {
    private EntityManager entityManager;

    public <T> List<T> findByCriteria(EntityManager entityManager,CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root, String lastName, String firstName, String countryCode, Sex sex, String position, DateRange entranceRange, DateRange departureRange, Pageable pageable) {

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.or(
                builder.like(builder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%")));

        predicates.add(builder.or(
                builder.like(builder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%")));

        predicates.add(builder.or(
                builder.like(builder.lower(root.get("phones").get("value")), "%" + countryCode + ",%")));

        if (sex != null) {
            predicates.add(builder.or(
                    builder.equal(root.get("sex"), sex)
            ));
        }

        // date rages
        // departure
        if (departureRange.getEnd() != null && departureRange.getBegin() != null) {
            predicates.add(builder.or(
                    builder.between(root.get("departureDate"), departureRange.getBegin(), departureRange.getEnd())
            ));
        } else if (departureRange.getEnd() == null && departureRange.getBegin() != null) {
            predicates.add(builder.or(
                    builder.equal(root.get("departureDate"), departureRange.getBegin())
            ));
        } else if (departureRange.getEnd() != null) {
            predicates.add(builder.or(
                    builder.equal(root.get("departureDate"), departureRange.getEnd())
            ));
        }

        // date rages
        // entrance
        if (entranceRange.getEnd() != null && entranceRange.getBegin() != null) {
            predicates.add(builder.or(
                    builder.between(root.get("entranceDate"), entranceRange.getBegin(), entranceRange.getEnd())
            ));
        } else if (entranceRange.getEnd() == null && entranceRange.getBegin() != null) {
            predicates.add(builder.or(
                    builder.equal(root.get("entranceDate"), entranceRange.getBegin())
            ));
        } else if (entranceRange.getEnd() != null) {
            predicates.add(builder.or(
                    builder.equal(root.get("entranceDate"), entranceRange.getEnd())
            ));
        }

        if (!position.isBlank()) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get("positions").get("name")), "%" + position.toLowerCase() + "%")
            ));
        }

        query.where(predicates.toArray(Predicate[]::new))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        try {
            return entityManager.createQuery(query)
                    .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}