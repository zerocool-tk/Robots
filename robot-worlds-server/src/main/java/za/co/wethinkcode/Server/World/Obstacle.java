package za.co.wethinkcode.Server.World;

public class Obstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;

    private final int size;

    public Obstacle(int bottomLeftX, int bottomLeftY, int size){
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.size = size;
    }

    public int getBottomLeftX(){
        return bottomLeftX;
    }

    public int getBottomLeftY(){
        return bottomLeftY;
    }

    public int getSize(){
        return size;
    }

    public boolean blocksPosition(int positionX, int positionY){

        return (positionX >= bottomLeftX && positionX <= bottomLeftX + 1) &&
                (positionY >= bottomLeftY && positionY <= bottomLeftY + 1);
    }
}
