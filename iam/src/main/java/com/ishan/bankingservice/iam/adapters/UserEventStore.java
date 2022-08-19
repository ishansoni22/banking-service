package com.ishan.bankingservice.iam.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.common.AggregateId;
import com.ishan.bankingservice.common.EventStore;
import com.ishan.bankingservice.iam.adapters.Fact.Status;
import com.ishan.bankingservice.iam.domain.UserCreated;
import com.ishan.bankingservice.iam.domain.UserEmailUpdated;
import com.ishan.bankingservice.iam.domain.UserEvent;
import com.ishan.bankingservice.iam.domain.UserNameUpdated;
import com.ishan.bankingservice.iam.domain.UserPanUpdated;
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
    UserEvent userEvent = null;
    try {
      String type = userFact.getFactType();
      if ("UserCreated".equals(type)){
        userEvent = mapper.readValue(userFact.getFact(), UserCreated.class);
      } else if ("UserNameUpdated".equals(type)) {
        userEvent = mapper.readValue(userFact.getFact(), UserNameUpdated.class);
      } else if ("UserEmailUpdated".equals(type)) {
        userEvent = mapper.readValue(userFact.getFact(), UserEmailUpdated.class);
      } else if ("UserPanUpdated".equals(type)) {
        userEvent = mapper.readValue(userFact.getFact(), UserPanUpdated.class);
      }
    } catch (Exception e) {
     log.error("Exception: Cannot parse fact -> event", e);
    }
    return userEvent;
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

  @Scheduled(cron = "* * * * * *")
  public void publishPendingEvents() {
    List<Fact> pendingEvents
        = factRepository.findAllByStatusOrderByAggregateIdAscRevisionAsc(Status.PENDING);
    for (Fact fact: pendingEvents) {
      try {
        ProducerRecord<String, String> record
            = new ProducerRecord<>(fact.getAggregate(), fact.getAggregateId(), fact.getFact());
        producer.send(record);
        fact.setStatus(Status.PROCESSED);
      } catch (Exception e) {
        log.error("Unable to publish user event {} to kafka", fact.getId(),  e);
        fact.setStatus(Status.FAILURE);
      }
    }
    factRepository.saveAll(pendingEvents);
  }

}
