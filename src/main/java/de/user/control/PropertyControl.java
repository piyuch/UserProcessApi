package de.user.control;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.model.PropertyEntity;

/**
 * Control to access the properties.
 * 
 * @author piyush chand
 *
 */
@Stateless
public class PropertyControl extends BasicCrudControl {

	private static final Logger LOG = LogManager.getLogger(PropertyControl.class);

	/**
	 * Fetch all properties.
	 * 
	 * @return List with all properties or empty list
	 */
	public List<PropertyEntity> getProperties() {
		List<PropertyEntity> properties = this.findAll(PropertyEntity.class);
		LOG.exit(properties);
		if (properties.isEmpty()) {
			return new ArrayList<PropertyEntity>();
		}
		return properties;
	}

	/**
	 * Update a list of properties.
	 * 
	 * @param properties
	 *            properties to update
	 */
	public void updateProperties(final List<PropertyEntity> properties) {
		LOG.entry(properties);
		for (PropertyEntity propertyEntity : properties) {
			this.update(propertyEntity);
			LOG.exit(propertyEntity);
		}
	}

}
