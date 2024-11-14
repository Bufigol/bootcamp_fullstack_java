package com.bufigol.universidad.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryByExamplePredicateBuilder {

    /**
     * Genera un predicado basado en un Example para consultas Criteria
     *
     * @param root    Root de la consulta Criteria
     * @param cb      CriteriaBuilder para construir la consulta
     * @param example Example con los criterios de búsqueda
     * @param <T>     Tipo de la entidad
     * @return Predicate construido según el Example
     */
    public static <T> Predicate getPredicate(Root<T> root, CriteriaBuilder cb, Example<T> example) {
        ExampleMatcher matcher = example.getMatcher();
        T probe = example.getProbe();
        List<Predicate> predicates = new ArrayList<>();

        // Obtener todos los campos de la entidad
        for (Field field : probe.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(probe);
                if (value != null) {
                    String fieldName = field.getName();

                    // Si es String y debe ignorar mayúsculas/minúsculas
                    if (matcher.isIgnoreCaseEnabled() && value instanceof String) {
                        predicates.add(cb.equal(
                                cb.lower(root.get(fieldName)),
                                ((String) value).toLowerCase()
                        ));
                    }
                    // Para strings, usar LIKE si el matcher lo indica
                    else if (value instanceof String && matcher.isIgnoreCaseEnabled()) {
                        predicates.add(cb.like(
                                cb.lower(root.get(fieldName)),
                                "%" + ((String) value).toLowerCase() + "%"
                        ));
                    }
                    // Para el resto de los casos, usar equals
                    else {
                        predicates.add(cb.equal(root.get(fieldName), value));
                    }
                }
            } catch (IllegalAccessException e) {
                // Log error si es necesario
            }
        }

        // Si no hay predicados, retornar siempre verdadero
        if (predicates.isEmpty()) {
            return cb.isTrue(cb.literal(true));
        }

        // Combinar todos los predicados con AND
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}