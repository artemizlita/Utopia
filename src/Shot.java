import java.lang.Math;
import java.util.List;

class Shot_object {
    double x;
    double y;
    double angle;
    double speed;
    double distance = 0;
    String pic = "-";
    double pic_size = 0;

    Shot_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "arrow") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 200;
        }
    }
}