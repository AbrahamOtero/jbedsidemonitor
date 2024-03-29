package signals;

public abstract class WriterRunnableOneSignal extends WriterRunnable {

    protected String identifier;

    public WriterRunnableOneSignal(String identifier) {
        super();
        this.identifier = identifier;
    }
    protected boolean getLocks() {
        this.lockManager.getWriteLock(identifier);
   //@debug     System.out.println("--GetLockWrite"+identifier);
        return true;
    }

   protected void releaseLocks() {
    //@debug   System.out.println("--ReleaseLockWrite"+identifier);
        this.lockManager.releaseWriteLock(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }
}
