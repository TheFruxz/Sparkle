package de.fruxz;

import de.jet.paper.structure.app.AppCache;
import de.jet.paper.structure.app.cache.CacheDepthLevel;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestCache implements AppCache {

    @Override
    public void dropEntityData(@NotNull UUID entityIdentity, @NotNull CacheDepthLevel dropDepth) {

    }

    @Override
    public void dropEverything(@NotNull CacheDepthLevel dropDepth) {

    }

}
