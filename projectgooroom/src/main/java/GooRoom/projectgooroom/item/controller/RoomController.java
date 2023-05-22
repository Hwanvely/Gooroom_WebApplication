package GooRoom.projectgooroom.item.controller;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.global.exception.RoomException;
import GooRoom.projectgooroom.global.exception.RoomExceptionType;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.item.domain.Room;
import GooRoom.projectgooroom.item.dto.RoomDto;
import GooRoom.projectgooroom.item.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
        public ResponseEntity<List<RoomDto>> getRoomsByCityAndDongAndFacilities(
                @RequestParam(value = "city") String city,
                @RequestParam(value = "dong") String dong,
                @RequestParam(required = false) Boolean airConditional,
                @RequestParam(required = false) Boolean washingMachine,
                @RequestParam(required = false) Boolean parking,
                @RequestParam(required = false) Boolean refrigerator,
                @RequestParam(defaultValue = "0") int page
        ) {

        if (city == null || dong == null) {
            throw new RoomException(RoomExceptionType.NOT_FOUND_ADDRESS);
        }

        Pageable pageable = PageRequest.of(page, 10);
        Page<RoomDto> roomPage = roomService.findRoomsByCityAndDongAndOptions(city, dong, airConditional, washingMachine, parking, refrigerator, pageable);


        return new ResponseEntity<>(roomPage.getContent() ,HttpStatus.OK);
    }



}
