package com.example.websocket.chatroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatroom2 is a Querydsl query type for Chatroom2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatroom2 extends EntityPathBase<Chatroom2> {

    private static final long serialVersionUID = 1266963224L;

    public static final QChatroom2 chatroom2 = new QChatroom2("chatroom2");

    public final com.example.websocket.config.domain.QBaseTimeEntity _super = new com.example.websocket.config.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath profileImg = createString("profileImg");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QChatroom2(String variable) {
        super(Chatroom2.class, forVariable(variable));
    }

    public QChatroom2(Path<? extends Chatroom2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatroom2(PathMetadata metadata) {
        super(Chatroom2.class, metadata);
    }

}

