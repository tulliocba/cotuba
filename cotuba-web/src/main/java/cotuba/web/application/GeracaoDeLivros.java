package cotuba.web.application;

import cotuba.application.Cotuba;
import cotuba.application.ParametrosCotuba;
import cotuba.application.RepositorioDeMDs;
import cotuba.domain.FormatoEbook;
import cotuba.web.domain.Livro;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

@Service
public class GeracaoDeLivros {

    private final CadastroDeLivros livros;
    private final Cotuba cotuba;

    public GeracaoDeLivros(CadastroDeLivros livros, final Cotuba cotuba) {
        this.livros = livros;
        this.cotuba = cotuba;
    }

    private static class ParametrosCotubaWeb implements ParametrosCotuba {

        private Path arquivoDeSaida;
        private FormatoEbook formatoEbook;

        public ParametrosCotubaWeb(final FormatoEbook formatoEbook) {
            this.formatoEbook = formatoEbook;
        }

        @Override
        public FormatoEbook getFormato() {
            return formatoEbook;
        }

        @Override
        public Path getArquivoDeSaida() {
            if (arquivoDeSaida != null) return arquivoDeSaida;

            try {
                Path diretorio = Files.createTempDirectory("ebooks");
                String nomeDoArquivoDeSaida = "book." + formatoEbook.name().toLowerCase();
                setArquivoDeSaida(diretorio.resolve(nomeDoArquivoDeSaida));
                return arquivoDeSaida;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        private void setArquivoDeSaida(final Path diretorioDeSaida) {
            this.arquivoDeSaida = diretorioDeSaida;
        }

    }

    public Path geraLivro(Long id, FormatoEbook formato) {

        Livro livro = livros.detalha(id);

        final ParametrosCotuba parametros = new ParametrosCotubaWeb(formato);
        final RepositorioDeMDs repositorioDeMDs = new MDsDoBancoDeDados(livro);
        final Consumer<String> acaoPosGeracao = System.out::println;
        cotuba.executa(parametros, acaoPosGeracao, repositorioDeMDs);
        return parametros.getArquivoDeSaida();
    }

}