package com.ishan.bankingservice.iam.adapters;

import com.ishan.bankingservice.iam.adapters.Fact.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FactRepository extends JpaRepository<Fact, String> {

  List<Fact> findAllByAggregateIdOrderByRevisionAsc(String aggregateId);

  List<Fact> findAllByStatusOrderByAggregateIdAscRevisionAsc(Status status);

}
