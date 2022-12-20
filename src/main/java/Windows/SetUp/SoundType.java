package Windows.SetUp;

public enum SoundType {
    Classic("经典"),
    War("钢铁雄心主题");
    private String name;

    SoundType(String name) {
        this.name = name;
    }

    public static SoundType getSoundType(String name) {
        for (SoundType l : SoundType.values()) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return Classic;//default
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
