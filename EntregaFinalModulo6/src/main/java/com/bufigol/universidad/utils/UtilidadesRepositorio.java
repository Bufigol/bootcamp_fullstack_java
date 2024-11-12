package com.bufigol.universidad.utils;

import com.bufigol.universidad.modelo.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class UtilidadesRepositorio {
    @NonNull
    public static  <S extends Role> CriteriaQuery<S> getsCriteriaQuery(@NonNull Example<S> example, EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
        Root<S> root = query.from((Class<S>) example.getProbeType());

        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        query.where(predicate);
        return query;
    }


    public static List<Order> createOrders(Root<?> root, Sort sort, CriteriaBuilder cb) {
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            if (order.isAscending()) {
                orders.add(cb.asc(root.get(order.getProperty())));
            } else {
                orders.add(cb.desc(root.get(order.getProperty())));
            }
        }
        return orders;
    }

    public static <T> Optional<T> executeQuerySafely(String operation, Supplier<T> queryExecution) {
        try {
            return Optional.ofNullable(queryExecution.get());
        } catch (Exception e) {
            log.error("Error executing {}: {}", operation, e.getMessage());
            return Optional.empty();
        }
    }


}
