package Windows.SetUp;

public enum SoundType {
    Classic("经典"),
    ;
    private String name;

    SoundType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
