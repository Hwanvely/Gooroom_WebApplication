package GooRoom.projectgooroom.item.repository;


import GooRoom.projectgooroom.item.domain.QRoom;
import GooRoom.projectgooroom.item.domain.Room;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public RoomRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Transactional
    @Override
    public Page<Room> findRoomsByCityAndDong(Pageable pageable, String city, String dong) {
        QRoom room = QRoom.room;
        Predicate predicate = room.address.city.eq(city)
                .and(room.address.dong.eq(dong));

        JPAQuery<Room> query = queryFactory.selectFrom(room)
                .where(predicate)
                .orderBy(room.id.desc());

        long total = query.fetchCount();

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Room> rooms = query.fetch();

        return new PageImpl<>(rooms, pageable, total);
    }

    @Override
    public Page<Room> findRoomsByCityAndDongAndOptions(String city, String dong, Boolean airConditional, Boolean washingMachine, Boolean parking, Boolean refrigerator, Pageable pageable) {

            QRoom room = QRoom.room;
            BooleanExpression expression = room.address.city.eq(city)
                    .and(room.address.dong.eq(dong));

            if (airConditional != null) {
                expression = expression.and(room.airConditional.eq(airConditional));
            }

            if (washingMachine != null) {
                expression = expression.and(room.washingMachine.eq(washingMachine));
            }

            if (parking != null) {
                expression = expression.and(room.parking.eq(parking));
            }

            if (refrigerator != null) {
                expression = expression.and(room.refrigerator.eq(refrigerator));
            }

            long total = queryFactory.selectFrom(room)
                    .where(expression)
                    .fetchCount();

            List<Room> results = queryFactory.selectFrom(room)
                    .where(expression)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            return new PageImpl<>(results, pageable, total);
        }
    }


