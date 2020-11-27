package cotuba.cli;

import cotuba.plugin.AcaoPosGeracao;

public class ImprimeNoConsole implements AcaoPosGeracao {

    @Override
    public void executa(final String mensagem) {
        System.out.println(mensagem);
    }

}