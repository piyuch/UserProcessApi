package de.user.properties;

import java.util.HashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.control.PropertyControl;

/**
 * Class for handling properties. It loads them from the database and caches
 * them for 2 hours. If there are no properties in the cache the class will try
 * to load them from the database.
 *
 * @author claas.buedding
 */
@Singleton
@Startup
public class PropertiesHandler {

	@Inject
	private PropertyControl propertyControl;

	/**
	 * The logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger(PropertiesHandler.class);

	private HashMap<String, String> properties;

	/**
	 * Initialize the properties handler.
	 */
	@PostConstruct
	public void initProperties() {
		this.properties = loadProperties();
	}

	/**
	 * Get a single property.
	 *
	 * @param key
	 *            The key of the property.
	 * @return value of the requested property.
	 */
	public String getProperty(final String key) {
		if (this.properties.isEmpty()) {
			// try to load the properties if the are empty
			this.initProperties();
		}
		return this.properties.get(key);
	}

	/**
	 * Get a single property.
	 * 
	 * @param key
	 *            The key of the property.
	 * @param defaultValue
	 *            default value if property is not available
	 * @return the property or the default value
	 */
	public String getProperty(final String key, final String defaultValue) {
		String value = this.properties.get(key);
		if (null == value || value.isEmpty()) {
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * Load properties from database.
	 *
	 * @return the HashMap with the properties from the database.
	 */
	private HashMap<String, String> loadProperties() {
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.putAll(propertyControl.getProperties().stream().filter(p -> p.getKey() != null)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())));
		LOG.entry(properties);
		return properties;
	}

	/**
	 * Reload the properties every 2 hours.
	 */
	@Schedule(hour = "*/2", minute = "50")
	private void reloadProperties() {
		this.initProperties();
	}
}
