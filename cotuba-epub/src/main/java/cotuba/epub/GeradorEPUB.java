package cotuba.epub;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.domain.FormatoEbook;
import cotuba.plugin.GeradorEbook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeradorEPUB implements GeradorEbook {

    @Override
    public void gera(final Ebook ebook) {
        final Path arquivoDeSaida = ebook.getArquivoDeSaida();

        Book epub = new Book();

        for (Capitulo capitulo : ebook.getCapitulos()) {
            // TODO: usar título do capítulo
            final String html = capitulo.getConteudoHTML();
            final String titulo = capitulo.getTitulo();

            epub.addSection(titulo, new Resource(html.getBytes(), MediatypeService.XHTML));
        }

        EpubWriter epubWriter = new EpubWriter();
        try {
            epubWriter.write(epub, Files.newOutputStream(arquivoDeSaida));
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao criar arquivo EPUB: " + arquivoDeSaida.toAbsolutePath(), ex);
        }
    }

    @Override
    public FormatoEbook formato() {
        return FormatoEbook.EPUB;
    }

}
