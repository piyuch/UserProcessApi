package de.user.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.inject.Default;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a Basic {@link Control} that provides CRUD functionality for JPA
 * {@link Entity}s. The methods can be used in other Controls but are not
 * exposed. So all {@link Control}s that inherit from the crud control must
 * expose qualified functions for their entities. It is used as an Abstraction
 * layer for the {@link EntityManager}.
 * 
 * 
 */
@Default
public abstract class BasicCrudControl {

	/**
	 * The Logger for this Class.
	 */
	private static final Logger LOG = LogManager.getLogger(BasicCrudControl.class);

	/**
	 * Entity Manager will be set from embedding application.
	 */
	@PersistenceContext(unitName = "de.paylax.jpa")
	EntityManager em;

	/**
	 * Creates a new Entity and makes it available in the {@link EntityManager}.
	 * 
	 * @param entityToCreate
	 *            The Entity to create
	 * @param <T>
	 *            Defines the Generic
	 * @return The persisted entity.
	 * 
	 */
	protected <T> T create(final T entityToCreate) {
		// CHECKSTYLE:ON FinalParameters
		LOG.entry(entityToCreate);
		this.em.persist(entityToCreate);
		this.em.flush();
		this.em.refresh(entityToCreate);
		LOG.info("create entry");
		return entityToCreate;
	}

	/**
	 * Find and entity by its type and the id.
	 * 
	 * @param type
	 *            The Type of the {@link Entity}
	 * @param id
	 *            The id of the {@link Entity}
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entity or null.
	 */
	protected <T> T find(final Class<T> type, final Object id) {
		return this.em.find(type, id);
	}

	/**
	 * Find all Entities of the given Type.
	 * 
	 * @param type
	 *            The type of the {@link Entity}
	 * @param <T>
	 *            Defines the Generic
	 * @return All Entities of the Type
	 */
	protected <T> List<T> findAll(final Class<T> type) {
		CriteriaQuery<T> findAllCriteria = this.em.getCriteriaBuilder().createQuery(type);
		findAllCriteria.select(findAllCriteria.from(type));
		List<T> all = this.em.createQuery(findAllCriteria).getResultList();
		return all;
	}

	/**
	 * Find all Entities of the given Type ordered ascending by the given
	 * orderedBy property.
	 * 
	 * @param type
	 *            The type of the {@link Entity}
	 * 
	 * @param orderedBy
	 *            The property to order by
	 * @param <T>
	 *            Defines the Generic
	 * @return All Entities of the Type
	 */
	protected <T> List<T> findAllOrderedAscending(final Class<T> type, final String orderedBy) {
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<T> findAllCriteria = criteriaBuilder.createQuery(type);
		Root<T> root = findAllCriteria.from(type);
		findAllCriteria.select(root);
		findAllCriteria.orderBy(criteriaBuilder.asc(root.get(orderedBy)));
		return this.em.createQuery(findAllCriteria).getResultList();
	}

	/**
	 * Find all Entities of the given Type ordered descending by the given
	 * orderedBy property.
	 * 
	 * @param type
	 *            The type of the {@link Entity}
	 * 
	 * @param orderedBy
	 *            The property to order by
	 * @param <T>
	 *            Defines the Generic
	 * @return All Entities of the Type
	 */
	protected <T> List<T> findAllOrderedDescending(final Class<T> type, final String orderedBy) {
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<T> findAllCriteria = criteriaBuilder.createQuery(type);
		Root<T> root = findAllCriteria.from(type);
		findAllCriteria.select(root);
		findAllCriteria.orderBy(criteriaBuilder.desc(root.get(orderedBy)));
		return this.em.createQuery(findAllCriteria).getResultList();
	}

	/**
	 * Delete an {@link Entity} of the given type with the given identifier.
	 * 
	 * @param type
	 *            The type of the entity.
	 * @param id
	 *            The Id to delete.
	 */
	protected void delete(final Class<?> type, final Object id) {
		Object ref = this.em.getReference(type, id);
		this.em.remove(ref);
	}

	/**
	 * Update an Entity and return the updated.
	 * 
	 * @param entityToUpdate
	 *            The {@link Entity} that should be updated.
	 * @param <T>
	 *            Defines the Generic
	 * @return The updated {@link Entity}
	 */
	protected <T> T update(final T entityToUpdate) {
		T t = this.em.merge(entityToUpdate);
		return t;
	}

	/**
	 * Merge an Entity and return the updated.
	 * 
	 * @param entityToUpdate
	 *            The {@link Entity} that should be updated.
	 * @param <T>
	 *            Defines the Generic
	 * @return The updated {@link Entity}
	 */
	protected <T> T merge(final T entityToUpdate) {
		return this.em.merge(entityToUpdate);
	}

	/**
	 * Delete a {@link Entity}.
	 * 
	 * @param entity
	 *            to delete
	 */
	protected void remove(final Object entity) {
		this.em.remove(entity);
	}

	/**
	 * Call a Finder that is specified by a named Query.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param type
	 *            The type to return
	 * @param <T>
	 *            The generic type
	 * @return The found Entities
	 */

	@SuppressWarnings("unchecked")
	protected <T> List<T> findWithNamedQuery(final String namedQueryName, final Class<T> type) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	/**
	 * Call a Finder that is specified by a named Query. Parameters are passed
	 * in a Object specifying the name and the parameter to use.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entities
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> findWithNamedQuery(final String namedQueryName, final Map<String, Object> parameters,
			final Class<T> type) {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	/**
	 * Call a Finder that is specified by a named Query. Parameters are passed
	 * in a Object specifying the name and the parameter to use.
	 * 
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @param type
	 *            The Result type expected
	 * @param maxLimit
	 *            The maximum Results to fetch.
	 * @return The found Entities
	 */

	@SuppressWarnings("unchecked")
	protected <T> List<T> findWithNamedQueryAndMaxLimit(final String namedQueryName,
			final Map<String, Object> parameters, final Class<T> type, int maxLimit) {
		return findWithNamedQuery(namedQueryName, parameters, maxLimit);
	}

	/**
	 * Call a Finder that is specified by a named Query. Parameters are passed
	 * in a Object specifying the name and the parameter to use.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entity
	 */
	@SuppressWarnings("unchecked")
	protected <T> T findSingleResultByNamedQuery(final String namedQueryName, final Map<String, Object> parameters,
			final Class<T> type) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return (T) query.getSingleResult();
	}

	/**
	 * Call a Finder that is specified by a named Query. Parameters are passed
	 * in a Object specifying the name and the parameter to use. The parameters
	 * are specified by position rather than by name.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 *            Key is an int.
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entities
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> findWithNamedNativeQuery(final String namedQueryName, final Map<Integer, Object> parameters,
			final Class<T> type) {
		return findWithNamedNativeQuery(namedQueryName, parameters, 0);
	}

	/**
	 * Call a Finder that is specified by a named Query. The Result is limited
	 * by the provided result limit.
	 * 
	 * @param queryName
	 *            The named query to use.
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @param resultLimit
	 *            The maximum Results to fetch
	 * @return The found Entities
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> findWithNamedQuery(final String queryName, final Class<T> type, final int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided. The Results are of the provided type.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * 
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entities.
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> findByNativeQuery(final String nativeQuerySql, final Class<T> type) {
		return this.em.createNativeQuery(nativeQuerySql, type).getResultList();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided. The Results are of the provided type.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * 
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entities.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T findSingleResultByNativeQuery(final String nativeQuerySql, final Class<T> type) {
		return (T) this.em.createNativeQuery(nativeQuerySql, type).getSingleResult();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * 
	 * @param type
	 *            The Result type expected
	 * @param <T>
	 *            Defines the Generic
	 * @return The found Entity.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T findSingleResultByNativeQuery(final String nativeQuerySql) {
		return (T) this.em.createNativeQuery(nativeQuerySql).getSingleResult();
	}

	/**
	 * Executes the native query (update or delete) specified by the native
	 * query {@link String} provided. Returns the number of entities deleted or
	 * updated.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * @return the number of entities deleted or updated.
	 */
	protected int executeNativeQuery(final String nativeQuerySql) {
		return this.em.createNativeQuery(nativeQuerySql).executeUpdate();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided. The Results are of the provided untyped.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * 
	 * @return The found Entities.
	 */
	@SuppressWarnings("rawtypes")
	protected List findByNativeQuery(final String nativeQuerySql) {
		return this.em.createNativeQuery(nativeQuerySql).getResultList();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided. The Results are of the provided untyped.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * 
	 * @param mapping
	 *            The name of the SqlResultSetMapping that should be used
	 * @return The found Entities.
	 */
	@SuppressWarnings("rawtypes")
	protected List findByNativeQuery(final String nativeQuerySql, final String mapping) {
		return this.em.createNativeQuery(nativeQuerySql, mapping).getResultList();
	}

	/**
	 * Call a Finder that is specified by a named Query. Parameters are passed
	 * in a Object specifying the name and the parameter to use.
	 * 
	 * The Result is limited by the provided result limit.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @param resultLimit
	 *            The maximum Results to fetch.
	 * @return The found Entities
	 */
	@SuppressWarnings("rawtypes")
	protected List findWithNamedQuery(final String namedQueryName, final Map<String, Object> parameters,
			final int resultLimit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	/**
	 * Call a Finder that is specified by a named native Query. Parameters are
	 * passed in a Object specifying the name and the parameter to use. The
	 * parameters are specified by position rather than by name.
	 * 
	 * The Result is limited by the provided result limit.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 *            The key is an int, so that it specifies the position.
	 * @param resultLimit
	 *            The maximum Results to fetch.
	 * @return The found Entities
	 */
	@SuppressWarnings("rawtypes")
	protected List findWithNamedNativeQuery(final String namedQueryName, final Map<Integer, Object> parameters,
			final int resultLimit) {
		Set<Entry<Integer, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}
		for (Entry<Integer, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	/**
	 * Call a Finder that is specified by a named Query.
	 * 
	 * The Result is limited by the provided result limit.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param resultLimit
	 *            The maximum Results to fetch.
	 * @return The found Entities
	 */
	@SuppressWarnings("rawtypes")
	protected List findWithNamedQuery(final String namedQueryName, final int resultLimit) {
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}
		return query.getResultList();
	}

	/**
	 * Call a Finder that is specified by a named Query.
	 * 
	 * The Result is limited by the provided result limit.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param page
	 *            the page to fetch
	 * @param size
	 *            the page size
	 * @return The found Entities
	 */
	@SuppressWarnings("rawtypes")
	protected List findWithNamedQuery(final String namedQueryName, final Map<Integer, Object> parameters,
			final int page, final int size) {
		Set<Entry<Integer, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (size > 0) {
			query.setMaxResults(size);
			query.setFirstResult((page - 1) * size);
		}
		for (Entry<Integer, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	/**
	 * Call a Finder that is specified by a named Query. *
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @return The found Entities
	 */
	@SuppressWarnings("rawtypes")
	protected List findWithNamedQuery(final String namedQueryName) {
		Query query = this.em.createNamedQuery(namedQueryName);
		return query.getResultList();
	}

	/**
	 * Executes a named query.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @return Number of changed table entries
	 */
	protected int executeNamedQuery(final String namedQueryName, final Map<String, Object> parameters) {
		Query query = this.em.createNamedQuery(namedQueryName);
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.executeUpdate();
	}

	/**
	 * Executes a named native query.
	 * 
	 * @param namedQueryName
	 *            The named query to use.
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @return Number of changed table entries
	 */
	protected int executeNamedNativeQuery(final String namedQueryName, final Map<Integer, Object> parameters) {
		Query query = this.em.createNamedQuery(namedQueryName);
		Set<Entry<Integer, Object>> rawParameters = parameters.entrySet();
		for (Entry<Integer, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.executeUpdate();
	}

	/**
	 * Find a single result with a named query.
	 * 
	 * @param namedQueryName
	 *            The query to use
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @return a single result
	 */
	protected Object findSingleResultWithNamedNativeQuery(final String namedQueryName,
			final Map<Integer, Object> parameters) {
		Set<Entry<Integer, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);

		for (Entry<Integer, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getSingleResult();
	}

	/**
	 * Executes the native query specified by the native query {@link String}
	 * provided.
	 * 
	 * @param nativeQuerySql
	 *            The native query as SQl String
	 * @param parameters
	 *            A {@link Map} of parameters that should be used in the query.
	 * @return The found Entities.
	 */
	@SuppressWarnings(value = "rawtypes")
	protected List findByNativeQuery(final String nativeQuerySql, final Map<Integer, Object> parameters) {
		Query query = this.em.createNativeQuery(nativeQuerySql);
		Set<Entry<Integer, Object>> rawParameters = parameters.entrySet();
		for (Entry<Integer, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	/**
	 * Executes native sql query.
	 * 
	 * @param sqlString
	 *            sql string
	 * @return result list
	 */
	@SuppressWarnings("rawtypes")
	protected List executeNativeQueryNew(final String sqlString) {
		List result = new ArrayList();
		try {
			Query query = this.em.createNativeQuery(sqlString);
			result = query.getResultList();
		} catch (OptimisticLockException e) {
			LOG.error(e.getMessage());
			return result;
		}

		return result;
	}

}
