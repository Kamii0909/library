package hust.kien.project.core.service;

public final class VoidSchema {
    private static final VoidSchema INSTANCE = new VoidSchema();
    
    public static VoidSchema get() {
        return INSTANCE;
    }
    
    private VoidSchema() {
    }
}
