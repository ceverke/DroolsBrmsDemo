package de.adesso.blog.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.blog.data.PrivateCustomerRepository;
import de.adesso.blog.model.JobState;
import de.adesso.blog.model.PrivateCustomer;

@RestController
public class UserController {

	@Autowired
	private PrivateCustomerRepository privateCustomerRepository;

	private KieSession kieSession;

	@Value("${de.adesso.blog.businessrules.bankAccountRulesBase}")
	private String bankAccountRulesBase;

	@RequestMapping(value = "/student/{lastName}/{firstName}/{salary}/{birthday}", method = RequestMethod.POST)
	public PrivateCustomer createRandomStudent(@PathVariable String lastName, @PathVariable String firstName,
			@PathVariable Double salary, @PathVariable String birthday) {
		PrivateCustomer student = new PrivateCustomer();
		student.setJobState(JobState.STUDENT);

		return addPrivateCustomer(student, lastName, firstName, salary, birthday);
	}

	@RequestMapping(value = "/employee/{lastName}/{firstName}/{salary}/{birthday}", method = RequestMethod.POST)
	public PrivateCustomer createRandomEmployee(@PathVariable String lastName, @PathVariable String firstName,
			@PathVariable Double salary, @PathVariable String birthday) {
		PrivateCustomer employee = new PrivateCustomer();
		employee.setJobState(JobState.EMPLOYEE);

		return addPrivateCustomer(employee, lastName, firstName, salary, birthday);
	}

	@RequestMapping(value = "/pensioner/{lastName}/{firstName}/{salary}/{birthday}", method = RequestMethod.POST)
	public PrivateCustomer createRandomPensioneer(@PathVariable String lastName, @PathVariable String firstName,
			@PathVariable Double salary, @PathVariable String birthday) {
		PrivateCustomer pensioner = new PrivateCustomer();
		pensioner.setJobState(JobState.PENSIONER);

		return addPrivateCustomer(pensioner, lastName, firstName, salary, birthday);
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<PrivateCustomer> getAllPrivateCustomers() {
		return (List<PrivateCustomer>) privateCustomerRepository.findAll();
	}

	@RequestMapping(value = "/customers/{lastName}", method = RequestMethod.GET)
	public List<PrivateCustomer> getPrivateCustomersByLastName(@PathVariable String lastName) {
		return (List<PrivateCustomer>) privateCustomerRepository.findByLastName(lastName);
	}

	private PrivateCustomer addPrivateCustomer(PrivateCustomer customer, String lastName, String firstName,
			Double salary, String birthday) {
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setMonthlySalaray(salary);
		customer.setBirthDate(parseStringBirthday(birthday));
		customer.setNonOnlineTransferals(getRandomBoolean());
		customer.setSecondCard(getRandomBoolean());
		customer.setGoldCard(getRandomBoolean());
		privateCustomerRepository.save(calculateAccountConditions(customer));
		return customer;
	}

	private boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}

	private Date parseStringBirthday(String birthday) {
		// Expected format: yyyy-MM-dd, e.g. 1991-10-02 if somebody was born at 10th
		// February, 1991
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date bDay;
		try {
			bDay = dateFormat.parse(birthday);
		} catch (ParseException e) {
			throw new RuntimeException("Wrong parameter format. Expected yyy-MM-dd");
		}
		return bDay;
	}

	private PrivateCustomer calculateAccountConditions(PrivateCustomer privateCustomer) {
		KieServices kieService = KieServices.Factory.get();
		KieContainer kieContainer = kieService.getKieClasspathContainer(getClass().getClassLoader());

		try {
			kieSession = kieContainer.getKieBase(bankAccountRulesBase).newKieSession();
			kieSession.insert(privateCustomer);
			kieSession.fireAllRules();
		} catch (Exception anyException) {
			throw new RuntimeException("Drools session could not be instantiated");
		}
		return privateCustomer;
	}

}
