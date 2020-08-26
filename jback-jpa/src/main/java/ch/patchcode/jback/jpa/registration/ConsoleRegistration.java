package ch.patchcode.jback.jpa.registration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("console")
public class ConsoleRegistration extends Registration {
}
