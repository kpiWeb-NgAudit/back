package kpiweb.app.dev.service;

import kpiweb.app.dev.entity.Fact;
import kpiweb.app.dev.exception.ResourceNotFoundException;
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

    @Autowired
    public FactServiceImpl(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    @Override
    @Transactional // Good practice for operations that modify data
    public Fact createFact(Fact fact) {
        // You might want to set some default values or perform validation here
        // e.g., if fact_lastupdate should be set on creation:
        fact.setFactLastupdate(LocalDateTime.now());
        return factRepository.save(fact);
    }

    @Override
    @Transactional(readOnly = true) // Good for read operations
    public Optional<Fact> getFactById(Integer factId) {
        return factRepository.findById(factId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fact> getAllFacts() {
        return factRepository.findAll();
    }

    @Override
    @Transactional
    public Fact updateFact(Integer factId, Fact factDetails) {
        Fact existingFact = factRepository.findById(factId)
                .orElseThrow(() -> new ResourceNotFoundException("Fact", "id", factId));

        // Update fields from factDetails to existingFact
        // Be careful not to overwrite the ID or version (timestamp) if managed by JPA
        existingFact.setFactTname(factDetails.getFactTname());
        existingFact.setFactType(factDetails.getFactType());
        existingFact.setFactdbextrIdPk(factDetails.getFactdbextrIdPk());
        existingFact.setFactProccube(factDetails.getFactProccube());
        existingFact.setFactShortcubename(factDetails.getFactShortcubename());
        existingFact.setFactShortpresname(factDetails.getFactShortpresname());
        existingFact.setFactWorkorder(factDetails.getFactWorkorder());
        existingFact.setCubeIdPk(factDetails.getCubeIdPk());
        existingFact.setFactFactdatafiletype(factDetails.getFactFactdatafiletype());
        existingFact.setFactFactdatafilename(factDetails.getFactFactdatafilename());
        existingFact.setFactFactdatafilecheckunicity(factDetails.getFactFactdatafilecheckunicity());
        existingFact.setFactZonespe(factDetails.getFactZonespe());
        existingFact.setFactLastupdate(LocalDateTime.now()); // Or from factDetails if user can set it
        existingFact.setFactComments(factDetails.getFactComments());
        existingFact.setFactPartitiontype(factDetails.getFactPartitiontype());
        // The 'factTimestamp' (rowversion) will be handled by JPA's @Version mechanism automatically

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
