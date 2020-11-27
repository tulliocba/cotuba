package cotuba.md;

import cotuba.application.RenderizadorMDParaHTML;
import cotuba.application.RepositorioDeMDs;
import cotuba.domain.Capitulo;
import cotuba.domain.builder.CapituloBuilder;
import cotuba.tema.AplicadorTema;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RenderizadorMDParaHTMLComCommonMark implements RenderizadorMDParaHTML {

    private static final PathMatcher MD_FILE_MATCHER = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
    private final AplicadorTema aplicadorTema;

    public RenderizadorMDParaHTMLComCommonMark(final AplicadorTema aplicadorTema) {
        this.aplicadorTema = aplicadorTema;
    }

    @Override
    public List<Capitulo> renderiza(RepositorioDeMDs repositorioDeMDs) {
        return repositorioDeMDs.obtemMDsDosCapitulos()
            .stream()
            .map(md -> gerarCapitulo(md))
            .collect(Collectors.toList());
    }

    private String getHtml(final Node document) {
        return Optional.ofNullable(HtmlRenderer.builder().build().render(document))
            .orElseThrow(() -> new RuntimeException("Erro ao renderizar MD para HTML"));
    }

    private AbstractVisitor getVisitor(final CapituloBuilder builder) {
        return new AbstractVisitor() {
            @Override
            public void visit(Heading heading) {
                if (heading.getLevel() == 1) {
                    String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                    builder.comTitulo(tituloDoCapitulo);
                } else if (heading.getLevel() == 2) {
                    // seção
                } else if (heading.getLevel() == 3) {
                    // título
                }
            }
        };
    }

    private Node getDocument(final String md) {
        return Parser.builder().build().parse(md);
    }

    private Capitulo gerarCapitulo(String md) {
        final CapituloBuilder builder = new CapituloBuilder();

        final Node document = parseMDFile(md, builder);

        final String html = getHtml(document);

        return builder.comConteudoHTML(aplicadorTema.aplica(html)).constroi();
    }

    private Node parseMDFile(final String md, final CapituloBuilder builder) {
        Node document;
        try {
            document = getDocument(md);
            document.accept(getVisitor(builder));
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao fazer parse de markdown " + md, ex);
        }
        return document;
    }

}
