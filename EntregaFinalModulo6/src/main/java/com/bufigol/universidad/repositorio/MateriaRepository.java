package com.bufigol.universidad.repositorio;

import com.bufigol.universidad.interfaces.repositorios.INT_MateriaRepository;
import com.bufigol.universidad.modelo.Materia;
import com.bufigol.universidad.utils.ConstantesSQLyHQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.bufigol.universidad.utils.UtilidadesRepositorio.createOrders;


@Slf4j
@Repository
@Transactional
public class MateriaRepository implements INT_MateriaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Materia> findByAlumnoId(Long alumnoId) {
        log.debug("Buscando materias para el alumno con ID: {}", alumnoId);
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.FIND_MATERIA_BY_ALUMNO_ID
                            , Materia.class)
                    .setParameter("alumnoId", alumnoId)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar materias por alumnoId {}: {}", alumnoId, e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Materia> findByNombreContainingIgnoreCase(String nombre) {
        log.debug("Buscando materias que contengan el nombre: {}", nombre);
        try {
            return entityManager.createQuery(
                            ConstantesSQLyHQL.FIND_MATERIA_BY_NOMBRE,
                            Materia.class)
                    .setParameter("nombre", "%" + nombre.trim() + "%")
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar materias por nombre {}: {}", nombre, e.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean existsByNombreAndAlumnoId(String nombre, Long alumnoId) {
        log.debug("Verificando existencia de materia {} para alumno ID: {}", nombre, alumnoId);
        try {
            Long count = entityManager.createQuery(
                            ConstantesSQLyHQL.COUNT_MATERIA_BY_NOMBRE_AND_ALUMNO_ID, Long.class)
                    .setParameter("nombre", nombre.trim())
                    .setParameter("alumnoId", alumnoId)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia de materia {} para alumno {}: {}",
                    nombre, alumnoId, e.getMessage());
            return false;
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
    public <S extends Materia> S saveAndFlush(@NonNull S entity) {
        log.debug("Guardando y haciendo flush de entidad Materia");
        try {
            S result = entityManager.merge(entity);
            entityManager.flush();
            return result;
        } catch (Exception e) {
            log.error("Error en saveAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar y hacer flush de la materia", e);
        }
    }

    @Override
    @NonNull
    public <S extends Materia> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples materias y haciendo flush");
        List<S> result = new ArrayList<>();
        try {
            entities.forEach(entity -> result.add(saveAndFlush(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error en saveAllAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples materias", e);
        }
    }

    @Override
    public void deleteAllInBatch(@NonNull Iterable<Materia> entities) {
        log.debug("Eliminando materias en batch");
        try {
            for (Materia entity : entities) {
                entityManager.remove(
                        entityManager.contains(entity) ? entity : entityManager.merge(entity)
                );
            }
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar materias en batch", e);
        }
    }

    @Override
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> ids) {
        log.debug("Eliminando materias por IDs en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_MATERIA_BY_ID)
                    .setParameter("ids", ids)
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllByIdInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar materias por IDs en batch", e);
        }
    }

    @Override
    public void deleteAllInBatch() {
        log.debug("Eliminando todas las materias en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL_MATERIA)
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todas las materias", e);
        }
    }

    @Override
    @NonNull
    @Deprecated
    public Materia getOne(@NonNull Long id) {
        log.debug("Obteniendo referencia a materia por ID (deprecated): {}", id);
        return entityManager.getReference(Materia.class, id);
    }

    @Override
    @NonNull
    @Deprecated
    public Materia getById(@NonNull Long id) {
        log.debug("Obteniendo materia por ID (deprecated): {}", id);
        return entityManager.find(Materia.class, id);
    }

    @Override
    @NonNull
    public Materia getReferenceById(@NonNull Long id) {
        log.debug("Obteniendo referencia a materia por ID: {}", id);
        try {
            return entityManager.getReference(Materia.class, id);
        } catch (Exception e) {
            log.error("Error al obtener referencia de materia con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener referencia de materia", e);
        }
    }

    @Override
    @NonNull
    public <S extends Materia> Optional<S> findOne(@NonNull Example<S> example) {
        log.debug("Buscando una materia por Example");
        validateExample(example);
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
    public <S extends Materia> List<S> findAll(@NonNull Example<S> example) {
        log.debug("Buscando todas las materias por Example");
        validateExample(example);
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
    public <S extends Materia> List<S> findAll(@NonNull Example<S> example, @NonNull  Sort sort) {
        log.debug("Buscando todas las materias por Example con ordenamiento");
        validateExample(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            // Aplicar predicados del Example
            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            // Aplicar ordenamiento
            List<Order> orders = getOrders(root, sort, cb);
            query.orderBy(orders);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error en findAll con Example y Sort: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    public <S extends Materia> Page<S> findAll(@NonNull Example<S> example,@NonNull Pageable pageable) {
        log.debug("Buscando materias paginadas por Example");
        validateExample(example);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            // Consulta para obtener los resultados
            CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
            Root<S> root = query.from((Class<S>) example.getProbeType());

            Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
            query.where(predicate);

            // Aplicar ordenamiento si existe
            if (pageable.getSort().isSorted()) {
                query.orderBy(getOrders(root, pageable.getSort(), cb));
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
    public <S extends Materia> long count(@NonNull Example<S> example) {
        log.debug("Contando materias por Example");
        validateExample(example);
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
    public <S extends Materia> boolean exists(@NonNull Example<S> example) {
        log.debug("Verificando existencia de materias por Example");
        return count(example) > 0;
    }

    @Override
    @NonNull
    public <S extends Materia, R> R findBy(@NonNull Example<S> example, @NonNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // Crear consulta base usando los criterios del Example
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = cb.createQuery((Class<S>) example.getProbeType());
        Root<S> root = query.from((Class<S>) example.getProbeType());

        // Aplicar predicados del Example
        Predicate predicate = com.bufigol.universidad.utils.QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
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
    @NonNull
    @Transactional
    public <S extends Materia> S save(@NonNull S entity) {
        log.debug("Guardando materia: {}", entity);
        try {
            validateEntity(entity);
            if (entity.getId() == null) {
                log.debug("Persistiendo nueva materia");
                entityManager.persist(entity);
                return entity;
            } else {
                log.debug("Actualizando materia existente con ID: {}", entity.getId());
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            log.error("Error al guardar materia: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la materia", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public <S extends Materia> List<S> saveAll(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples materias");
        List<S> result = new ArrayList<>();
        try {
            entities.forEach(entity -> result.add(save(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error al guardar múltiples materias: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples materias", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Optional<Materia> findById(@NonNull Long id) {
        log.debug("Buscando materia por ID: {}", id);
        try {
            validateId(id);
            return Optional.ofNullable(entityManager.find(Materia.class, id));
        } catch (Exception e) {
            log.error("Error al buscar materia por ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.debug("Verificando existencia de materia por ID: {}", id);
        try {
            validateId(id);
            Long count = entityManager.createQuery(
                            "SELECT COUNT(m) FROM Materia m WHERE m.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia de materia por ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Materia> findAll() {
        log.debug("Buscando todas las materias");
        try {
            return entityManager.createQuery(
                            ConstantesSQLyHQL.SELECT_ALL_MATERIA, Materia.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todas las materias: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Materia> findAllById(@NonNull Iterable<Long> ids) {
        log.debug("Buscando materias por múltiples IDs");
        try {
            return entityManager.createQuery(
                            ConstantesSQLyHQL.SELECT_MATERIA_BY_ID, Materia.class)
                    .setParameter("ids", CollectionUtils.iterableToList(ids))
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar materias por IDs: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Contando total de materias");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.COUNT_ALL_MATERIA
                            , Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar materias: {}", e.getMessage());
            return 0L;
        }
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        log.debug("Eliminando materia por ID: {}", id);
        try {
            validateId(id);
            Materia materia = entityManager.find(Materia.class, id);
            if (materia != null) {
                entityManager.remove(materia);
            } else {
                log.warn("No se encontró materia con ID: {} para eliminar", id);
            }
        } catch (Exception e) {
            log.error("Error al eliminar materia por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar la materia", e);
        }
    }

    @Override
    @Transactional
    public void delete(@NonNull Materia entity) {
        log.debug("Eliminando materia: {}", entity);
        try {
            validateEntity(entity);
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else {
                entityManager.remove(entityManager.merge(entity));
            }
        } catch (Exception e) {
            log.error("Error al eliminar materia: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar la materia", e);
        }

    }

    @Override
    @Transactional
    public void deleteAllById(@NonNull Iterable<? extends Long> ids) {
        log.debug("Eliminando materias por múltiples IDs");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_MATERIA_BY_ID
                            )
                    .setParameter("ids", CollectionUtils.iterableToList(ids))
                    .executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar materias por IDs: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar materias por IDs", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll(@NonNull Iterable<? extends Materia> entities) {
        log.debug("Eliminando múltiples materias");
        try {
            entities.forEach(this::delete);
        } catch (Exception e) {
            log.error("Error al eliminar múltiples materias: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar múltiples materias", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("Eliminando todas las materias");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL_MATERIA).executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar todas las materias: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todas las materias", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Materia> findAll(@NonNull Sort sort) {
        log.debug("Buscando todas las materias con ordenamiento: {}", sort);
        try {
            // Crear consulta base usando Criteria API
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Materia> query = cb.createQuery(Materia.class);
            Root<Materia> root = query.from(Materia.class);

            // Aplicar ordenamiento
            List<Order> orders = createOrders(root, sort, cb);
            query.orderBy(orders);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error al buscar materias con ordenamiento: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Page<Materia> findAll(@NonNull Pageable pageable) {
        log.debug("Buscando materias paginadas: {}", pageable);
        try {
            // Crear consulta para datos usando Criteria API
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Materia> query = cb.createQuery(Materia.class);
            Root<Materia> root = query.from(Materia.class);

            // Aplicar ordenamiento si existe
            if (pageable.getSort().isSorted()) {
                List<Order> orders = createOrders(root, pageable.getSort(), cb);
                query.orderBy(orders);
            }

            // Ejecutar consulta paginada
            List<Materia> materias = entityManager.createQuery(query)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            // Consulta para contar el total de elementos
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Materia> countRoot = countQuery.from(Materia.class);
            countQuery.select(cb.count(countRoot));
            Long total = entityManager.createQuery(countQuery).getSingleResult();

            return new PageImpl<>(materias, pageable, total);
        } catch (Exception e) {
            log.error("Error al buscar materias paginadas: {}", e.getMessage());
            return Page.empty();
        }
    }


    private <S extends Materia> void validateExample(Example<S> example) {
        if (example == null) {
            throw new IllegalArgumentException("Example no puede ser null");
        }
    }

    private List<Order> getOrders(Root<?> root, Sort sort, CriteriaBuilder cb) {
        return createOrders(root, sort, cb);
    }

    // Métodos helper
    private void validateEntity(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser null");
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }
    }

    // Utility class para convertir Iterable a List
    private static class CollectionUtils {
        public static <T> List<T> iterableToList(Iterable<T> iterable) {
            List<T> list = new ArrayList<>();
            iterable.forEach(list::add);
            return list;
        }
    }
}
