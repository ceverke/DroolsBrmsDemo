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
public class StudentTest {

	@Autowired
	private TestUtils testUtils;

	private static final JobState JOBSTATE = JobState.STUDENT;

	private static final double SALARY = 4500.00;

	@Test
	public void testRule_1_2b_3a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, false, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 / 2);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1a_1c_2b_3a() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateOlderThan25(), SALARY, true, false, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo((3.0 + 5.0 + 9.9) / 2);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_2b_3c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateYoungerThan25(), SALARY, false, false, false));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo(3.0 * 0.4);
		this.checkPensionerConditions(privateCustomer);
	}

	@Test
	public void testRule_1_1a_1c_2b_3c() {
		PrivateCustomer privateCustomer = testUtils.callCalculateAccountConditions(
				testUtils.getTestObject(JOBSTATE, testUtils.getDateYoungerThan25(), SALARY, true, false, true));
		assertThat(privateCustomer.getBankAccount().getCostPerMonth()).isEqualTo((3.0 + 5.0 + 9.9) * 0.4);
		this.checkPensionerConditions(privateCustomer);
	}

	private void checkPensionerConditions(PrivateCustomer privateCustomer) {
		assertThat(privateCustomer.getBankAccount().getOverdraft()).isEqualTo(0);
		assertThat(privateCustomer.getBankAccount().getOverdraftInterest()).isEqualTo(0);
	}

}
