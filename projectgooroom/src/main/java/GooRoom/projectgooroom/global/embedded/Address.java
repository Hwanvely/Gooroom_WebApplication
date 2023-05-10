package GooRoom.projectgooroom.global.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Objects;

@Embeddable
@Builder
@AllArgsConstructor
public class Address {
    private String city;
    private String roadName;
    private String buildingNumber;
    private String zipcode;

    public Address(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(roadName, address.roadName) && Objects.equals(buildingNumber, address.buildingNumber) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, roadName, buildingNumber, zipcode);
    }
}
