package com.example.websocket.chatroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInviteChatId is a Querydsl query type for InviteChatId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInviteChatId extends BeanPath<InviteChatId> {

    private static final long serialVersionUID = -975389789L;

    public static final QInviteChatId inviteChatId = new QInviteChatId("inviteChatId");

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QInviteChatId(String variable) {
        super(InviteChatId.class, forVariable(variable));
    }

    public QInviteChatId(Path<? extends InviteChatId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInviteChatId(PathMetadata metadata) {
        super(InviteChatId.class, metadata);
    }

}

