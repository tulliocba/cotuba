package cotuba.plugin;

import cotuba.domain.Ebook;

import java.util.function.Consumer;

public interface AoFinalizarGeracao {

    void aposGeracao(Ebook ebook, final Consumer<String> impressao);

}
