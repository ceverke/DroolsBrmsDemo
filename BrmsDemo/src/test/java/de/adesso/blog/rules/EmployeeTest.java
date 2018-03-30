package de.adesso.blog.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.adesso.blog.model.JobState;
import de.adesso.blog.model.PrivateCustomer;
import de.adesso.blog.rules.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {

	@Autowired
	private TestUtils testUtils;

	private static final JobState JOBSTATE = JobState.EMPLOYEE;

	private static final double SALARY = 4500.00;

	@Test
	public void testRule_1_2a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0);
		this.checkEmployeeConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1a_2a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, true, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 5.0);
		this.checkEmployeeConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1a_1b_2a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, true, true, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 5.0 + 4.5);
		this.checkEmployeeConditions(privateCustomer);
	}

	@Test
	public void testRule_1a_1b_1c_2a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, true, true, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 5.0 + 4.5 + 9.9);
		this.checkEmployeeConditions(privateCustomer);
	}

	@Test
	public void testRule_1_2a_3b() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateYoungerThan25(), SALARY, false, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 / 2);
		this.checkEmployeeConditions(privateCustomer);
	}

	@Test
	public void testRule_1a_1b_1c_2a_3c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateYoungerThan25(), SALARY, true, true, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo((3.0 + 5.0 + 4.5 + 9.9) / 2);
		this.checkEmployeeConditions(privateCustomer);
	}

	private void checkEmployeeConditions(PrivateCustomer privateCustomer) {
		assertThat(privateCustomer.getBankAccount().getOverdraft()).isEqualTo(SALARY * 0.1);
		assertThat(privateCustomer.getBankAccount().getOverdraftInterest()).isEqualTo(10.9);
	}

}
