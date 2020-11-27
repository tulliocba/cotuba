package cotuba.application;

import cotuba.domain.Capitulo;

import java.util.List;

public interface RenderizadorMDParaHTML {

    List<Capitulo> renderiza(RepositorioDeMDs repositorioDeMDs);

}