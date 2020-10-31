package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface Page<TEntity> {

    List<TEntity> getItems();

    int getRequestedPage();

    int getRequestedSize();

    int getTotalPages();

    long getTotalItems();

    class Builder<TEntity> extends Page_Builder<TEntity>{}
}
