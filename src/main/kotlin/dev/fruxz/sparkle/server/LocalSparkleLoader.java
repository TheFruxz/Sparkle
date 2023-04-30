package dev.fruxz.sparkle.server;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class represents the loader of this paper plugin, to enable
 * the required dependencies.
 */
public class LocalSparkleLoader implements PluginLoader {

    /**
     * The list of dependencies, which this loader should load.
     */
    public static List<String> dependencies = List.of(
            "org.jetbrains.kotlin:kotlin-stdlib:1.8.21",
            "org.jetbrains.kotlin:kotlin-reflect:1.8.21",
            "com.github.TheFruxz:Ascend:ba6c401235",
            "com.github.TheFruxz:Stacked:74e454b253",
            "com.github.TheFruxz:Kojang:1.0-RC2"
    );

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver maven = new MavenLibraryResolver();

        dependencies.forEach(dependency -> maven.addDependency(new Dependency(new DefaultArtifact(dependency), null)));

        maven.addRepository(new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2/").build());
        maven.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io/").build());

        classpathBuilder.addLibrary(maven);
    }

}
