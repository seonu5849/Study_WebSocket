package com.example.websocket.chatroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInviteChat is a Querydsl query type for InviteChat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInviteChat extends EntityPathBase<InviteChat> {

    private static final long serialVersionUID = -1699337112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInviteChat inviteChat = new QInviteChat("inviteChat");

    public final QChatRoom chatroom;

    public final QInviteChatId inviteChatId;

    public final com.example.websocket.user.domain.QUser user;

    public QInviteChat(String variable) {
        this(InviteChat.class, forVariable(variable), INITS);
    }

    public QInviteChat(Path<? extends InviteChat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInviteChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInviteChat(PathMetadata metadata, PathInits inits) {
        this(InviteChat.class, metadata, inits);
    }

    public QInviteChat(Class<? extends InviteChat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatroom = inits.isInitialized("chatroom") ? new QChatRoom(forProperty("chatroom")) : null;
        this.inviteChatId = inits.isInitialized("inviteChatId") ? new QInviteChatId(forProperty("inviteChatId")) : null;
        this.user = inits.isInitialized("user") ? new com.example.websocket.user.domain.QUser(forProperty("user")) : null;
    }

}

