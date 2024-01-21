package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.repository.CustomerRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.example.finalprojectphasetwo.service.dto.UserSingUpDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl
        extends UserServiceImpl<Customer>
        implements CustomerService {

    public CustomerServiceImpl(UserRepository<Customer> repository, CustomerRepository customerRepository) {
        super(repository);
    }

    @Override
    public void customerSingUp(UserSingUpDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmailAddress(dto.getEmailAddress());
        customer.setUsername(dto.getUsername());
        customer.setPassword(dto.getPassword());
        customer.setActive(true);
        customer.setHasPermission(false);
        customer.setWallet(dto.getWallet());
        repository.save(customer);
    }

}