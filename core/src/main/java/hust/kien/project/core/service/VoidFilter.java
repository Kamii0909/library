package hust.kien.project.core.service;

public final class VoidFilter {
    private static final VoidFilter INSTANCE = new VoidFilter();
    
    public static VoidFilter get() {
        return INSTANCE;
    }
    
    private VoidFilter() {
        
    }
}
