package com.ishan.bankingservice.userhistory.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.userhistory.domain.User;
import com.ishan.bankingservice.userhistory.domain.DefaultUserEventHandler;
import com.ishan.bankingservice.userhistory.domain.UserEventHandler;
import com.ishan.bankingservice.userhistory.domain.UserRepository;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserHistoryListener {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private UserRepository userRepository;

  @KafkaListener(
      groupId = "user-history",
      topics = "users",
      concurrency = "1",
      properties = {
          ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest"
      }
  )
  public void listen(String userEvent) throws Exception {
    Map event = mapper.readValue(userEvent, Map.class);
    String eventType = String.valueOf(event.get("eventType"));
    int eventVersion = Integer.parseInt(String.valueOf(event.get("version")));
    Map idMap = (Map) event.get("aggregateId");
    String id = String.valueOf(idMap.get("id"));

    User user = userRepository.findById(id).orElse(new User());

    UserEventHandler userCreatedHandler = new DefaultUserEventHandler();
    userCreatedHandler.next(null);

    userCreatedHandler.handle(eventType, eventVersion, userEvent, mapper, user);

    userRepository.save(user);
  }

}
