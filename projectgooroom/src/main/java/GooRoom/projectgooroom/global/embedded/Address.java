package GooRoom.projectgooroom.global.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Builder
@AllArgsConstructor
@Getter
public class Address {
    private String city; //시 구
    private String dong; // 동
    private String roadName; //~ 로 ~길
    private String buildingNumber; //

    public Address(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getDong(), address.getDong()) && Objects.equals(getRoadName(), address.getRoadName()) && Objects.equals(getBuildingNumber(), address.getBuildingNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getDong(), getRoadName(), getBuildingNumber());
    }
}
