package com.ishan.bankingservice.accounts.adapters.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.accounts.adapters.repository.Fact.Status;
import com.ishan.bankingservice.accounts.domain.AccountEvent;
import com.ishan.bankingservice.common.AggregateId;
import com.ishan.bankingservice.common.EventStore;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class AccountEventStore implements EventStore<AccountEvent> {

  @Autowired
  private FactRepository factRepository;

  @Autowired
  private KafkaProducer<String, String> kafkaProducer;

  @Autowired
  private ObjectMapper mapper;

  @Override
  public List<AccountEvent> getAllEvents(AggregateId aggregateId) {
    return this.factRepository.getAllByAggregateIdOrderByRevisionAsc(aggregateId.getId())
        .stream()
        .map(this::toEvent)
        .collect(Collectors.toList());
  }

  @Override
  public void saveAllEvents(AggregateId aggregateId, List<AccountEvent> events) {
    List<Fact> facts = events
        .stream()
        .map(this::toFact)
        .collect(Collectors.toList());

    this.factRepository.saveAll(facts);
  }

  private Fact toFact(AccountEvent event) {
    Fact fact = new Fact();
    fact.setAggregate(AccountEvent.TYPE);
    fact.setAggregateId(event.getAggregateId().getId());
    fact.setRevision(event.getRevision());
    fact.setFactType(event.getEventType());
    fact.setVersion(event.getVersion());
    try {
      fact.setFact(this.mapper.writeValueAsString(event));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    fact.setStatus(Status.PENDING);

    return fact;
  }

  private AccountEvent toEvent(Fact fact) {
    DefaultAccountEventParser defaultAccountEventParser = new DefaultAccountEventParser();
    return defaultAccountEventParser.parse(fact, this.mapper);
  }

  @Scheduled(fixedDelay = 1000)
  public void publishPendingEvents() {
    List<Fact> pendingEvents = this.factRepository
        .getAllByStatusOrderByAggregateIdAscRevisionAsc(Status.PENDING);

    for (Fact event: pendingEvents) {
      try {
        ProducerRecord<String, String> record
            = new ProducerRecord<>(AccountEvent.TYPE, event.getAggregateId(), event.getFact());
        this.kafkaProducer.send(record);
        //todo - Register a callback instead!
        event.setStatus(Status.PROCESSED);
      } catch (Exception e) {
        e.printStackTrace();
        event.setStatus(Status.FAILURE);
      }
    }
    this.factRepository.saveAll(pendingEvents);
  }

}
