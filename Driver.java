public class Driver {
    public static void main (String[] args) {
        Grid map = new Grid(8, false);
        map.addGold(4, 3);
        map.addBeacon(5, 5);
        map.addPit(0, 4);
        map.addPit(5, 2);
        
        map.printGrid();
        System.out.println("\n");

        SmartMiner miner = new SmartMiner(map);
        miner.startSearch();
    }
}