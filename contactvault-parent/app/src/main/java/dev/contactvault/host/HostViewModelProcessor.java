package dev.contactvault.host;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.model.ViewModelProcessor;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Requires(bean = HostConfiguration.class)
@Singleton
public class HostViewModelProcessor implements ViewModelProcessor<Map<String, Object>> {

    private final HttpHostResolver httpHostResolver;

    public HostViewModelProcessor(HttpHostResolver httpHostResolver) {
        this.httpHostResolver = httpHostResolver;
    }

    @Override
    public void process(
            @NonNull HttpRequest<?> request,
            @NonNull ModelAndView<Map<String, Object>> modelAndView) {
        Map<String, Object> viewModel = modelAndView.getModel().orElseGet(() -> {
            final HashMap<String, Object> newModel = new HashMap<>(1);
            modelAndView.setModel(newModel);
            return newModel;
        });
        try {
            viewModel.putIfAbsent("host", httpHostResolver.resolve(request));
        } catch (UnsupportedOperationException ex) {
            final HashMap<String, Object> modifiableModel = new HashMap<>(viewModel);
            modifiableModel.putIfAbsent("host", httpHostResolver.resolve(request));
            modelAndView.setModel(modifiableModel);
        }
    }
}
