package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.coreEntities.Page;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public abstract class ClubMembershipApplicationPage {

    public abstract List<ClubMembershipApplication> getApplications();

    public abstract int getRequestedPage();

    public abstract int getRequestedSize();

    public abstract int getTotalPages();

    public abstract long getTotalItems();

    public static ClubMembershipApplicationPage fromDomain(Page<ch.patchcode.jback.coreEntities.ClubMembershipApplication> page) {

        return new Builder()
                .addAllApplications(page.getItems().stream().map(ClubMembershipApplication::fromDomain))
                .setRequestedPage(page.getRequestedPage())
                .setRequestedSize(page.getRequestedSize())
                .setTotalPages(page.getTotalPages())
                .setTotalItems(page.getTotalItems())
                .build();
    }

    public static class Builder extends ClubMembershipApplicationPage_Builder {
    }
}
