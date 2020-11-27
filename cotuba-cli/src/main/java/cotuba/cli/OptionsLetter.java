package cotuba.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.stream.Stream;

import static cotuba.cli.Opt.DIR;
import static cotuba.cli.Opt.FORMAT;
import static cotuba.cli.Opt.OUTPUT;
import static cotuba.cli.Opt.VERBOSE;

enum OptionsLetter {
    D(new Option(DIR.getShortOpt(), DIR.getLongOpt(), true, "Diretório que contem os arquivos md. Default: diretório atual.")),
    F(new Option(FORMAT.getShortOpt(), FORMAT.getLongOpt(), true, "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf")),
    O(new Option(OUTPUT.getShortOpt(), OUTPUT.getLongOpt(), true, "Arquivo de saída do ebook. Default: book.{formato}.")),
    V(new Option(VERBOSE.getLongOpt(), VERBOSE.getLongOpt(), false, "Habilita modo verboso."));

    private Option option;

    OptionsLetter(final Option option) {
        this.option = option;
    }

    public static Options options() {
        return Stream.of(values()).map(letterOption -> letterOption.option)
            .collect(Options::new, (left, right) -> left.addOption(right), (left, right) -> {});
    }
}

enum Opt {
    DIR("d", "dir"),
    FORMAT("f", "format"),
    OUTPUT("o", "output"),
    VERBOSE("v", "verbose");

    private String shortOpt;
    private String longOpt;

    Opt(final String shortOpt, final String longOpt) {
        this.shortOpt = shortOpt;
        this.longOpt = longOpt;
    }

    public String getShortOpt() {
        return shortOpt;
    }

    public String getLongOpt() {
        return longOpt;
    }
}