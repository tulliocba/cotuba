package br.com.cognitio.estatisticas;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.plugin.AoFinalizarGeracao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CalculadoraEstatisticas implements AoFinalizarGeracao {

    @Override
    public void aposGeracao(final Ebook ebook, final Consumer<String> impressao) {
        final List<Capitulo> capitulos = ebook.getCapitulos();

        ContagemPalavras contagemPalavras = new ContagemPalavras();

        for (Capitulo capitulo : capitulos) {
            final String html = capitulo.getConteudoHTML();
            final Document doc = Jsoup.parse(html);

            String textoDoCapitulo = doc.body().text();

            String textoDoCapituloSemPontuacao =
                textoDoCapitulo.replaceAll("\\p{Punct}", " ");

            String textoDoCapituloSemAcentos =
                Normalizer.normalize(textoDoCapituloSemPontuacao, Normalizer.Form.NFD).
                    replaceAll("[^\\p{ASCII}]", "");

            String[] palavras = textoDoCapituloSemAcentos.split("\\s+");

            for (String palavra : palavras) {
                String emMaiusculas = palavra.toUpperCase();
                contagemPalavras.adicionaPalavra(emMaiusculas);
            }

            for (ContagemPalavras.Contagem contagem : contagemPalavras) {
                impressao.accept(contagem.getPalavra() + ": " + contagem.getQuantidade());
            }
        }

        //        ebook.getCapitulos().stream()
        //            .map(capitulo -> Jsoup.parse(capitulo.getConteudoHTML()).body().text())
        //            .map(this::removerPontuacao)
        //            .map(this::removerAcento)
        //            .map(this::removeEspacos)
        //            .flatMap(List::stream)
        //            .map(String::toUpperCase)
        //            .collect(Collectors.toMap(palavra -> palavra, palavra -> 1, (currentValue, newValue) -> currentValue + newValue))
        //            .forEach((palavra, ocorrencias) -> System.out.println(palavra + ": " + ocorrencias));
    }

    private List<String> removeEspacos(final String text) {
        return Arrays.asList(text.split("\\s+"));
    }

    private String removerAcento(final String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).
            replaceAll("[^\\p{ASCII}]", "");
    }

    private String removerPontuacao(final String text) {
        return text.replaceAll("\\p{Punct}", " ");
    }
}