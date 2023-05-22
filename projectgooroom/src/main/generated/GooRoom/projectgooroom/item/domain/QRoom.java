package GooRoom.projectgooroom.item.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -1072671709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final GooRoom.projectgooroom.global.embedded.QAddress address;

    public final BooleanPath airConditional = createBoolean("airConditional");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> deposit = createNumber("deposit", Integer.class);

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final NumberPath<Integer> houseSize = createNumber("houseSize", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastEditTime = createDateTime("lastEditTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> monthlyFee = createNumber("monthlyFee", Integer.class);

    public final BooleanPath parking = createBoolean("parking");

    public final BooleanPath refrigerator = createBoolean("refrigerator");

    public final EnumPath<GooRoom.projectgooroom.global.embedded.RentType> rentType = createEnum("rentType", GooRoom.projectgooroom.global.embedded.RentType.class);

    public final EnumPath<GooRoom.projectgooroom.global.embedded.ResidenceType> residenceType = createEnum("residenceType", GooRoom.projectgooroom.global.embedded.ResidenceType.class);

    public final EnumPath<RoomStatus> roomStatus = createEnum("roomStatus", RoomStatus.class);

    public final BooleanPath washingMachine = createBoolean("washingMachine");

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new GooRoom.projectgooroom.global.embedded.QAddress(forProperty("address")) : null;
    }

}

