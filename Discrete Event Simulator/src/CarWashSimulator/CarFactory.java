package CarWashSimulator;


class CarFactory {
    private int nextID = 0;

    CarFactory(){

    }

    Car createCar(){
        return new Car();
    }

    void incrementID(){
        this.nextID += 1;
    }
}
