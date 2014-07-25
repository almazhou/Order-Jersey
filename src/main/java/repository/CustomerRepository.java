package repository;

import resources.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> getAllCustomers();

    Customer getCustomerWithId(String id);

    void save(Customer customer);
}
