package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import repository.RepositoryFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import util.Type;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository= RepositoryFactory.getInstance().getRepositoryType(Type.CUSTOMER);

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        List<CustomerEntity> customerEntityList = customerRepository.findAll();

        customerEntityList.forEach(customerEntity -> {
            customers.add(new Customer(
                    customerEntity.getCustomerId(),
                    customerEntity.getName(),
                    customerEntity.getEmail(),
                    customerEntity.getPhoneNumber())
            );
        });
        return customers;
    }
}
