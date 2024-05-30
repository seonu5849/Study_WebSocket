package com.example.websocket.chatting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatId is a Querydsl query type for ChatId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QChatId extends BeanPath<ChatId> {

    private static final long serialVersionUID = 933579085L;

    public static final QChatId chatId1 = new QChatId("chatId1");

    public final NumberPath<Long> chatId = createNumber("chatId", Long.class);

    public final NumberPath<Long> chatroomId = createNumber("chatroomId", Long.class);

    public QChatId(String variable) {
        super(ChatId.class, forVariable(variable));
    }

    public QChatId(Path<? extends ChatId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatId(PathMetadata metadata) {
        super(ChatId.class, metadata);
    }

}

