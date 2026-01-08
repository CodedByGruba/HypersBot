package de.codedbygruba.di;

import com.google.inject.AbstractModule;
import de.codedbygruba.commands.AddCommand;
import de.codedbygruba.commands.AddSecondCommand;
import de.codedbygruba.commands.StartFarmBuildCommand;
import de.codedbygruba.commands.StopFarmBuildCommand;
import de.codedbygruba.listeners.MessageListener;
import de.codedbygruba.mapper.DurationMapper;
import de.codedbygruba.mapper.implementation.DurationMapperImpl;
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

        //Services
        bind(ApiService.class).to(ApiServiceImpl.class);
        bind(SheetsService.class).to(SheetsServiceImpl.class);
        bind(FarmService.class).to(FarmServiceImpl.class);

        //Mapper
        bind(DurationMapper.class).to(DurationMapperImpl.class);

        //Repositories
        bind(FarmRepository.class).to(FarmRepositoryImpl.class).asEagerSingleton();

        //Commands
        bind(AddCommand.class);
        bind(AddSecondCommand.class);
        bind(StartFarmBuildCommand.class);
        bind(StopFarmBuildCommand.class);
        bind(MessageListener.class).asEagerSingleton();
    }
}
