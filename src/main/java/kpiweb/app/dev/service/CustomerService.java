package kpiweb.app.dev.service;


import kpiweb.app.dev.entity.Customer;
import kpiweb.app.dev.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> findAll() {
        return repo.findAll();
    }

    public Optional<Customer> findById(String id) {
        return repo.findById(id);
    }

    public Customer save(Customer customer) {
        return repo.save(customer);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }
}
