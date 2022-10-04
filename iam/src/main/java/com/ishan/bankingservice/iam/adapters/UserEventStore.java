package com.ishan.bankingservice.iam.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.common.AggregateId;
import com.ishan.bankingservice.common.EventStore;
import com.ishan.bankingservice.iam.adapters.Fact.Status;
import com.ishan.bankingservice.iam.domain.UserEvent;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
class UserEventStore implements EventStore<UserEvent> {

  @Autowired
  private FactRepository factRepository;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private KafkaProducer<String, String> producer;

  @Override
  public List<UserEvent> getAllEvents(AggregateId aggregateId) {
    return factRepository.findAllByAggregateIdOrderByRevisionAsc(aggregateId.getId())
        .stream()
        .map(this::from)
        .collect(Collectors.toList());
  }

  @Override
  public void saveAllEvents(AggregateId aggregateId, List<UserEvent> changes) {
    factRepository.saveAll(changes.stream().map(this::to).collect(Collectors.toList()));
  }

  private UserEvent from(Fact userFact) {
    UserEventParser defaultParser = new DefaultUserEventParser();

    UserEventParser v1Parser = new UserEventParserV1();
    v1Parser.setNext(defaultParser);

    return v1Parser.parse(userFact, mapper);
  }

  private Fact to(UserEvent userEvent) {
    Fact fact = new Fact();
    fact.setAggregate(UserEvent.TYPE);
    fact.setAggregateId(userEvent.getAggregateId().getId());
    fact.setVersion(userEvent.getVersion());
    fact.setRevision(userEvent.getRevision());
    fact.setFactType(userEvent.getEventType());
    try {
      fact.setFact(mapper.writeValueAsString(userEvent));
    } catch (Exception e) {
      log.error("Exception: Cannot parse event -> fact", e);
    }
    fact.setStatus(Status.PENDING);
    return fact;
  }

  @Scheduled(fixedDelay = 1000)
  public void publishPendingEvents() {
    List<Fact> pendingEvents
        = factRepository.findAllByStatusOrderByAggregateIdAscRevisionAsc(Status.PENDING);
    for (Fact event: pendingEvents) {
      try {
        ProducerRecord<String, String> record
            = new ProducerRecord<>(event.getAggregate(), event.getAggregateId(), event.getFact());
        producer.send(record);
        //todo - Register a callback instead!
        event.setStatus(Status.PROCESSED);
      } catch (Exception e) {
        log.error("Unable to publish user event {} to kafka", event.getId(),  e);
        event.setStatus(Status.FAILURE);
      }
    }
    factRepository.saveAll(pendingEvents);
  }

}
