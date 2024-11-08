package com.bufigol.evm6s6.repositorio;

import com.bufigol.evm6s6.interfaces.INT_ProductoRepository;
import com.bufigol.evm6s6.modelo.Producto;
import com.bufigol.evm6s6.utils.SQLConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class ProductoRepository implements INT_ProductoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByMarca(String marca) {
        try {
            TypedQuery<Producto> query = entityManager.createQuery(
                    SQLConstants.HQL_BUQUEDA_POR_MARCA, Producto.class);
            query.setParameter("marca", marca);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar productos por marca: {}", marca, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByModelo(String modelo) {
        try {
            TypedQuery<Producto> query = entityManager.createQuery(
                    SQLConstants.HQL_FIND_BY_MODELO, Producto.class);
            query.setParameter("modelo", modelo);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar productos por modelo: {}", modelo, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorDescripcion(String palabra) {
        try {
            return entityManager.createNativeQuery(
                            SQLConstants.SQL_BUSCAR_POR_DESCRIPCION, Producto.class)
                    .setParameter("palabra", palabra)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar productos por descripción: {}", palabra, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByModeloAndMarca(String modelo, String marca) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    SQLConstants.HQL_CONTAR_PRODUCTOS_SEGUN_MARCA_MODELO, Long.class);
            query.setParameter("modelo", modelo);
            query.setParameter("marca", marca);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia por modelo y marca: {} - {}", modelo, marca, e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> busquedaGeneral(String termino) {
        try {
            TypedQuery<Producto> query = entityManager.createQuery(
                    SQLConstants.HQL_BUSQUEDA_GENERAL, Producto.class);
            query.setParameter("termino", termino);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error en búsqueda general: {}", termino, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void flush() {
        try {
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error al realizar flush", e);
        }
    }

    @Override
    @NonNull
    public <S extends Producto> S saveAndFlush(@NonNull S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    @NonNull
    public <S extends Producto> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    public void deleteAllInBatch(@NonNull Iterable<Producto> entities) {
        try {
            deleteAll(entities);
            flush();
        } catch (Exception e) {
            log.error("Error al eliminar entidades en batch", e);
        }
    }

    @Override
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> ids) {
        try {
            ids.forEach(this::deleteById);
            flush();
        } catch (Exception e) {
            log.error("Error al eliminar por IDs en batch", e);
        }
    }

    @Override
    public void deleteAllInBatch() {
        try {
            deleteAll();
            flush();
        } catch (Exception e) {
            log.error("Error al eliminar todo en batch", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Producto getOne(@NonNull Long id) {
        return entityManager.getReference(Producto.class, id);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Producto getById(@NonNull Long id) {
        return getOne(id);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Producto getReferenceById(@NonNull Long id) {
        return getOne(id);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public <S extends Producto> Optional<S> findOne(@NonNull Example<S> example) {
        try {
            return entityManager.createQuery(
                            SQLConstants.HQL_COMIENZO_BUSQUEDA_GENERAL + createExampleWhereClause(example),
                            (Class<S>) example.getProbeType())
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (Exception e) {
            log.error("Error al buscar uno por ejemplo", e);
            return Optional.empty();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public <S extends Producto> List<S> findAll(@NonNull Example<S> example) {
        try {
            return entityManager.createQuery(
                            SQLConstants.HQL_COMIENZO_BUSQUEDA_GENERAL + createExampleWhereClause(example),
                            (Class<S>) example.getProbeType())
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos por ejemplo", e);
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public <S extends Producto> List<S> findAll(@NonNull Example<S> example, @NonNull Sort sort) {
        try {
            String queryString = SQLConstants.HQL_COMIENZO_BUSQUEDA_GENERAL +
                    createExampleWhereClause(example) + " " +
                    createOrderByClause(sort);
            return entityManager.createQuery(queryString, (Class<S>) example.getProbeType())
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos por ejemplo con ordenamiento", e);
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public <S extends Producto> Page<S> findAll(@NonNull Example<S> example, @NonNull Pageable pageable) {
        try {
            String queryString = SQLConstants.HQL_COMIENZO_BUSQUEDA_GENERAL +
                    createExampleWhereClause(example) + " " +
                    createOrderByClause(pageable.getSort());

            TypedQuery<S> query = entityManager.createQuery(
                    queryString, (Class<S>) example.getProbeType());

            long total = count(example);

            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

            List<S> content = query.getResultList();

            return new PageImpl<>(content, pageable, total);
        } catch (Exception e) {
            log.error("Error al buscar todos paginado por ejemplo", e);
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }

    @Override
    public <S extends Producto> long count(@NonNull Example<S> example) {
        try {
            String start = SQLConstants.HQL_COMIENZO_BUSQUEDA_GENERAL.replace(" p ", " COUNT(p) ");
            String queryString = start + createExampleWhereClause(example);
            return entityManager.createQuery(queryString, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar por ejemplo", e);
            return 0L;
        }
    }

    @Override
    public <S extends Producto> boolean exists(@NonNull Example<S> example) {
        return count(example) > 0;
    }

    @Override
    public <S extends Producto, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Método findBy no implementado");
    }

    @Override
    public <S extends Producto> S save(@NonNull S entity) {
        try {
            if (entity.getIdProducto() == null) {
                entityManager.persist(entity);
                return entity;
            } else {
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            log.error("Error al guardar producto", e);
            throw new RuntimeException("Error al guardar producto", e);
        }
    }

    @Override
    public <S extends Producto> List<S> saveAll(Iterable<S> entities) {
        try {
            List<S> savedEntities = new ArrayList<>();
            entities.forEach(entity -> savedEntities.add(save(entity)));
            return savedEntities;
        } catch (Exception e) {
            log.error("Error al guardar múltiples productos", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Producto> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(Producto.class, id));
        } catch (Exception e) {
            log.error("Error al buscar producto por ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        try {
            return entityManager.createQuery(SQLConstants.HQL_FIND_ALL, Producto.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los productos", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAllById(Iterable<Long> ids) {
        try {
            List<Long> idList = StreamSupport.stream(ids.spliterator(), false)
                    .collect(Collectors.toList());
            if (idList.isEmpty()) {
                return new ArrayList<>();
            }
            return entityManager.createQuery(
                            "SELECT p FROM Producto p WHERE p.idProducto IN :ids", Producto.class)
                    .setParameter("ids", idList)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar productos por IDs", e);
            return new ArrayList<>();
        }
    }

    @Override
    public long count() {
        try {
            return entityManager.createQuery(SQLConstants.HQL_COUNT_ALL, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar productos", e);
            return 0L;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            findById(id).ifPresent(this::delete);
        } catch (Exception e) {
            log.error("Error al eliminar producto por ID: {}", id, e);
        }
    }

    @Override
    public void delete(Producto entity) {
        try {
            entityManager.remove(
                    entityManager.contains(entity) ? entity : entityManager.merge(entity)
            );
        } catch (Exception e) {
            log.error("Error al eliminar producto", e);
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        try {
            ids.forEach(this::deleteById);
        } catch (Exception e) {
            log.error("Error al eliminar productos por IDs", e);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Producto> entities) {
        try {
            entities.forEach(this::delete);
        } catch (Exception e) {
            log.error("Error al eliminar múltiples productos", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            entityManager.createQuery("DELETE FROM Producto").executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar todos los productos", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll(Sort sort) {
        try {
            String queryString = SQLConstants.HQL_FIND_ALL + " " + createOrderByClause(sort);
            return entityManager.createQuery(queryString, Producto.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los productos ordenados", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        try {
            String queryString = SQLConstants.HQL_FIND_ALL + " " + createOrderByClause(pageable.getSort());

            TypedQuery<Producto> query = entityManager.createQuery(queryString, Producto.class);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

            List<Producto> content = query.getResultList();
            long total = count();

            return new PageImpl<>(content, pageable, total);
        } catch (Exception e) {
            log.error("Error al buscar todos los productos paginados", e);
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }

    private <S extends Producto> String createExampleWhereClause(Example<S> example) {
        S probe = example.getProbe();
        List<String> conditions = new ArrayList<>();

        if (probe.getModelo() != null) {
            conditions.add("p.modelo = '" + probe.getModelo() + "'");
        }
        if (probe.getMarca() != null) {
            conditions.add("p.marca = '" + probe.getMarca() + "'");
        }
        if (probe.getDescripcion() != null) {
            conditions.add("p.descripcion = '" + probe.getDescripcion() + "'");
        }

        return conditions.isEmpty() ? "1=1" : String.join(" AND ", conditions);
    }

    private String createOrderByClause(Sort sort) {
        if (sort == null || !sort.isSorted()) {
            return "";
        }

        List<String> orders = new ArrayList<>();
        sort.forEach(order ->
                orders.add("p." + order.getProperty() + " " + order.getDirection())
        );

        return "ORDER BY " + String.join(", ", orders);
    }
}