package kpiweb.app.dev.service;

import kpiweb.app.dev.dto.FactCreateDTO;
import kpiweb.app.dev.dto.FactUpdateDTO;
import kpiweb.app.dev.entity.*;
import kpiweb.app.dev.exception.ResourceNotFoundException;
import kpiweb.app.dev.repository.CustomerRepository;
import kpiweb.app.dev.repository.FactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FactServiceImpl implements FactService {

    private final FactRepository factRepository;
    private final CustomerRepository customerRepository;
    // private final FactMapper factMapper;

    @Autowired
    public FactServiceImpl(FactRepository factRepository, CustomerRepository customerRepository /*, FactMapper factMapper */) {
        this.factRepository = factRepository;
        this.customerRepository = customerRepository;
        // this.factMapper = factMapper;
    }

    // Méthode createFact modifiée pour prendre un DTO
    @Transactional
    public Fact createFact(FactCreateDTO dto) {
        Fact fact = new Fact();

        fact.setFactTname(dto.getFactTname());
        fact.setFactType(dto.getFactType());
        fact.setFactdbextrIdPk(dto.getFactdbextrIdPk());

        if (dto.getFactProccube() != null && !dto.getFactProccube().isEmpty()) {
            fact.setFactProccube(dto.getFactProccube());
        } else {
            fact.setFactProccube("FPROCY");
        }

        fact.setFactShortcubename(dto.getFactShortcubename());
        fact.setFactShortpresname(dto.getFactShortpresname());
        fact.setFactWorkorder(dto.getFactWorkorder());

        Customer managedCustomer = customerRepository.findById(dto.getCustomerCubeIdPk())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "cubeIdPk", dto.getCustomerCubeIdPk()));
        fact.setCustomer(managedCustomer);

        fact.setFactFactdatafiletype(dto.getFactFactdatafiletype());
        fact.setFactFactdatafilename(dto.getFactFactdatafilename());
        fact.setFactFactdatafilecheckunicity(dto.getFactFactdatafilecheckunicity());
        fact.setFactZonespe(dto.getFactZonespe());

        fact.setFactLastupdate(LocalDateTime.now());
        fact.setFactComments(dto.getFactComments());
        fact.setFactPartitiontype(dto.getFactPartitiontype());

        return factRepository.save(fact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Fact> getFactById(Integer factId) {
        return factRepository.findById(factId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fact> getAllFacts() {
        return factRepository.findAll();
    }

    // Méthode updateFact modifiée pour prendre un DTO
    @Transactional
    public Fact updateFact(Integer factId, FactUpdateDTO dto) {
        Fact existingFact = factRepository.findById(factId)
                .orElseThrow(() -> new ResourceNotFoundException("Fact", "id", factId));

        existingFact.setFactTname(dto.getFactTname());
        existingFact.setFactType(dto.getFactType());
        existingFact.setFactFactdatafiletype(dto.getFactFactdatafiletype());
        existingFact.setFactZonespe(dto.getFactZonespe());
        existingFact.setFactPartitiontype(dto.getFactPartitiontype());

        existingFact.setFactLastupdate(LocalDateTime.now());
        return factRepository.save(existingFact);
    }

    @Override
    @Transactional
    public void deleteFact(Integer factId) {
        if (!factRepository.existsById(factId)) {
            throw new ResourceNotFoundException("Fact", "id", factId);
        }
        factRepository.deleteById(factId);
    }
}