package com.bufigol.universidad.repositorio;

import com.bufigol.universidad.interfaces.INT_RoleRepository;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.utils.QueryByExamplePredicateBuilder;
import com.bufigol.universidad.utils.UtilidadesRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Repository
@Transactional
public class RoleRepository implements INT_RoleRepository {

    private static final String DELETE_BY_IDS = "DELETE FROM Role r WHERE r.id IN :ids";
    private static final String COUNT_BY_ID = "SELECT COUNT(r) > 0 FROM Role r WHERE r.id = :id";
    private static final String FIND_BY_NAME = "SELECT r FROM Role r WHERE r.name = :name";
    private static final String FIND_ALL = "SELECT r FROM Role r";
    private static final String COUNT_ALL = "SELECT COUNT(r) FROM Role r";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Role> findByName(String name) {
        return UtilidadesRepositorio.executeQuerySafely("findByName", () ->
                entityManager.createQuery(FIND_BY_NAME, Role.class)
                        .setParameter("name", name)
                        .getSingleResult()
        );
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
    @Transactional
    @NonNull
    public <S extends Role> S saveAndFlush(@NonNull S entity) {
        Assert.notNull(entity, "Entity must not be null");
        S result = entityManager.merge(entity);
        entityManager.flush();
        return result;
    }

    @Override
    @Transactional
    @NonNull
    public <S extends Role> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null");
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(saveAndFlush(entity));
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteAllInBatch(@NonNull Iterable<Role> entities) {
        Assert.notNull(entities, "Entities must not be null");
        for (Role entity : entities) {
            entityManager.remove(
                    entityManager.contains(entity) ? entity : entityManager.merge(entity)
            );
        }
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> ids) {
        Assert.notNull(ids, "IDs must not be null");
        entityManager.createQuery(DELETE_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        entityManager.createQuery("DELETE FROM Role")
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    @NonNull
    @Deprecated
    public Role getOne(@NonNull Long id) {
        Assert.notNull(id, "ID must not be null");
        return entityManager.getReference(Role.class, id);
    }

    @Override
    @NonNull
    @Deprecated
    public Role getById(@NonNull Long id) {
        Assert.notNull(id, "ID must not be null");
        return entityManager.find(Role.class, id);
    }



    @Override
    @NonNull
    public Role getReferenceById(@NonNull Long id) {
        Assert.notNull(id, "ID must not be null");
        return entityManager.getReference(Role.class, id);
    }

    @Override
    @NonNull
    public <S extends Role> Optional<S> findOne(@NonNull Example<S> example) {
        Assert.notNull(example, "Example must not be null");
        try {
            CriteriaQuery<S> query = UtilidadesRepositorio.getsCriteriaQuery(example, this.entityManager);
            return Optional.ofNullable(entityManager.createQuery(query)
                    .setMaxResults(1)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @NonNull
    public <S extends Role> List<S> findAll(@NonNull Example<S> example) {
        Assert.notNull(example, "Example must not be null");
        CriteriaQuery<S> query = UtilidadesRepositorio.getsCriteriaQuery(example, this.entityManager);
        return entityManager.createQuery(query).getResultList();
    }



    @Override
    @NonNull
    public <S extends Role> List<S> findAll(@NonNull Example<S> example, @NonNull Sort sort) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
        Root<S> root = query.from((Class<S>) example.getProbeType());

        // Aplicar predicados del Example
        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        query.where(predicate);

        // Aplicar ordenamiento
        List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            if (order.isAscending()) {
                orders.add(cb.asc(root.get(order.getProperty())));
            } else {
                orders.add(cb.desc(root.get(order.getProperty())));
            }
        }
        query.orderBy(orders);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @NonNull
    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
        Root<S> root = query.from((Class<S>) example.getProbeType());

        // Aplicar predicados del Example
        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        query.where(predicate);

        // Aplicar ordenamiento
        if (pageable.getSort().isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (order.isAscending()) {
                    orders.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(order.getProperty())));
                }
            }
            query.orderBy(orders);
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
    }



    @Override
    public <S extends Role> long count(Example<S> example) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<S> root = query.from((Class<S>) example.getProbeType());

        // Aplicar predicados del Example
        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        query.where(predicate);

        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }


    @Override
    public <S extends Role> boolean exists(Example<S> example) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<S> root = query.from((Class<S>) example.getProbeType());

        // Aplicar predicados del Example
        Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        query.where(predicate);

        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult() > 0;
    }


    @Override
    @NonNull
    public <S extends Role, R> R findBy(Example<S> example,
                                        Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {

        // Crear consulta base usando los criterios del Example
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
    }

    @Override
    @Transactional
    @NonNull
    public <S extends Role> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    @NonNull
    public <S extends Role> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    @NonNull
    public Optional<Role> findById(@NonNull Long id) {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        try {
            return entityManager.createQuery(
                            "SELECT COUNT(r) > 0 FROM Role r WHERE r.id = :id", Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @NonNull
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }

    @Override
    @NonNull
    public List<Role> findAllById(@NonNull Iterable<Long> ids) {
        return entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery(
                        "SELECT COUNT(r) FROM Role r", Long.class)
                .getSingleResult();
    }


    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
        }
    }

    @Override
    @Transactional
    public void delete(@NonNull Role entity) {
        entityManager.remove(
                entityManager.contains(entity) ? entity : entityManager.merge(entity)
        );
    }

    @Override
    @Transactional
    @NonNull
    public void deleteAllById(Iterable<? extends Long> ids) {
        entityManager.createQuery("DELETE FROM Role r WHERE r.id IN :ids")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends Role> entities) {
        for (Role entity : entities) {
            delete(entity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Role where id > 0").executeUpdate();
    }

    @Override
    @NonNull
    public List<Role> findAll(@NonNull Sort sort) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);

        List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            if (order.isAscending()) {
                orders.add(cb.asc(root.get(order.getProperty())));
            } else {
                orders.add(cb.desc(root.get(order.getProperty())));
            }
        }
        query.orderBy(orders);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @NonNull
    public Page<Role> findAll(Pageable pageable) {
        // Consulta para obtener los resultados paginados
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);

        // Aplicar ordenamiento si existe
        if (pageable.getSort().isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (order.isAscending()) {
                    orders.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(order.getProperty())));
                }
            }
            query.orderBy(orders);
        }

        // Ejecutar la consulta con paginación
        List<Role> roles = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Consulta para obtener el total de elementos
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Role.class)));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(roles, pageable, total);
    }



}
