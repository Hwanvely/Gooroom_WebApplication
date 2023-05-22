package GooRoom.projectgooroom.item.dto;


import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import GooRoom.projectgooroom.item.domain.Room;
import GooRoom.projectgooroom.item.domain.RoomStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class RoomDto {

    private Long id;



    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RentType rentType;

    private int monthlyFee;

    private int deposit;

    private int houseSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ResidenceType residenceType;

    private int floor;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RoomStatus roomStatus;

    @Lob
    private String content;


    //Address
    private String city;
    private String dong;
    private String roadName;
    private String buildingNumber;

    private boolean airConditional;

    private boolean refrigerator;

    private boolean washingMachine;

    private boolean parking;

    public RoomDto(Room room)
    {
        this.id = room.getId();
        this.city = room.getAddress().getCity();
        this.dong = room.getAddress().getDong();
        this.roadName = room.getAddress().getRoadName();
        this.buildingNumber = room.getAddress().getBuildingNumber();
        this.rentType = room.getRentType();
        this.monthlyFee = room.getMonthlyFee();
        this.deposit = room.getDeposit();
        this.houseSize = room.getHouseSize();
        this.residenceType = room.getResidenceType();
        this.floor = room.getFloor();
        this.roomStatus = room.getRoomStatus();
        this.content = room.getContent();
        this.airConditional = room.getAirConditional();
        this.refrigerator = room.getRefrigerator();
        this.washingMachine = room.getWashingMachine();
        this.parking = room.getParking();
    }

}
