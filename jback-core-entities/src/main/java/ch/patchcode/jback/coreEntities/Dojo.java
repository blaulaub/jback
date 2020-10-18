package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Dojo} is a training location of a {@link Club}.
 * <p>
 * In the real world, a {@link Club} may have multiple {@link Dojo}s, and the data model used here allows for this.
 * <p>
 * However, it may also be the case that more than on {@link Club} uses the same training location. The data model
 * used here ignores this. The rationale behind is that each {@link Club} maintains its list of {@link Dojo}s for
 * itself, without possible interference from other clubs.
 */
@FreeBuilder
public interface Dojo {

    UUID getId();

    String getName();

    Club getClub();

    Optional<Address> getAddress();

    class Builder extends Dojo_Builder {
    }

    @FreeBuilder
    interface Draft {

        String getName();

        Club getClub();

        Optional<Address> getAddress();

        class Builder extends Dojo_Draft_Builder {
        }
    }
}
