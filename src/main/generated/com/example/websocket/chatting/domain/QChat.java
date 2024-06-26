package com.example.websocket.chatting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = -1111876462L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChat chat = new QChat("chat");

    public final com.example.websocket.chatroom.domain.QChatRoom chatroom;

    public final StringPath comment = createString("comment");

    public final StringPath createDate = createString("createDate");

    public final QChatId id;

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final EnumPath<MessageType> messageType = createEnum("messageType", MessageType.class);

    public final com.example.websocket.user.domain.QUser user;

    public QChat(String variable) {
        this(Chat.class, forVariable(variable), INITS);
    }

    public QChat(Path<? extends Chat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChat(PathMetadata metadata, PathInits inits) {
        this(Chat.class, metadata, inits);
    }

    public QChat(Class<? extends Chat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatroom = inits.isInitialized("chatroom") ? new com.example.websocket.chatroom.domain.QChatRoom(forProperty("chatroom")) : null;
        this.id = inits.isInitialized("id") ? new QChatId(forProperty("id")) : null;
        this.user = inits.isInitialized("user") ? new com.example.websocket.user.domain.QUser(forProperty("user")) : null;
    }

}

