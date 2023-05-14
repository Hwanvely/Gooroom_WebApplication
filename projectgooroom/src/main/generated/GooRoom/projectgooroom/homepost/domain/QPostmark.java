package GooRoom.projectgooroom.homepost.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostmark is a Querydsl query type for Postmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostmark extends EntityPathBase<Postmark> {

    private static final long serialVersionUID = 938523937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostmark postmark = new QPostmark("postmark");

    public final QHomePost homePost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final GooRoom.projectgooroom.member.domain.QMember member;

    public QPostmark(String variable) {
        this(Postmark.class, forVariable(variable), INITS);
    }

    public QPostmark(Path<? extends Postmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostmark(PathMetadata metadata, PathInits inits) {
        this(Postmark.class, metadata, inits);
    }

    public QPostmark(Class<? extends Postmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.homePost = inits.isInitialized("homePost") ? new QHomePost(forProperty("homePost"), inits.get("homePost")) : null;
        this.member = inits.isInitialized("member") ? new GooRoom.projectgooroom.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

