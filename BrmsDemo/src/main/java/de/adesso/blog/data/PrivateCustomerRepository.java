package de.adesso.blog.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.adesso.blog.model.PrivateCustomer;

public interface PrivateCustomerRepository extends CrudRepository<PrivateCustomer, Long>{

	List<PrivateCustomer> findByLastName(String lastName);
	
}
