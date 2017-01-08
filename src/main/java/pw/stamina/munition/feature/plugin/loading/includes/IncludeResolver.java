package pw.stamina.munition.feature.plugin.loading.includes;

/**
 * @author Mark Johnson
 */
public interface IncludeResolver<T> {
    T resolveInclude(String include) throws PluginIncludeResolutionException;
}
