package cotuba.cli;

import cotuba.application.RepositorioDeMDs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.FileSystems.getDefault;

public class MDsDoDiretorio implements RepositorioDeMDs {

    private Path diretorioDosMD;

    public MDsDoDiretorio(Path diretorioDosMD) {
        this.diretorioDosMD = diretorioDosMD;
    }

    @Override
    public List<String> obtemMDsDosCapitulos() {
        try (Stream<Path> arquivosMD = Files.list(diretorioDosMD)) {
            return arquivosMD
                .filter(getDefault().getPathMatcher("glob:**/*.md")::matches)
                .sorted()
                .map(arquivoMD -> {
                    try {
                        return new String(Files.readAllBytes(arquivoMD));
                    } catch (Exception ex) {
                        throw new RuntimeException("Erro ao ler arquivo " + arquivoMD, ex);
                    }
                }).collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Erro tentando encontrar arquivos .md em " + diretorioDosMD.toAbsolutePath(), ex);
        }
    }

}
