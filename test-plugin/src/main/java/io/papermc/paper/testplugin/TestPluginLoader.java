package io.papermc.paper.testplugin;

import io.papermc.paper.plugin.PluginLoader;
import io.papermc.paper.plugin.provider.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.provider.loader.library.impl.JarLibrary;
import io.papermc.paper.plugin.provider.loader.library.impl.maven.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;

import java.nio.file.Path;
import java.util.List;

public class TestPluginLoader implements PluginLoader {
    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
       // classpathBuilder.addLibrary(new JarLibrary(Path.of("bob.jar")));

        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addDependency(new Dependency(new DefaultArtifact("com.owen1212055:particlehelper:1.0.0-SNAPSHOT"), null));
        resolver.addRepository(new RemoteRepository.Builder("bytecode", "default", "https://repo.bytecode.space/repository/maven-public/").build());

        resolver.setFilter(new DependencyFilter() {
            @Override
            public boolean accept(DependencyNode node, List<DependencyNode> parents) {
                return true;
            }
        });

        classpathBuilder.addLibrary(resolver);
    }
}
