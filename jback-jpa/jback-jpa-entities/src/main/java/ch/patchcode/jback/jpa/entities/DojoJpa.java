package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.Address;
import ch.patchcode.jback.coreEntities.Dojo;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = DojoJpa.ENTITY_NAME)
@Table(name = DojoJpa.ENTITY_NAME)
public class DojoJpa {

    public final static String ENTITY_NAME = "Dojos";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    private String name;

    @ManyToOne
    private ClubJpa club;

    @ElementCollection
    @OrderBy("line")
    private List<AddressLine> addressLines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClubJpa getClub() {
        return club;
    }

    public void setClub(ClubJpa club) {
        this.club = club;
    }

    public List<AddressLine> getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(List<AddressLine> addressLines) {
        this.addressLines = addressLines;
    }

    public Dojo toDomain() {

        var builder = new Dojo.Builder()
                .setId(getId())
                .setName(getName())
                .setClub(getClub().toDomain());
        Optional.ofNullable(getAddressLines())
                .map(it -> it.stream().map(AddressLine::getValue))
                .map(it -> new Address.Builder().addAllLines(it).build())
                .ifPresent(builder::setAddress);
        return builder.build();
    }
}