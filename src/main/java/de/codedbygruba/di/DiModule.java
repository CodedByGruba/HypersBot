package de.codedbygruba.di;

import com.google.inject.AbstractModule;
import de.codedbygruba.commands.AddCommand;
import de.codedbygruba.commands.AddSecondCommand;
import de.codedbygruba.listeners.MessageListener;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.repositories.FarmRepository;
import de.codedbygruba.repositories.implementation.FarmRepositoryImpl;
import de.codedbygruba.services.ApiService;
import de.codedbygruba.services.FarmService;
import de.codedbygruba.services.SheetsService;
import de.codedbygruba.services.implementation.ApiServiceImpl;
import de.codedbygruba.services.implementation.FarmServiceImpl;
import de.codedbygruba.services.implementation.SheetsServiceImpl;

public class DiModule extends AbstractModule {
    private final Secrets secret;

    public DiModule(Secrets secret) {
        this.secret = secret;
    }


    @Override
    protected void configure() {
        bind(Secrets.class).toInstance(secret);

        bind(ApiService.class).to(ApiServiceImpl.class);
        bind(SheetsService.class).to(SheetsServiceImpl.class);
        bind(FarmService.class).to(FarmServiceImpl.class);

        bind(FarmRepository.class).to(FarmRepositoryImpl.class).asEagerSingleton();

        bind(AddCommand.class);
        bind(AddSecondCommand.class);
        bind(MessageListener.class).asEagerSingleton();
    }
}
