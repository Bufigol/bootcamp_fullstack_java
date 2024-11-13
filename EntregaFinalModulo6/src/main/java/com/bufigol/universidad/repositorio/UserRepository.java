package com.bufigol.universidad.repositorio;

import com.bufigol.universidad.interfaces.INT_UserRepository;
import com.bufigol.universidad.modelo.Usuario;
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
public class UserRepository implements INT_UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Usuario> findByUsername(String username) {
        log.debug("Buscando usuario por username: {}", username);
        try {
            Usuario usuario = entityManager.createQuery(ConstantesSQLyHQL.FIND_BY_USERNAME, Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(usuario);
        } catch (NoResultException e) {
            log.debug("No se encontró usuario con username: {}", username);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar usuario por username {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        log.debug("Buscando usuario por email: {}", email);
        try {
            Usuario usuario = entityManager.createQuery(ConstantesSQLyHQL.FIND_BY_EMAIL, Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(usuario);
        } catch (NoResultException e) {
            log.debug("No se encontró usuario con email: {}", email);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar usuario por email {}: {}", email, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        log.debug("Verificando existencia de usuario por username: {}", username);
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.EXISTS_BY_USERNAME, Boolean.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al verificar existencia de usuario por username {}: {}",
                    username, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("Verificando existencia de usuario por email: {}", email);
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.EXISTS_BY_EMAIL, Boolean.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al verificar existencia de usuario por email {}: {}",
                    email, e.getMessage());
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
    @Transactional
    public <S extends Usuario> S saveAndFlush(@NonNull S entity) {
        log.debug("Guardando y haciendo flush de entidad Usuario");
        try {
            S result = save(entity);
            flush();
            return result;
        } catch (Exception e) {
            log.error("Error en saveAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar y hacer flush del usuario", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public <S extends Usuario> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples usuarios y haciendo flush");
        List<S> result = new ArrayList<>();
        try {
            for (S entity : entities) {
                result.add(saveAndFlush(entity));
            }
            return result;
        } catch (Exception e) {
            log.error("Error en saveAllAndFlush: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples usuarios", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public void deleteAllInBatch(@NonNull Iterable<Usuario> entities) {
        log.debug("Eliminando usuarios en batch");
        try {
            for (Usuario entity : entities) {
                entityManager.remove(
                        entityManager.contains(entity) ? entity : entityManager.merge(entity)
                );
            }
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar usuarios en batch", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> id) {
        log.debug("Eliminando usuarios por IDs en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_BY_IDS_USUARIOS)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(id))
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllByIdInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar usuarios por IDs en batch", e);
        }
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        log.debug("Eliminando todos los usuarios en batch");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL_USUARIOS)
                    .executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            log.error("Error en deleteAllInBatch: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todos los usuarios", e);
        }
    }


    @Override
    @NonNull
    @Deprecated
    public Usuario getOne(@NonNull Long id) {
        log.debug("Obteniendo referencia a usuario por ID (deprecated): {}", id);
        return entityManager.getReference(Usuario.class, id);
    }

    @Override
    @NonNull
    @Deprecated
    public Usuario getById(@NonNull Long id) {
        log.debug("Obteniendo usuario por ID (deprecated): {}", id);
        return entityManager.find(Usuario.class, id);
    }


    @Override
    @NonNull
    public Usuario getReferenceById(@NonNull Long id) {
        log.debug("Obteniendo referencia a usuario por ID: {}", id);
        try {
            return entityManager.getReference(Usuario.class, id);
        } catch (Exception e) {
            log.error("Error al obtener referencia de usuario con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener referencia de usuario", e);
        }
    }

    @Override
    @NonNull
    public <S extends Usuario> Optional<S> findOne(@NonNull Example<S> example) {
        log.debug("Buscando un usuario por Example");
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> List<S> findAll(@NonNull Example<S> example) {
        log.debug("Buscando todos los usuarios por Example");
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> List<S> findAll(@NonNull Example<S> example,@NonNull Sort sort) {
        log.debug("Buscando todos los usuarios por Example con ordenamiento");
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> Page<S> findAll(@NonNull Example<S> example,@NonNull Pageable pageable) {
        log.debug("Buscando usuarios paginados por Example: {}", example);
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> long count(@NonNull Example<S> example) {
        log.debug("Contando usuarios por Example");
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> boolean exists(@NonNull Example<S> example) {
        log.debug("Verificando existencia de usuarios por Example");
        return count(example) > 0;
    }

    @Override
    @NonNull
    public <S extends Usuario, R> R findBy(@NonNull  Example<S> example,
                                           @NonNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        log.debug("Ejecutando findBy con Example y Function");
        UtilidadesRepositorio.validateExampleUsuario(example);
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
    public <S extends Usuario> S save(@NonNull S entity) {
        log.debug("Guardando usuario: {}", entity);
        try {
            if (entity.getId() == null) {
                log.debug("Persistiendo nuevo usuario");
                entityManager.persist(entity);
                return entity;
            } else {
                log.debug("Actualizando usuario existente con ID: {}", entity.getId());
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            log.error("Error al guardar usuario: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el usuario", e);
        }
    }

    @Override
    @NonNull
    @Transactional
    public <S extends Usuario> List<S> saveAll(@NonNull Iterable<S> entities) {
        log.debug("Guardando múltiples usuarios");
        List<S> result = new ArrayList<>();
        try {
            entities.forEach(entity -> result.add(save(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error al guardar múltiples usuarios: {}", e.getMessage());
            throw new RuntimeException("Error al guardar múltiples usuarios", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(@NonNull Long id) {
        log.debug("Buscando usuario por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            return Optional.ofNullable(entityManager.find(Usuario.class, id));
        } catch (Exception e) {
            log.error("Error al buscar usuario por ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(@NonNull Long id) {
        log.debug("Verificando existencia de usuario por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            Long count = entityManager.createQuery(ConstantesSQLyHQL.COUNT_BY_ID_USUARIOS, Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia de usuario por ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        log.debug("Buscando todos los usuarios");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.FIND_ALL_USUARIOS, Usuario.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los usuarios: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Usuario> findAllById(@NonNull Iterable<Long> ids) {
        log.debug("Buscando usuarios por múltiples IDs");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.FIND_BY_ID_USUARIOS, Usuario.class)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(ids))
                    .getResultList();
        } catch (Exception e) {
            log.error("Error al buscar usuarios por IDs: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Contando total de usuarios");
        try {
            return entityManager.createQuery(ConstantesSQLyHQL.COUNT_ALL_USUARIOS, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar usuarios: {}", e.getMessage());
            return 0L;
        }
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        log.debug("Eliminando usuario por ID: {}", id);
        try {
            UtilidadesRepositorio.validateId(id);
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
            } else {
                log.warn("No se encontró usuario con ID: {} para eliminar", id);
            }
        } catch (Exception e) {
            log.error("Error al eliminar usuario por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    @Override
    @Transactional
    public void delete(@NonNull Usuario usuario) {
        log.debug("Eliminando usuario: {}", usuario);
        try {
            UtilidadesRepositorio.validateEntity(usuario);
            if (entityManager.contains(usuario)) {
                entityManager.remove(usuario);
            } else {
                entityManager.remove(entityManager.merge(usuario));
            }
        } catch (Exception e) {
            log.error("Error al eliminar usuario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(@NonNull Iterable<? extends Long> ids) {
        log.debug("Eliminando usuarios por múltiples IDs");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_BY_IDS_USUARIOS)
                    .setParameter("ids", UtilidadesRepositorio.iterableToList(ids))
                    .executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar usuarios por IDs: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar usuarios por IDs", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll(@NonNull Iterable<? extends Usuario> entities) {
        log.debug("Eliminando múltiples usuarios");
        try {
            entities.forEach(this::delete);
        } catch (Exception e) {
            log.error("Error al eliminar múltiples usuarios: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar múltiples usuarios", e);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("Eliminando todos los usuarios");
        try {
            entityManager.createQuery(ConstantesSQLyHQL.DELETE_ALL_USUARIOS)
                    .executeUpdate();
        } catch (Exception e) {
            log.error("Error al eliminar todos los usuarios: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar todos los usuarios", e);
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Usuario> findAll(@NonNull Sort sort) {
        log.debug("Buscando todos los usuarios con ordenamiento: {}", sort);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
            Root<Usuario> root = query.from(Usuario.class);

            // Aplicar ordenamiento
            List<Order> orders = UtilidadesRepositorio.createOrders(root, sort, cb);
            query.orderBy(orders);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error al buscar usuarios con ordenamiento: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(@NonNull Pageable pageable) {
        log.debug("Buscando usuarios paginados: {}", pageable);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
            Root<Usuario> root = query.from(Usuario.class);

            // Aplicar ordenamiento si existe
            if (pageable.getSort().isSorted()) {
                List<Order> orders = UtilidadesRepositorio.createOrders(root, pageable.getSort(), cb);
                query.orderBy(orders);
            }

            // Ejecutar consulta paginada
            List<Usuario> usuarios = entityManager.createQuery(query)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            // Consulta para contar el total
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Usuario> countRoot = countQuery.from(Usuario.class);
            countQuery.select(cb.count(countRoot));
            Long total = entityManager.createQuery(countQuery).getSingleResult();

            return new PageImpl<>(usuarios, pageable, total);
        } catch (Exception e) {
            log.error("Error al buscar usuarios paginados: {}", e.getMessage());
            return Page.empty();
        }
    }

}
