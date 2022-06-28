import java.lang.Math;
import java.util.List;

class Land_object {
    double x;
    double y;
    double angle;
    String pic = "-";
    double pic_size = 0;

    Land_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "road") {
            this.pic = "data\\lands\\road.png";
            this.pic_size = 41;
        }
        if (type == "grass") {
            this.pic = "data\\lands\\grass.png";
            this.pic_size = 27;
        }
        if (type == "camp_birch") {
            this.pic = "data\\lands\\camp_birch.png";
            this.pic_size = 27;
        }
        if (type == "camp_fir") {
            this.pic = "data\\lands\\camp_fir.png";
            this.pic_size = 27;
        }
    }
}