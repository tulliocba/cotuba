package cotuba.application;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.domain.FormatoEbook;
import cotuba.plugin.AoFinalizarGeracao;
import cotuba.plugin.GeradorEbook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class Cotuba {

    private final RenderizadorMDParaHTML renderizador;
    private final Map<FormatoEbook, GeradorEbook> geradores;
    private final List<AoFinalizarGeracao> finalizacoes;

    public Cotuba(final RenderizadorMDParaHTML renderizador, final Map<FormatoEbook, GeradorEbook> geradores,
        final List<AoFinalizarGeracao> finalizacoes) {
        this.renderizador = renderizador;
        this.geradores = geradores;
        this.finalizacoes = finalizacoes;
    }

    public void executa(ParametrosCotuba parametros, final Consumer<String> acaoAposGeracao, RepositorioDeMDs repositorioDeMDs) {

        List<Capitulo> capitulos = renderizador.renderiza(repositorioDeMDs);

        Ebook ebook = new Ebook(parametros.getFormato(),
            parametros.getArquivoDeSaida(),
            capitulos);

        geradores.get(parametros.getFormato()).gera(ebook);

        finalizacoes.forEach(finalizacao -> finalizacao.aposGeracao(ebook, acaoAposGeracao));
    }

}