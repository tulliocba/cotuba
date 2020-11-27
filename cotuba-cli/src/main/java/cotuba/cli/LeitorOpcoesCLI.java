package cotuba.cli;

import cotuba.application.ParametrosCotuba;
import cotuba.domain.FormatoEbook;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static cotuba.cli.Opt.DIR;
import static cotuba.cli.Opt.FORMAT;
import static cotuba.cli.Opt.OUTPUT;
import static cotuba.cli.Opt.VERBOSE;

public class LeitorOpcoesCLI implements ParametrosCotuba {

    private Path diretorioDosMD;
    private FormatoEbook formato;
    private Path arquivoDeSaida;
    private boolean modoVerboso = false;

    public LeitorOpcoesCLI(String[] args) {
        CommandLine cmd = getCommandLine(args, OptionsLetter.options());

        String nomeDoDiretorioDosMD = cmd.getOptionValue(DIR.getLongOpt());
        setNomeDoDiretorioDosMD(nomeDoDiretorioDosMD);

        String nomeDoFormatoDoEbook = cmd.getOptionValue(FORMAT.getLongOpt());
        setFormato(nomeDoFormatoDoEbook);

        String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue(OUTPUT.getLongOpt());
        setArquivoDeSaida(nomeDoArquivoDeSaidaDoEbook);

        Boolean verbose = cmd.hasOption(VERBOSE.getLongOpt());
        setVerbose(verbose);
    }

    private void setVerbose(final Boolean verbose) {
        modoVerboso = verbose;
    }

    private void setArquivoDeSaida(final String nomeDoArquivoDeSaidaDoEbook) {
        if (nomeDoArquivoDeSaidaDoEbook != null) {
            arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
            isADirectory(nomeDoArquivoDeSaidaDoEbook);
        } else {
            arquivoDeSaida = Paths.get("book." + formato.name().toLowerCase());
        }
    }

    private void isADirectory(final String nomeDoArquivoDeSaidaDoEbook) {
        if (Files.exists(arquivoDeSaida) && Files.isDirectory(arquivoDeSaida)) {
            throw new RuntimeException(nomeDoArquivoDeSaidaDoEbook + " é um diretório.");
        }
    }

    private void setFormato(final String nomeDoFormatoDoEbook) {
        if (nomeDoFormatoDoEbook != null) {
            try {
                formato = FormatoEbook.valueOf(nomeDoFormatoDoEbook.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Formato do ebook inválido: " + formato);
            }
        } else {
            formato = FormatoEbook.PDF;
        }
    }

    private CommandLine getCommandLine(final String[] args, final Options options) {
        try {
            return new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("cotuba", options);
            throw new RuntimeException("Opção inválida", e);
        }
    }

    private void setNomeDoDiretorioDosMD(final String nomeDoDiretorioDosMD) {
        if (nomeDoDiretorioDosMD != null) {
            diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
            isNotADirectory(nomeDoDiretorioDosMD);
        } else {
            Path diretorioAtual = Paths.get("");
            diretorioDosMD = diretorioAtual;
        }
    }

    private void isNotADirectory(final String nomeDoDiretorioDosMD) {
        if (!Files.isDirectory(diretorioDosMD)) {
            throw new RuntimeException(nomeDoDiretorioDosMD + " não é um diretório.");
        }
    }

    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    @Override
    public FormatoEbook getFormato() {
        return formato;
    }

    @Override
    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    public boolean isModoVerboso() {
        return modoVerboso;
    }

}
