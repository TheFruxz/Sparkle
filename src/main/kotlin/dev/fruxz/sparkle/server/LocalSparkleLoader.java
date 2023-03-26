package dev.fruxz.sparkle.server;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class LocalSparkleLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver maven = new MavenLibraryResolver();

        maven.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib:1.8.20-RC2"), null));

        maven.addDependency(new Dependency(new DefaultArtifact("com.github.TheFruxz:Ascend:2023.1"), null));
        maven.addDependency(new Dependency(new DefaultArtifact("com.github.TheFruxz:Stacked:2023.1"), null));

        maven.addRepository(new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2/").build());
        maven.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io/").build());

        classpathBuilder.addLibrary(maven);
    }

}
