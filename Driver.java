public class Driver {
    public static void main (String[] args) {
        Grid map = new Grid(8, false); // parameter: n
        map.addGold(4, 3);
        map.addBeacon(5, 5);
        map.addPit(0, 4);
        map.addPit(5, 2);

        Miner miner = new Miner();
        System.out.println("Initial front: "+miner.getFront());
        if (miner.scan(map) == 0)   System.out.println("miner.scan(): NULL\n");
        else                        System.out.println("miner.scan(): " + miner.scan(map) + "\n");
        map.printGrid(miner);
        System.out.println();

        System.out.println("Move 4x. (front: "+miner.getFront()+")");
        miner.move();
        miner.move();
        miner.move();
        miner.move();
        map.printGrid(miner);
        System.out.println();

        System.out.println("Rotate 1x. (New front: "+miner.getFront()+")");
        miner.rotate();
        if (miner.scan(map) == 0)   System.out.println("miner.scan(): NULL\n");
        else                        System.out.println("miner.scan(): " + miner.scan(map) + "\n");

        System.out.println("Move 3x.");
        miner.move();
        miner.move();
        miner.move();
        map.printGrid(miner);
    }
}