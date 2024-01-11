package kernel.jdon.inflearncourse.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInflearnCourse is a Querydsl query type for InflearnCourse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInflearnCourse extends EntityPathBase<InflearnCourse> {

    private static final long serialVersionUID = -172030404L;

    public static final QInflearnCourse inflearnCourse = new QInflearnCourse("inflearnCourse");

    public final kernel.jdon.base.QBaseEntity _super = new kernel.jdon.base.QBaseEntity(this);

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath instructor = createString("instructor");

    public final StringPath lectureUrl = createString("lectureUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> studentCount = createNumber("studentCount", Long.class);

    public final StringPath title = createString("title");

    public QInflearnCourse(String variable) {
        super(InflearnCourse.class, forVariable(variable));
    }

    public QInflearnCourse(Path<? extends InflearnCourse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInflearnCourse(PathMetadata metadata) {
        super(InflearnCourse.class, metadata);
    }

}

