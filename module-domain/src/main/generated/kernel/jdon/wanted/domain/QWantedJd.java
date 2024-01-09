package kernel.jdon.wanted.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWantedJd is a Querydsl query type for WantedJd
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWantedJd extends EntityPathBase<WantedJd> {

    private static final long serialVersionUID = 384620504L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWantedJd wantedJd = new QWantedJd("wantedJd");

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Long> detailId = createNumber("detailId", Long.class);

    public final StringPath detailUrl = createString("detailUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final kernel.jdon.jobcategory.domain.QJobCategory jobCategory;

    public final StringPath scrapingDate = createString("scrapingDate");

    public final StringPath title = createString("title");

    public QWantedJd(String variable) {
        this(WantedJd.class, forVariable(variable), INITS);
    }

    public QWantedJd(Path<? extends WantedJd> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWantedJd(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWantedJd(PathMetadata metadata, PathInits inits) {
        this(WantedJd.class, metadata, inits);
    }

    public QWantedJd(Class<? extends WantedJd> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobCategory = inits.isInitialized("jobCategory") ? new kernel.jdon.jobcategory.domain.QJobCategory(forProperty("jobCategory")) : null;
    }

}

