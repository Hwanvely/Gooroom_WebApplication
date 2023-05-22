package GooRoom.projectgooroom.item.domain;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import GooRoom.projectgooroom.item.BooleanConverter;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private RentType rentType;

    private int monthlyFee;

    private int deposit;

    private int houseSize;

    @Enumerated(EnumType.STRING)
    private ResidenceType residenceType;

    private int floor;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    private LocalDateTime lastEditTime;

    @Lob
    private String content;

    private boolean airConditional;


    private boolean refrigerator;


    private boolean washingMachine;


    private boolean parking;

    public boolean getAirConditional() {
        return airConditional;
    }

    public void setAirConditional(boolean airConditional) {
        this.airConditional = airConditional;
    }

    public boolean getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(boolean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public boolean getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(boolean washingMachine) {
        this.washingMachine = washingMachine;
    }

    public boolean getParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }
}
