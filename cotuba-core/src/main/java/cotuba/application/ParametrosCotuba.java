package cotuba.application;

import cotuba.domain.FormatoEbook;

import java.nio.file.Path;

public interface ParametrosCotuba {

    FormatoEbook getFormato();

    Path getArquivoDeSaida();

}