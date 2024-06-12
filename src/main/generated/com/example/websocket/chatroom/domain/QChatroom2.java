package com.example.websocket.chatroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoom2 is a Querydsl query type for ChatRoom2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom2 extends EntityPathBase<ChatRoom2> {

    private static final long serialVersionUID = 1237410552L;

    public static final QChatRoom2 chatRoom2 = new QChatRoom2("chatRoom2");

    public final com.example.websocket.config.domain.QBaseTimeEntity _super = new com.example.websocket.config.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath profileImg = createString("profileImg");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QChatRoom2(String variable) {
        super(ChatRoom2.class, forVariable(variable));
    }

    public QChatRoom2(Path<? extends ChatRoom2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom2(PathMetadata metadata) {
        super(ChatRoom2.class, metadata);
    }

}

