package de.user.test;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import de.user.web.RestPaylax;

/**
 * Abstract test class that all REST Test can use.
 */
public abstract class InContainerTest {

	/**
	 * Create the Web Archive.
	 * 
	 * @return the web archive
	 */
	@Deployment(testable = true)
	public static final WebArchive createDeployment() {
		// loads the pom configuration
		File[] dependencies = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();
		// loads the mockito framework for testing
		File mockito = Maven.resolver().loadPomFromFile("pom.xml").resolve("org.mockito:mockito-all:1.10.19")
				.withTransitivity().asSingleFile();
		// adds the package for paylax pointing to the RestPaylax api
		WebArchive war = ShrinkWrap.create(WebArchive.class).addPackages(true, "de.paylax").addClass(RestPaylax.class)
				.addAsLibraries(dependencies).addAsLibraries(mockito)
				// adds the test perisistence xml configuration
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				// adds the test beans.xml and the log4j2.xml
				.addAsResource("test-beans.xml", "META-INF/beans.xml").addAsResource("log4j2.xml", "log4j2.xml")
				// adds the paylaxMapping.xml
				.addAsResource("paylaxMapping.xml", "paylaxMapping.xml")
				.addAsResource("HTMLEmailTemplate/head.tmpl", "HTMLEmailTemplate/base.tmpl")
				.addAsResource("HTMLEmailTemplate/head.tmpl", "HTMLEmailTemplate/head.tmpl")
				.addAsResource("HTMLEmailTemplate/header.tmpl", "HTMLEmailTemplate/header.tmpl")
				.addAsResource("HTMLEmailTemplate/footer.tmpl", "HTMLEmailTemplate/footer.tmpl")
				.addAsResource("HTMLEmailTemplate/users-content/content-password-reset-mail.tmpl",
						"HTMLEmailTemplate/users-content/content-password-reset-mail.tmpl")
				.addAsResource("HTMLEmailTemplate/users-content/content-verification-mail.tmpl",
						"HTMLEmailTemplate/users-content/content-verification-mail.tmpl")
				.addAsResource("HTMLEmailTemplate/users-content/content-invitepayer-mail.tmpl",
						"HTMLEmailTemplate/users-content/content-invitepayer-mail.tmpl")
				.addAsResource("datasets/scripts/contracts.sql", "datasets/scripts/contracts.sql")
				.addAsResource("datasets/users/login.json", "datasets/users/login.json");
		;
		;
		return war;
	}

}
