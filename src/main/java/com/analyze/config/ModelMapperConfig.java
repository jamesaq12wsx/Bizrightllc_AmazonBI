package com.analyze.config;

import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    Logger logger = LoggerFactory.getLogger(ModelMapperConfig.class);

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try{
                    LocalDate localDate = LocalDate.parse(source, format);
                    return localDate;
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage());
                    return null;
                }
            }
        };

        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

}
