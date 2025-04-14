package driver;

import enums.DriverType;
import enums.EnvironmentType;

public class DriverFactory {

    public static DriverManager getManager(EnvironmentType environmentType, DriverType browserType) {
        return switch (environmentType) {
            case LOCAL -> new LocalDriverManager(browserType);
            case GRID -> new GridDriverManager(browserType);
            case LAMBDA_TEST -> new LambdaTestDriverManager(browserType);
        };
    }
}

