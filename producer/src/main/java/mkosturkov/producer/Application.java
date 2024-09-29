package mkosturkov.producer;

import io.micronaut.runtime.Micronaut;
import io.micronaut.serde.annotation.SerdeImport;
import mosturkov.common.NumberData;

@SerdeImport(NumberData.class)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}