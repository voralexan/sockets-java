package client;

public class User {
    int rdy = 0;
    private final String point;

    public User(String point) {
        this.point = point;
    }

    boolean set(int row, int column) {
        if (!ConnectF.retPointer().isBusy(row, column)) {
            ConnectF.retPointer().c[row][column] = point;
            return true;
        }
        return false;
    }


    boolean win() {
        return  ConnectF.retPointer().verifyEnd(this.point);
    }
}
