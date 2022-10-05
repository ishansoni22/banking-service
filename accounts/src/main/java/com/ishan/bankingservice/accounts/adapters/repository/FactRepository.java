package com.ishan.bankingservice.accounts.adapters.repository;

import com.ishan.bankingservice.accounts.adapters.repository.Fact.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, String> {

  List<Fact> getAllByAggregateIdOrderByRevisionAsc(String aggregateId);

  List<Fact> getAllByStatusOrderByAggregateIdAscRevisionAsc(Status status);

}
