package kernel.jdon.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 186973844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final EnumPath<MemberAccountStatus> accountStatus = createEnum("accountStatus", MemberAccountStatus.class);

    public final StringPath birth = createString("birth");

    public final StringPath email = createString("email");

    public final ListPath<kernel.jdon.favorite.domain.Favorite, kernel.jdon.favorite.domain.QFavorite> favoriteList = this.<kernel.jdon.favorite.domain.Favorite, kernel.jdon.favorite.domain.QFavorite>createList("favoriteList", kernel.jdon.favorite.domain.Favorite.class, kernel.jdon.favorite.domain.QFavorite.class, PathInits.DIRECT2);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final ListPath<kernel.jdon.coffeechatmember.domain.CoffeeChatMember, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember> guestChatList = this.<kernel.jdon.coffeechatmember.domain.CoffeeChatMember, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember>createList("guestChatList", kernel.jdon.coffeechatmember.domain.CoffeeChatMember.class, kernel.jdon.coffeechatmember.domain.QCoffeeChatMember.class, PathInits.DIRECT2);

    public final ListPath<kernel.jdon.coffeechat.domain.CoffeeChat, kernel.jdon.coffeechat.domain.QCoffeeChat> hostChatList = this.<kernel.jdon.coffeechat.domain.CoffeeChat, kernel.jdon.coffeechat.domain.QCoffeeChat>createList("hostChatList", kernel.jdon.coffeechat.domain.CoffeeChat.class, kernel.jdon.coffeechat.domain.QCoffeeChat.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.jobcategory.domain.QJobCategory jobCategory;

    public final DateTimePath<java.time.LocalDateTime> joinDate = createDateTime("joinDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final ListPath<kernel.jdon.memberskill.domain.MemberSkill, kernel.jdon.memberskill.domain.QMemberSkill> memberSkillList = this.<kernel.jdon.memberskill.domain.MemberSkill, kernel.jdon.memberskill.domain.QMemberSkill>createList("memberSkillList", kernel.jdon.memberskill.domain.MemberSkill.class, kernel.jdon.memberskill.domain.QMemberSkill.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public final EnumPath<SocialProviderType> socialProvider = createEnum("socialProvider", SocialProviderType.class);

    public final DateTimePath<java.time.LocalDateTime> withdrawDate = createDateTime("withdrawDate", java.time.LocalDateTime.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobCategory = inits.isInitialized("jobCategory") ? new kernel.jdon.jobcategory.domain.QJobCategory(forProperty("jobCategory")) : null;
    }

}

