package GooRoom.projectgooroom.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberInformation is a Querydsl query type for MemberInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInformation extends EntityPathBase<MemberInformation> {

    private static final long serialVersionUID = -580726653L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberInformation memberInformation = new QMemberInformation("memberInformation");

    public final EnumPath<CleanupType> cleanupType = createEnum("cleanupType", CleanupType.class);

    public final EnumPath<DrinkingType> drinkingType = createEnum("drinkingType", DrinkingType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final QMember member;

    public final EnumPath<OrganizeType> organizeType = createEnum("organizeType", OrganizeType.class);

    public final StringPath profileImage = createString("profileImage");

    public final BooleanPath sleepingHabitType = createBoolean("sleepingHabitType");

    public final BooleanPath smokingType = createBoolean("smokingType");

    public final EnumPath<WakeupType> wakeupType = createEnum("wakeupType", WakeupType.class);

    public QMemberInformation(String variable) {
        this(MemberInformation.class, forVariable(variable), INITS);
    }

    public QMemberInformation(Path<? extends MemberInformation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberInformation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberInformation(PathMetadata metadata, PathInits inits) {
        this(MemberInformation.class, metadata, inits);
    }

    public QMemberInformation(Class<? extends MemberInformation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

