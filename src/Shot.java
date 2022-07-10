import java.lang.Math;
import java.util.List;

class Shot_object {
    String fraction;
    double x;
    double y;
    double angle;
    double speed;
    double damage;
    double distance = 0;
    String pic = "-";
    double pic_size = 0;

    Shot_object(String fraction, double x, double y, double angle) {
        this.fraction = fraction;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "train_arrow") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 100;
            this.damage = 8;
        }
        if (type == "leather_arrow") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 100;
            this.damage = 10;
        }
        if (type == "iron_arrow") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 100;
            this.damage = 15;
        }
        if (type == "mythril_arrow") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 100;
            this.damage = 20;
        }
        if (type == "leather_bolt") {
            this.pic = "data\\shots\\arrow.png";
            this.pic_size = 5;
            this.speed = 200;
            this.damage = 20;
        }
    }
}