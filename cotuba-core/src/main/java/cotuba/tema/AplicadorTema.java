package cotuba.tema;

import cotuba.plugin.Tema;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AplicadorTema {

    private final List<Tema> temas;

    public AplicadorTema(final List<Tema> temas) {
        this.temas = temas;
    }

    public String aplica(final String html) {
        Document document = Jsoup.parse(html);
        temas.forEach(css -> document.select("head").append("<style> " + css + " </style>"));
        return document.html();
    }

}