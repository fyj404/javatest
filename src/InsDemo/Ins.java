package InsDemo;

public class Ins {
    private static volatile Ins instance;
    static Ins getInstance() {
        if (instance == null) {
            instance = new Ins();
        }
        return instance;
    }
}
