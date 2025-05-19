package kpiweb.app.dev.service;


import jakarta.persistence.EntityNotFoundException;
import kpiweb.app.dev.dto.CustomerDTO;
import kpiweb.app.dev.entity.Customer;
import kpiweb.app.dev.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;

    // Convert entity → DTO
    private CustomerDTO toDto(Customer c) {
        return CustomerDTO.builder()
                .cubeIdPk(c.getCubeIdPk())
                .cubeNumber(c.getCubeNumber())
                .cubeName(c.getCubeName())
                .custGeocode(c.getCustGeocode())
                .custTown(c.getCustTown())
                .custCountry(c.getCustCountry())
                .custCubetype(c.getCustCubetype())
                .custLanguage(c.getCustLanguage())
                .cubeLastupdate(c.getCubeLastupdate())
                .cubeLastprocess(c.getCubeLastprocess())
                .custRefreshfrq(c.getCustRefreshfrq())
                .custCharseparator(c.getCustCharseparator())
                .custLimitrdlfilter(c.getCustLimitrdlfilter())
                .custShowfiscmeasureandset(c.getCustShowfiscmeasureandset())
                .custShowpctdifferencebase100(c.getCustShowpctdifferencebase100())
                .build();
    }

    // Convert DTO → entity (for create/update)
    private Customer toEntity(CustomerDTO dto) {
        return Customer.builder()
                .cubeIdPk(dto.getCubeIdPk())
                .cubeNumber(dto.getCubeNumber())
                .cubeName(dto.getCubeName())
                .custGeocode(dto.getCustGeocode())
                .custTown(dto.getCustTown())
                .custCountry(dto.getCustCountry())
                .custCubetype(dto.getCustCubetype())
                .custLanguage(dto.getCustLanguage())
                .cubeLastupdate(dto.getCubeLastupdate())
                .cubeLastprocess(dto.getCubeLastprocess())
                .custRefreshfrq(dto.getCustRefreshfrq())
                .custCharseparator(dto.getCustCharseparator())
                .custLimitrdlfilter(dto.getCustLimitrdlfilter())
                .custShowfiscmeasureandset(dto.getCustShowfiscmeasureandset())
                .custShowpctdifferencebase100(dto.getCustShowpctdifferencebase100())
                .build();
    }

    // List all
    public List<CustomerDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Get by id
    public CustomerDTO findById(String id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    // Create
    public CustomerDTO create(CustomerDTO dto) {
        Customer saved = repo.save(toEntity(dto));
        return toDto(saved);
    }

    // Update
    public CustomerDTO update(String id, CustomerDTO dto) {
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        // copy updatable fields
        existing.setCubeName(dto.getCubeName());
        existing.setCustTown(dto.getCustTown());
        // …etc for other updatable fields…
        return toDto(repo.save(existing));
    }

    // Delete
    public void delete(String id) {
        repo.deleteById(id);
    }
}
