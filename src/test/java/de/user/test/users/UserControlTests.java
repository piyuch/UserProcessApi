package de.user.test.users;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.user.control.UserControl;
import de.user.model.UserEntity;
import de.user.test.InContainerTest;

/**
 * This class tests the UserControl
 * 
 * @author piyush
 *
 */

@RunWith(Arquillian.class)
public class UserControlTests extends InContainerTest {

	@Inject
	private UserControl userControl;

	/**
	 * Find user by his email test
	 */
	@Test
	@UsingDataSet("datasets/users/users.yml")
	public void FindUserByEmail_UserEntityReturned() {
		UserEntity userEntity = userControl.findUserByEmail("p.chand@paynrelax.de");
		assertThat(userEntity, notNullValue());
		assertThat(userEntity.isMailVerified(), is(false));
		assertThat(userEntity.isIdentified(), is(false));
		assertThat(userEntity.isDeactivated(), is(false));
		assertThat(userEntity.isNeedsIdentification(), is(false));
		assertThat(userEntity.isRegistered(), is(false));

	}

	/**
	 * Check if user email is verified test
	 */
	@Test
	@UsingDataSet("datasets/users/users.yml")
	public void CheckIfUserMailIsVerified_UserEntityReturned() {
		UserEntity userEntity = userControl.findUserByEmail("h.bensassi@paynrelax.de");
		assertThat(userEntity, notNullValue());
		assertThat(userEntity.getCitizenship(), is(("Germany")));
		assertThat(userEntity.getFirstName(), is(("piyush")));
		assertThat(userEntity.getLastName(), is(("chand")));
		assertThat(userEntity.isMailVerified(), is(true));
		assertThat(userEntity.isIdentified(), is(false));
		assertThat(userEntity.isDeactivated(), is(false));
		assertThat(userEntity.isNeedsIdentification(), is(false));
		assertThat(userEntity.isRegistered(), is(false));
		assertThat(userEntity.getPin(), is("1234"));

	}

	/**
	 * Find user by his id test
	 */
	@Test
	@UsingDataSet("datasets/users/users.yml")
	public void FindUserById_UserEntityReturned() {
		UserEntity userEntity = userControl.findUserById(3);
		assertThat(userEntity, notNullValue());
		assertThat(userEntity.getCitizenship(), is(("Germany")));
		assertThat(userEntity.getFirstName(), is(("piyush")));
		assertThat(userEntity.getLastName(), is(("chand")));
		assertThat(userEntity.isMailVerified(), is(true));
		assertThat(userEntity.isIdentified(), is(false));
		assertThat(userEntity.isDeactivated(), is(false));
		assertThat(userEntity.isNeedsIdentification(), is(false));
		assertThat(userEntity.isRegistered(), is(false));
		assertThat(userEntity.getPin(), is("1234"));

	}

	/**
	 * Find all users test
	 */
	@Test
	@UsingDataSet("datasets/users/users.yml")
	public void FindAllUsers_UsersListReturned() {
		List<UserEntity> users = userControl.findAllUsers();
		assertThat(users, notNullValue());
		assertThat(users.size(), is(13));
	}

}