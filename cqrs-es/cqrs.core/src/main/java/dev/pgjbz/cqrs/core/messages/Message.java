package dev.pgjbz.cqrs.core.messages;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;



public interface Message {
    String id();
}
