package com.bufigol.universidad.repositorio;

import com.bufigol.universidad.interfaces.INT_AlumnoRepository;
import com.bufigol.universidad.modelo.Alumno;
import com.bufigol.universidad.utils.ConstantesSQLyHQL;
import com.bufigol.universidad.utils.QueryByExamplePredicateBuilder;
import com.bufigol.universidad.utils.UtilidadesRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Repository
@Transactional
public class AlumnoRepository implements INT_AlumnoRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Alumno> findByRut(@NonNull String rut) {
        log.debug("Buscando alumno por RUT: {}", rut);
        try {
            return UtilidadesRepositorio.executeQuerySafely(
                    "findByRut",
                    () -> entityManager.createQuery(ConstantesSQLyHQL.FIND_BY_RUT, Alumno.class)
                            .setParameter("rut", rut)
                            .getSingleResult()
            );
        } catch (Exception e) {
            log.error("Error al buscar alumno por RUT {}: {}", rut, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByRut(String rut) {
        log.debug("Verificando existencia de alumno por RUT: {}", rut);
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.EXISTS_BY_RUT, Boolean.class)
                    .setParameter("rut", rut)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al verificar existencia de alumno por RUT {}: {}", rut, e.getMessage());
            return false;
        }
    }

    @Override
    public List<Alumno> findByNombreContainingIgnoreCase(String nombre) {
        log.debug("Buscando alumnos que contengan el nombre: {}", nombre);
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.FIND_BY_NOMBRE, Alumno.class)
                    .setParameter("nombre", "%" + nombre.trim().toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar alumnos por nombre {}: {}", nombre, e.getMessage());
            return List.of();
        }
    }

    @Override
    public void flush() {
        log.debug("Ejecutando flush de la sesión");
        try {
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error durante el flush: {}", e.getMessage());
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> S saveAndFlush(@NonNull S entity) {
        log.debug("Guardando y haciendo flush de entidad Alumno");
        try {
            S result = entityManager.merge(entity);
            entityManager.flush();
            return result;
        } catch (Exception e) {
            log.error("Error en saveAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar y hacer flush del alumno", e);
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples alumnos y haciendo flush");
        List<S> result = new ArrayList<>();
        try {
            for (S entity : entities) {
                result.add(saveAndFlush(entity));
            }
            return result;
        } catch (Exception e) {
            log.error("Error en saveAllAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples alumnos", e);
        }
    }

    @Override
    public void deleteAllInBatch(@NonNull Iterable<Alumno> entities) {
        log.debug("Eliminando alumnos en batch");
        try {
            for (Alumno entity : entities) {
                entityManager.remove(
                        entityManager.contains(entity) ? entity : entityManager.merge(entity)
                );
            }
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar alumnos en batch", e);
        }
    }

    @Override
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> ids) {
        log.debug("Eliminando alumnos por IDs en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_BY_IDS)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(ids))
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllByIdInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar alumnos por IDs en batch", e);
        }
    }

    @Override
    public void deleteAllInBatch() {
        log.debug("Eliminando todos los alumnos en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL)
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todos los alumnos", e);
        }
    }

    @Override
    @NonNull
    @Deprecated
    public Alumno getOne(@NonNull Long id) {
        log.debug("Obteniendo referencia a alumno por ID (deprecated): {}", id);
        return entityManager.getReference(Alumno.class, id);
    }

    @Override
    @NonNull
    @Deprecated
    public Alumno getById(@NonNull Long id) {
        log.debug("Obteniendo alumno por ID (deprecated): {}", id);
        return entityManager.find(Alumno.class, id);
    }


    @Override
    @NonNull
    public Alumno getReferenceById(@NonNull Long id) {
        log.debug("Obteniendo referencia a alumno por ID: {}", id);
        try {
            return entityManager.getReference(Alumno.class, id);
        } catch (Exception e) {
            log.error("Error al obtener referencia de alumno con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener referencia de alumno", e);
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> Optional<S> findOne(@NonNull Example<S> example) {
        log.debug("Buscando un alumno por Example");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            try {
                return Optional.ofNullable(
                        entityManager.createQuery(query)
                                .setMaxResults(1)
                                .getSingleResult()
                );
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error en findOne con Example: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> List<S> findAll(@NonNull Example<S> example) {
        log.debug("Buscando todos los alumnos por Example");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error en findAll con Example: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> List<S> findAll(@NonNull Example<S> example,@NonNull Sort sort) {
        log.debug("Buscando todos los alumnos por Example con ordenamiento");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            // Aplicar predicados del Example
            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            // Aplicar ordenamiento
            List<Order> orders = UtilidadesRepositorio.createOrders(root, sort, cb);
            query.orderBy(orders);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error en findAll con Example y Sort: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    public <S extends Alumno> Page<S> findAll(@NonNull Example<S> example,@NonNull Pageable pageable) {
        log.debug("Buscando alumnos paginados por Example");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            // Consulta para obtener los resultados
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            // Aplicar ordenamiento si existe
            if (pageable.getSort().isSorted()) {
                query.orderBy(UtilidadesRepositorio.createOrders(root, pageable.getSort(), cb));
            }

            // Ejecutar consulta paginada
            List<S> content = entityManager.createQuery(query)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            // Consulta para contar el total de elementos
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<S> countRoot = countQuery.from((Class<S>) example.getProbeType());
            countQuery.select(cb.count(countRoot));
            countQuery.where(QueryByExamplePredicateBuilder.getPredicate(countRoot, cb, example));
            Long total = entityManager.createQuery(countQuery).getSingleResult();

            return new PageImpl<>(content, pageable, total);
        } catch (Exception e) {
            log.error("Error en findAll con Example y Pageable: {}", e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public <S extends Alumno> long count(@NonNull Example<S> example) {
        log.debug("Contando alumnos por Example");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<S> root = query.from((Class<S>) example.getProbeType());

            query.select(cb.count(root));
            query.where(QueryByExamplePredicateBuilder.getPredicate(root, cb, example));

            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            log.error("Error en count con Example: {}", e.getMessage());
            return 0L;
        }
    }

    @Override
    public <S extends Alumno> boolean exists(@NonNull Example<S> example) {
        log.debug("Verificando existencia de alumnos por Example");
        return count(example) > 0;
    }

    @Override
    @NonNull
    public <S extends Alumno, R> R findBy(@NonNull Example<S> example,
                                          @NonNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        log.debug("Ejecutando findBy con Example y Function");
        UtilidadesRepositorio.validateExampleAlumno(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            // Aplicar predicados del Example
            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            // Ejecutar la consulta y retornar el primer resultado
            try {
                S result = entityManager.createQuery(query)
                        .setMaxResults(1)
                        .getSingleResult();
                return (R) Optional.ofNullable(result);
            } catch (NoResultException e) {
                return (R) Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error en findBy: {}", e.getMessage());
            throw new RuntimeException("Error en findBy", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public <S extends Alumno> S save(@NonNull S entity) {
        log.debug("Guardando alumno: {}", entity);
        try {
            UtilidadesRepositorio.validateEntity(entity);
            if (entity.getId() == null) {
                log.debug("Persistiendo nuevo alumno");
                entityManager.persist(entity);
                return entity;
            } else {
                log.debug("Actualizando alumno existente con ID: {}", entity.getId());
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            log.error("Error al guardar alumno: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el alumno", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public <S extends Alumno> List<S> saveAll(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples alumnos");
        List<S> result = new ArrayList<>();
        try {
            entities.forEach(entity -> result.add(save(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error al guardar múltiples alumnos: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples alumnos", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Optional<Alumno> findById(@NonNull Long id) {
        log.debug("Buscando alumno por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            return Optional.ofNullable(entityManager.find(Alumno.class, id));
        } catch (Exception e) {
            log.error("Error al buscar alumno por ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(@NonNull Long id) {
        log.debug("Verificando existencia de alumno por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            Long count = entityManager.createQuery(
                            ConstantesSQLyHQL.COUNT_BY_ID, Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia de alumno por ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Alumno> findAll() {
        log.debug("Buscando todos los alumnos");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.FIND_ALL, Alumno.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los alumnos: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    public List<Alumno> findAllById(@NonNull Iterable<Long> ids) {
        log.debug("Buscando alumnos por múltiples IDs");
        try {
            return entityManager.createQuery(
                            ConstantesSQLyHQL.FIND_BY_ID, Alumno.class)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(ids))
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar alumnos por IDs: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Contando total de alumnos");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.COUNT_ALL, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar alumnos: {}", e.getMessage());
            return 0L;
        }
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        log.debug("Eliminando alumno por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            Alumno alumno = entityManager.find(Alumno.class, id);
            if (alumno != null) {
                entityManager.remove(alumno);
            } else {
                log.warn("No se encontró alumno con ID: {} para eliminar", id);
            }
        } catch (Exception e) {
            log.error("Error al eliminar alumno por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el alumno", e);
        }
    }

    @Override
    @Transactional
    public void delete(@NonNull Alumno entity) {
        log.debug("Eliminando alumno: {}", entity);
        try {
            UtilidadesRepositorio.validateEntity(entity);
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else {
                entityManager.remove(entityManager.merge(entity));
            }
        } catch (Exception e) {
            log.error("Error al eliminar alumno: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el alumno", e);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(@NonNull Iterable<? extends Long> ids) {
        log.debug("Eliminando alumnos por múltiples IDs");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_BY_IDS)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(ids))
                    .executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar alumnos por IDs: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar alumnos por IDs", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll(@NonNull Iterable<? extends Alumno> entities) {
        log.debug("Eliminando múltiples alumnos");
        try {
            entities.forEach(this::delete);
        } catch (Exception e) {
            log.error("Error al eliminar múltiples alumnos: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar múltiples alumnos", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("Eliminando todos los alumnos");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL).executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar todos los alumnos: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todos los alumnos", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Alumno> findAll(@NonNull Sort sort) {
        log.debug("Buscando todos los alumnos con ordenamiento: {}", sort);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Alumno> query = cb.createQuery(Alumno.class);
            Root<Alumno> root = query.from(Alumno.class);

            // Aplicar ordenamiento
            List<Order> orders = UtilidadesRepositorio.createOrders(root, sort, cb);
            query.orderBy(orders);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error al buscar alumnos con ordenamiento: {}", e.getMessage());
            return new ArrayList<>();
        }    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(@NonNull Pageable pageable) {
        log.debug("Buscando alumnos paginados: {}", pageable);
        try {
            // Consulta para datos
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Alumno> query = cb.createQuery(Alumno.class);
            Root<Alumno> root = query.from(Alumno.class);

            // Aplicar ordenamiento si existe
            if (pageable.getSort().isSorted()) {
                List<Order> orders = UtilidadesRepositorio.createOrders(root, pageable.getSort(), cb);
                query.orderBy(orders);
            }

            // Ejecutar consulta paginada
            List<Alumno> alumnos = entityManager.createQuery(query)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            // Consulta para contar el total
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Alumno> countRoot = countQuery.from(Alumno.class);
            countQuery.select(cb.count(countRoot));
            Long total = entityManager.createQuery(countQuery).getSingleResult();

            return new PageImpl<>(alumnos, pageable, total);
        } catch (Exception e) {
            log.error("Error al buscar alumnos paginados: {}", e.getMessage());
            return Page.empty();
        }
    }

}
