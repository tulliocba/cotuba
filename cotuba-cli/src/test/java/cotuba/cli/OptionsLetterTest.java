package cotuba.cli;

import org.apache.commons.cli.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

class OptionsLetterTest {

    @Test
    void get_all_options() {
        Assertions.assertThat(OptionsLetter.options().getOptions()
            .stream().map(Option::getOpt).collect(Collectors.toList()))
            .containsAll(Arrays.asList("d", "f", "o", "v"));
    }

}