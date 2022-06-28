class Locality_object {
    double x;
    double y;
    double distance_to_cener;
    String type;
    boolean there_road = false;

    Locality_object(double x, double y, double distance_to_cener, String type) {
        this.x = x;
        this.y = y;
        this.distance_to_cener = distance_to_cener;
        this.type = type;
    }
}