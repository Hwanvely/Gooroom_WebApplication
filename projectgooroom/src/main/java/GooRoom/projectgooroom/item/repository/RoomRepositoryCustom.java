package GooRoom.projectgooroom.item.repository;

import GooRoom.projectgooroom.item.domain.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;





@Repository
public interface RoomRepositoryCustom  {

    Page<Room> findRoomsByCityAndDong(Pageable pageable, String city, String dong);

    Page<Room> findRoomsByCityAndDongAndOptions(String city, String dong, Boolean airConditional, Boolean washingMachine, Boolean parking, Boolean refrigerator, Pageable pageable);
}
