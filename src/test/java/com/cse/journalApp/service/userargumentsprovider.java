package com.cse.journalApp.service;

import com.cse.journalApp.entity.users;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class userargumentsprovider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(users.builder().username("shyam").password("shyam").build()),
                Arguments.of(users.builder().username("suraj").password("").build())
        );
    }
}
