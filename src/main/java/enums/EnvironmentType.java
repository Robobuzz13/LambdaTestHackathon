package enums;

public enum EnvironmentType {
    LOCAL("local"),
    GRID("grid"),
    LAMBDA_TEST("lambda_test");

    private final String environmentName;

    EnvironmentType(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentName() {
        return environmentName;
    }
}
