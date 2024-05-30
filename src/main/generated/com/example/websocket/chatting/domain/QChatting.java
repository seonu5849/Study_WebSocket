package com.example.websocket.chatting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatting is a Querydsl query type for Chatting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatting extends EntityPathBase<Chatting> {

    private static final long serialVersionUID = -477374848L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatting chatting = new QChatting("chatting");

    public final com.example.websocket.config.domain.QBaseTimeEntity _super = new com.example.websocket.config.domain.QBaseTimeEntity(this);

    public final QChatId chatId;

    public final com.example.websocket.chatroom.domain.QChatroom2 chatroom2;

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath isChecked = createBoolean("isChecked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final com.example.websocket.user.domain.QUser user;

    public QChatting(String variable) {
        this(Chatting.class, forVariable(variable), INITS);
    }

    public QChatting(Path<? extends Chatting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatting(PathMetadata metadata, PathInits inits) {
        this(Chatting.class, metadata, inits);
    }

    public QChatting(Class<? extends Chatting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatId = inits.isInitialized("chatId") ? new QChatId(forProperty("chatId")) : null;
        this.chatroom2 = inits.isInitialized("chatroom2") ? new com.example.websocket.chatroom.domain.QChatroom2(forProperty("chatroom2")) : null;
        this.user = inits.isInitialized("user") ? new com.example.websocket.user.domain.QUser(forProperty("user")) : null;
    }

}

