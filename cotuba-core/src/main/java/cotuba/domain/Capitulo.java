package cotuba.domain;

public final class Capitulo {

    private final String titulo;

    private final String conteudoHTML;

    public String getTitulo() {
        return titulo;
    }

    public String getConteudoHTML() {
        return conteudoHTML;
    }

    public Capitulo(final String titulo, final String conteudoHTML) {
        this.titulo = titulo;
        this.conteudoHTML = conteudoHTML;
    }

}