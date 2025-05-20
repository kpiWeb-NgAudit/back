package kpiweb.app.dev.service;

import kpiweb.app.dev.dto.FactCreateDTO;
import kpiweb.app.dev.dto.FactUpdateDTO;
import kpiweb.app.dev.entity.Fact;

import java.util.List;
import java.util.Optional;

 public interface FactService {
     Fact createFact(FactCreateDTO factCreateDTO);
     Optional<Fact> getFactById(Integer factId);
     List<Fact> getAllFacts();
     Fact updateFact(Integer factId, FactUpdateDTO factUpdateDTO);
     void deleteFact(Integer factId);
 }