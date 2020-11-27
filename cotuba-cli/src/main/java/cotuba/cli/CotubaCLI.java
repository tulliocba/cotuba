package cotuba.cli;

import cotuba.application.Cotuba;
import cotuba.application.RepositorioDeMDs;
import org.springframework.stereotype.Component;

@Component
public class CotubaCLI {

    private final Cotuba cotuba;

    public CotubaCLI(final Cotuba cotuba) {
        this.cotuba = cotuba;
    }

    public void executa(String[] args) {

        final LeitorOpcoesCLI opcoesCLI = new LeitorOpcoesCLI(args);

        try {
            RepositorioDeMDs repositorioDeMD = new MDsDoDiretorio(opcoesCLI.getDiretorioDosMD());
            cotuba.executa(opcoesCLI, System.out::println, repositorioDeMD);

            System.out.println("Arquivo gerado com sucesso: " + opcoesCLI.getArquivoDeSaida());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (opcoesCLI.isModoVerboso()) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }

}
