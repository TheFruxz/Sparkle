package dev.fruxz.sparkle.server;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * This class represents the loader of this paper plugin, to enable
 * the required dependencies.
 */
@SuppressWarnings("UnstableApiUsage") // We keep an eye on that.
public class LocalSparkleLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver maven = new MavenLibraryResolver();

        new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("delivered.dependencies"))))
                .lines()
                .forEach(dependency -> maven.addDependency(new Dependency(new DefaultArtifact(dependency), null)));

        maven.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io/").build());
        maven.addRepository(new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2/").build());
        maven.addRepository(new RemoteRepository.Builder("minecraft", "default", "https://libraries.minecraft.net").build());

        classpathBuilder.addLibrary(maven);
    }

}
