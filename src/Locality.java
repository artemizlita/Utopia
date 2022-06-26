class Locality_object {
    double x;
    double y;
    double distance_to_cross;
    String type;
    boolean there_road = false;

    Locality_object(double x, double y, double distance_to_cross, String type) {
        this.x = x;
        this.y = y;
        this.distance_to_cross = distance_to_cross;
        this.type = type;
    }
}