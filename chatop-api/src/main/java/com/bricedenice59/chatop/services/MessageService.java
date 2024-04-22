package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.models.Message;
import com.bricedenice59.chatop.repositories.MessageRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
