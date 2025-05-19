package kpiweb.app.dev.service;

import kpiweb.app.dev.entity.Fact;

import java.util.List;
import java.util.Optional;

public interface FactService {
    Fact createFact(Fact fact);
    Optional<Fact> getFactById(Integer factId);
    List<Fact> getAllFacts();
    Fact updateFact(Integer factId, Fact factDetails);
    void deleteFact(Integer factId);
}