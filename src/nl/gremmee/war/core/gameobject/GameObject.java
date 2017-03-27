package nl.gremmee.war.core.gameobject;

public abstract class GameObject implements IGameObject {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String aName) {
        this.name = aName;
    }
}
