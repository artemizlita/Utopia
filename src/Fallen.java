import java.lang.Math;
import java.util.List;

class Fallen_object {
    double x;
    double y;
    double angle;
    String pic = "-";
    double pic_size = 0;

    Fallen_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "green_small_tree_fall") {
            this.pic = "data\\locations\\objects\\green_small_tree_fall.png";
            this.pic_size = 67;
        }
        if (type == "green_tree_fall") {
            this.pic = "data\\locations\\objects\\green_tree_fall.png";
            this.pic_size = 105;
        }
        if (type == "bush_fall") {
            this.pic = "data\\locations\\objects\\bush_fall.png";
            this.pic_size = 55;
        }
        if (type == "birch_fall") {
            this.pic = "data\\locations\\objects\\birch_fall.png";
            this.pic_size = 55;
        }

        if (type == "wooden_fence_fall") {
            this.pic = "data\\locations\\objects\\wooden_fence_fall.png";
            this.pic_size = 55;
        }

        if (type == "feed") {
            this.pic = "data\\locations\\objects\\feed.png";
            this.pic_size = 23;
        }
    }
}