package br.com.cognitio.estatisticas;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ContagemPalavras implements Iterable<ContagemPalavras.Contagem> {

    private Map<String, Integer> map = new TreeMap<>();

    public void adicionaPalavra(String palavra) {
        Integer contagem = map.get(palavra.trim());

        if (contagem != null) {
            contagem++;
        } else {
            contagem = 1;
        }

        map.put(palavra, contagem);
    }

    public Iterator<Contagem> iterator() {
        final Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        return new Iterator<Contagem>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Contagem next() {
                Map.Entry<String, Integer> entry = iterator.next();
                return new ContagemPalavras.Contagem(entry.getKey(), entry.getValue());
            }
        };
    }

    public static final class Contagem {

        private final String palavra;
        private final int quantidade;

        public Contagem(String palavra, int quantidade) {
            this.palavra = palavra;
            this.quantidade = quantidade;
        }

        public String getPalavra() {
            return palavra;
        }

        public int getQuantidade() {
            return quantidade;
        }

    }

}