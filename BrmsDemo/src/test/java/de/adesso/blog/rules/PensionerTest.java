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
public class PensionerTest {

	@Autowired
	private TestUtils testUtils;

	private static final JobState JOBSTATE = JobState.PENSIONER;

	private static final double SALARY = 4500.00;

	@Test
	public void testRule_1_2c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1c_2c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, false, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 9.9);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1b_2c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, true, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 4.5);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1b_1c_2c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, true, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 + 4.5 + 9.9);
		this.checkPensionerConditions(privateCustomer);
	}

	private void checkPensionerConditions(PrivateCustomer privateCustomer) {
		assertThat(privateCustomer.getBankAccount().getOverdraft()).isEqualTo(SALARY * 0.05);
		assertThat(privateCustomer.getBankAccount().getOverdraftInterest()).isEqualTo(6.0);
	}

}
