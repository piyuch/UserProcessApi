package de.user.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.inject.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * The ObjectMapping is used to map fields between objects. It is based upon
 * dozer and implemented as Singleton to provide only one instance per VM.
 * 
 */
@Default
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ObjectMapping {

	/**
	 * The BEAN_MAPPING_XML.
	 */
	private static final String BEAN_MAPPING_XML = "/paylaxMapping.xml";

	/**
	 * The Logger for this Class.
	 */
	private static final Logger LOG = LogManager.getLogger(ObjectMapping.class);

	/**
	 * The underlying Dozer Mapper.
	 */
	private Mapper mapper;

	/**
	 * Configuration after the object is constructed.
	 */
	@PostConstruct
	public void init() {
		// LOG.info("ObjectMapping.init() start..");

		URL mappingResource = ObjectMapping.class.getResource(BEAN_MAPPING_XML);

		List<String> mappingResources = new ArrayList<String>();
		if (mappingResource != null) {
			mappingResources.add(mappingResource.toExternalForm());
		}

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper(mappingResources);
		if (LOG.isTraceEnabled()) {
			List<String> mappingFiles = dozerBeanMapper.getMappingFiles();
			for (String mappingFile : mappingFiles) {
				LOG.trace("Added Dozer Mapping File: {}.", mappingFile);
			}
		}
		mapper = dozerBeanMapper;
	}

	/**
	 * Maps an object to another Object by the defined Mapping. If non is
	 * defined the default reflection based mapping is used.
	 * 
	 * @param fromObject
	 *            The source object to map from.
	 * @param toClass
	 *            the destination object to map to.
	 * @param <T>
	 *            The type of the destination class.
	 * @return The mapped Object.
	 */
	public <T> T mapObject(final Object fromObject, final Class<T> toClass) {
		T mappedObject = null;
		if (null != fromObject) {
			mappedObject = mapper.map(fromObject, toClass);
		}
		return mappedObject;
	}

	/**
	 * Maps an object to another.
	 * 
	 * @param fromObject
	 *            The object to map from.
	 * @param toObject
	 *            The object to map to.
	 * @return mapped object
	 */
	public Object mapObjectToExistingObject(final Object fromObject, final Object toObject) {
		if (null != fromObject && null != toObject) {
			mapper.map(fromObject, toObject);
		}
		return toObject;
	}

	/**
	 * Maps a Collection to another Collection. Each element is mapped by the
	 * defined mapping rules, if non are defined the default reflection based
	 * mapping is used.
	 * 
	 * @param source
	 *            the source collection to map from.
	 * @param destinationClass
	 *            the destinationClass of the Collection.
	 * @param <T>
	 *            The type of the destination class.
	 * @param <S>
	 *            The type of the source class.
	 * @return The Mapped List from the passed type <T>
	 */
	public <T, S> List<T> mapCollection(final Iterable<S> source, final Class<T> destinationClass) {
		List<T> destination = new ArrayList<T>();
		if (null != source) {
			for (S s : source) {
				destination.add(mapObject(s, destinationClass));
			}
		}
		return destination;
	}
}