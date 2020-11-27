open module cotuba.core {
    uses cotuba.plugin.Tema;
    uses cotuba.plugin.GeradorEbook;
    uses cotuba.plugin.AoFinalizarGeracao;

    requires org.commonmark;
    requires jsoup;

    requires spring.context;
    requires spring.beans;
    requires spring.core;

    exports cotuba.application;
    exports cotuba.plugin;
    exports cotuba.domain;
}
