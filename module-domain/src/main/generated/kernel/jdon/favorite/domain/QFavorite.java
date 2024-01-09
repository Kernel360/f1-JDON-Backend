package kernel.jdon.favorite.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = 281313560L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.inflearn.domain.QInflearnCourse inflearnCourse;

    public final kernel.jdon.member.domain.QMember member;

    public QFavorite(String variable) {
        this(Favorite.class, forVariable(variable), INITS);
    }

    public QFavorite(Path<? extends Favorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorite(PathMetadata metadata, PathInits inits) {
        this(Favorite.class, metadata, inits);
    }

    public QFavorite(Class<? extends Favorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inflearnCourse = inits.isInitialized("inflearnCourse") ? new kernel.jdon.inflearn.domain.QInflearnCourse(forProperty("inflearnCourse")) : null;
        this.member = inits.isInitialized("member") ? new kernel.jdon.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

