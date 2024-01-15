package kernel.jdon.coffeechatmember.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoffeeChatMember is a Querydsl query type for CoffeeChatMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoffeeChatMember extends EntityPathBase<CoffeeChatMember> {

    private static final long serialVersionUID = 822954396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoffeeChatMember coffeeChatMember = new QCoffeeChatMember("coffeeChatMember");

    public final kernel.jdon.coffeechat.domain.QCoffeeChat coffeeChat;

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.member.domain.QMember member;

    public QCoffeeChatMember(String variable) {
        this(CoffeeChatMember.class, forVariable(variable), INITS);
    }

    public QCoffeeChatMember(Path<? extends CoffeeChatMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoffeeChatMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoffeeChatMember(PathMetadata metadata, PathInits inits) {
        this(CoffeeChatMember.class, metadata, inits);
    }

    public QCoffeeChatMember(Class<? extends CoffeeChatMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coffeeChat = inits.isInitialized("coffeeChat") ? new kernel.jdon.coffeechat.domain.QCoffeeChat(forProperty("coffeeChat"), inits.get("coffeeChat")) : null;
        this.member = inits.isInitialized("member") ? new kernel.jdon.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

