package handler;

public interface Rules
{

  public void actionMoveEntity();

  public String getNextActor();

  public void addInitiative(String entityId);

  public void removeInitiative(String entityId);

} 