package g_raffes.blackdragon.implement;

import dk.acto.blackdragon.model.AuthorData;
import dk.acto.blackdragon.service.AuthorDataFactory;
import io.vavr.control.Try;

import java.net.URL;

public class AuthorDataFactoryImpl implements AuthorDataFactory {
    private final static String NAME = "Marcus Færk Henriksen";
    private final static String LINKEDIN = "https://www.linkedin.com/in/marcus-f%C3%A6rk-henriksen-70300a32b";
    private final static String SOLUTION_REPO = "https://github.com/Giraaffes/black-dragon-java";

    @Override
    public AuthorData create() {
        return AuthorData.builder()
            .name(NAME)
            .linkedInProfile(Try.of(() -> new URL(LINKEDIN)).get())
            .solutionRepository(Try.of(() -> new URL(SOLUTION_REPO)).get())
            .build();
    }
}
