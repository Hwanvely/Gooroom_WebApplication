package GooRoom.projectgooroom.member.domain;

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

    private static final long serialVersionUID = -198520599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath birthday = createString("birthday");

    public final StringPath birthyear = createString("birthyear");

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final ListPath<GooRoom.projectgooroom.homepost.domain.HomePost, GooRoom.projectgooroom.homepost.domain.QHomePost> homePostList = this.<GooRoom.projectgooroom.homepost.domain.HomePost, GooRoom.projectgooroom.homepost.domain.QHomePost>createList("homePostList", GooRoom.projectgooroom.homepost.domain.HomePost.class, GooRoom.projectgooroom.homepost.domain.QHomePost.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<LoginType> loginType = createEnum("loginType", LoginType.class);

    public final QMemberInformation memberInformation;

    public final StringPath mobile = createString("mobile");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<GooRoom.projectgooroom.homepost.domain.Postmark, GooRoom.projectgooroom.homepost.domain.QPostmark> postmarkList = this.<GooRoom.projectgooroom.homepost.domain.Postmark, GooRoom.projectgooroom.homepost.domain.QPostmark>createList("postmarkList", GooRoom.projectgooroom.homepost.domain.Postmark.class, GooRoom.projectgooroom.homepost.domain.QPostmark.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath socialId = createString("socialId");

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
        this.memberInformation = inits.isInitialized("memberInformation") ? new QMemberInformation(forProperty("memberInformation"), inits.get("memberInformation")) : null;
    }

}

