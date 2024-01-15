package kernel.jdon.inflearnjdskill.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInflearnJdSkill is a Querydsl query type for InflearnJdSkill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInflearnJdSkill extends EntityPathBase<InflearnJdSkill> {

    private static final long serialVersionUID = 1322124392L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInflearnJdSkill inflearnJdSkill = new QInflearnJdSkill("inflearnJdSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.inflearncourse.domain.QInflearnCourse inflearnCourse;

    public final kernel.jdon.skill.domain.QSkill skill;

    public QInflearnJdSkill(String variable) {
        this(InflearnJdSkill.class, forVariable(variable), INITS);
    }

    public QInflearnJdSkill(Path<? extends InflearnJdSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInflearnJdSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInflearnJdSkill(PathMetadata metadata, PathInits inits) {
        this(InflearnJdSkill.class, metadata, inits);
    }

    public QInflearnJdSkill(Class<? extends InflearnJdSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inflearnCourse = inits.isInitialized("inflearnCourse") ? new kernel.jdon.inflearncourse.domain.QInflearnCourse(forProperty("inflearnCourse")) : null;
        this.skill = inits.isInitialized("skill") ? new kernel.jdon.skill.domain.QSkill(forProperty("skill"), inits.get("skill")) : null;
    }

}

