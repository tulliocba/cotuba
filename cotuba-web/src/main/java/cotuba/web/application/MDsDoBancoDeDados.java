package cotuba.web.application;

import cotuba.application.RepositorioDeMDs;
import cotuba.web.domain.Livro;

import java.util.List;
import java.util.stream.Collectors;

public class MDsDoBancoDeDados implements RepositorioDeMDs {

    private Livro livro;

    public MDsDoBancoDeDados(Livro livro) {
        this.livro = livro;
    }

    @Override
    public List<String> obtemMDsDosCapitulos() {

        return livro.getCapitulos()
            .stream().map(capitulo -> new StringBuilder("# " + capitulo.getNome() + "\n").append(capitulo.getMarkdown()).toString())
            .collect(Collectors.toList());
    }

}