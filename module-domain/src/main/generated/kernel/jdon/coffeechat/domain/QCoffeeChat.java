package kernel.jdon.coffeechat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoffeeChat is a Querydsl query type for CoffeeChat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoffeeChat extends EntityPathBase<CoffeeChat> {

    private static final long serialVersionUID = -1077945784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoffeeChat coffeeChat = new QCoffeeChat("coffeeChat");

    public final kernel.jdon.base.QBaseEntity _super = new kernel.jdon.base.QBaseEntity(this);

    public final ListPath<kernel.jdon.coffeechatmember.domain.CoffeeChatMember, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember> coffeeChatMemberList = this.<kernel.jdon.coffeechatmember.domain.CoffeeChatMember, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember>createList("coffeeChatMemberList", kernel.jdon.coffeechatmember.domain.CoffeeChatMember.class, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember.class, PathInits.DIRECT2);

    public final EnumPath<CoffeeChatActiveStatus> coffeeChatStatus = createEnum("coffeeChatStatus", CoffeeChatActiveStatus.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> currentRecruitCount = createNumber("currentRecruitCount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> meetDate = createDateTime("meetDate", java.time.LocalDateTime.class);

    public final kernel.jdon.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath openChatUrl = createString("openChatUrl");

    public final StringPath title = createString("title");

    public final NumberPath<Long> totalRecruitCount = createNumber("totalRecruitCount", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QCoffeeChat(String variable) {
        this(CoffeeChat.class, forVariable(variable), INITS);
    }

    public QCoffeeChat(Path<? extends CoffeeChat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoffeeChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoffeeChat(PathMetadata metadata, PathInits inits) {
        this(CoffeeChat.class, metadata, inits);
    }

    public QCoffeeChat(Class<? extends CoffeeChat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kernel.jdon.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

