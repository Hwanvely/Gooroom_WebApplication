package GooRoom.projectgooroom.homepost.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHomePost is a Querydsl query type for HomePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomePost extends EntityPathBase<HomePost> {

    private static final long serialVersionUID = -305535021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHomePost homePost = new QHomePost("homePost");

    public final GooRoom.projectgooroom.global.embedded.QAddress address;

    public final StringPath content = createString("content");

    public final BooleanPath hasHome = createBoolean("hasHome");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastEditTime = createDateTime("lastEditTime", java.time.LocalDateTime.class);

    public final GooRoom.projectgooroom.member.domain.QMember member;

    public final EnumPath<PostStatus> postStatus = createEnum("postStatus", PostStatus.class);

    public final EnumPath<RentType> rentType = createEnum("rentType", RentType.class);

    public final EnumPath<ResidenceType> residenceType = createEnum("residenceType", ResidenceType.class);

    public final StringPath roomImage = createString("roomImage");

    public final NumberPath<Integer> roomPrice = createNumber("roomPrice", Integer.class);

    public final StringPath title = createString("title");

    public QHomePost(String variable) {
        this(HomePost.class, forVariable(variable), INITS);
    }

    public QHomePost(Path<? extends HomePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHomePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHomePost(PathMetadata metadata, PathInits inits) {
        this(HomePost.class, metadata, inits);
    }

    public QHomePost(Class<? extends HomePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new GooRoom.projectgooroom.global.embedded.QAddress(forProperty("address")) : null;
        this.member = inits.isInitialized("member") ? new GooRoom.projectgooroom.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

