package cotuba;

import cotuba.cli.CotubaCLI;
import cotuba.cli.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CotubaCLIApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringConfig.class)
            .getBean(CotubaCLI.class)
            .executa(args);
    }

}
