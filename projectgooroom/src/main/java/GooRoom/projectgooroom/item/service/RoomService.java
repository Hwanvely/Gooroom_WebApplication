package GooRoom.projectgooroom.item.service;


import GooRoom.projectgooroom.item.domain.Room;

import GooRoom.projectgooroom.item.dto.RoomDto;
import GooRoom.projectgooroom.item.repository.RoomRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepositoryCustomImpl roomRepositoryCustomImpl;

    public Page<RoomDto> findRoomsByCityAndDong(String city, String dong, Pageable pageable) {
        Page<Room> roomPage = roomRepositoryCustomImpl.findRoomsByCityAndDong(pageable, city, dong);
        return roomPage.map(RoomDto::new);
    }

    public Page<RoomDto> findRoomsByCityAndDongAndOptions(String city, String dong, Boolean airConditional, Boolean washingMachine, Boolean parking, Boolean refrigerator, Pageable pageable) {
        Page<Room> roomsByCityAndDongAndOptions = roomRepositoryCustomImpl.findRoomsByCityAndDongAndOptions(city, dong, airConditional, washingMachine, parking, refrigerator, pageable);
        return roomsByCityAndDongAndOptions.map(RoomDto::new);
    }





}
