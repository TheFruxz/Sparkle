package de.fruxz;

import de.jet.jvm.tool.smart.identification.Identity;
import de.jet.jvm.tool.timing.calendar.Calendar;
import de.jet.paper.extension.display.ui.InventoryKt;
import de.jet.paper.extension.tasky.TaskKt;
import de.jet.paper.structure.app.App;
import de.jet.paper.structure.app.AppCache;
import de.jet.paper.structure.app.AppCompanion;
import de.jet.paper.tool.display.item.Item;
import de.jet.paper.tool.display.message.Transmission;
import de.jet.paper.tool.timing.tasky.TemporalAdvice;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestApp extends App {

    @NotNull
    @Override
    public AppCompanion<?> getCompanion() {
        return new TestApp.TestCompanion();
    }

    @NotNull
    @Override
    public String getAppIdentity() {
        return "TestApp";
    }

    @NotNull
    @Override
    public String getAppLabel() {
        return "TestApp";
    }

    @NotNull
    @Override
    public AppCache getAppCache() {
        return new TestCache();
    }

    @Nullable
    @Override
    public Object hello(@NotNull Continuation<? super Unit> $completion) {

        InventoryKt.buildPanel(5, false, (panel) -> {

            panel.place(20, new Item(Material.STONE).putLabel("Hello World!"));

            return Unit.INSTANCE;
        });

        return null;
    }

    static class TestCompanion extends AppCompanion<TestApp> {

        @NotNull
        @Override
        public Identity<TestApp> getPredictedIdentity() {
            return new Identity<>("TestApp");
        }

    }

}
