package com.example.websocket.friend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriendId is a Querydsl query type for FriendId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFriendId extends BeanPath<FriendId> {

    private static final long serialVersionUID = 599766763L;

    public static final QFriendId friendId1 = new QFriendId("friendId1");

    public final NumberPath<Long> friendId = createNumber("friendId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFriendId(String variable) {
        super(FriendId.class, forVariable(variable));
    }

    public QFriendId(Path<? extends FriendId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriendId(PathMetadata metadata) {
        super(FriendId.class, metadata);
    }

}

