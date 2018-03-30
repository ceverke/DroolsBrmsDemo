package de.adesso.blog.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.blog.api.UserController;
import de.adesso.blog.model.JobState;
import de.adesso.blog.model.PrivateCustomer;

@Service
public class TestUtils {

	@Autowired
	private UserController userController;

	public PrivateCustomer getTestObject(JobState jobState, Date birthday, Double salary, boolean nonOnlineTransferals,
			boolean secondCard, boolean goldCard) {
		PrivateCustomer customer = new PrivateCustomer();
		customer.setFirstName("Dummy");
		customer.setLastName("Test");
		customer.setJobState(jobState);
		customer.setMonthlySalaray(salary);
		customer.setBirthDate(birthday);
		customer.setNonOnlineTransferals(nonOnlineTransferals);
		customer.setSecondCard(secondCard);
		customer.setGoldCard(goldCard);
		customer.setBankAccount(null);

		return customer;
	}

	/**
	 * To test the rules it is necessary to test the
	 * "caluclateAccountConditions"-method, implemented in userController. As it is
	 * private, it is accessed with the help of reflections.
	 * 
	 * @param customer
	 *            - test object
	 * @return calculated customer - null in case of error
	 */
	public PrivateCustomer callCalculateAccountConditions(PrivateCustomer customer) {
		try {
			Method caluclateAccountConditions = userController.getClass()
					.getDeclaredMethod("calculateAccountConditions", PrivateCustomer.class);
			caluclateAccountConditions.setAccessible(true);
			return (PrivateCustomer) caluclateAccountConditions.invoke(userController, customer);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Date getDateOlderThan25() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date bDay = null;
		try {
			bDay = dateFormat.parse("1991-02-10");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return bDay;
	}

	public Date getDateYoungerThan25() {
		return new Date(System.currentTimeMillis());
	}

}
