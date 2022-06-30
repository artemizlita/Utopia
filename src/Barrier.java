import java.lang.Math;
import java.util.List;


class Barrier_object {
    double x;
    double y;
    double angle;
    String pic;
    double pic_size;
    double[] size = new double[2];

    Barrier_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "green_small_tree") {
            this.pic = "data\\objects\\green_small_tree.png";
            this.pic_size = 25;
            this.size[0] = 3;
            this.size[1] = 3;
        }
        if (type == "bush") {
            this.pic = "data\\objects\\bush.png";
            this.pic_size = 21;
            this.size[0] = 3;
            this.size[1] = 3;
        }
        if (type == "birch") {
            this.pic = "data\\objects\\birch.png";
            this.pic_size = 21;
            this.size[0] = 3;
            this.size[1] = 3;
        }
        if (type == "dead_tree") {
            this.pic = "data\\objects\\dead_tree.png";
            this.pic_size = 21;
            this.size[0] = 3;
            this.size[1] = 3;
        }

        if (type == "house") {
            this.pic = "data\\objects\\house.png";
            this.pic_size = 85;
            this.size[0] = 75;
            this.size[1] = 49;
        }

        if (type == "wooden_fence") {
            this.pic = "data\\objects\\wooden_fence.png";
            this.pic_size = 31;
            this.size[0] = 29;
            this.size[1] = 3;
        }

        if (type == "water_well") {
            this.pic = "data\\objects\\water_well.png";
            this.pic_size = 21;
            this.size[0] = 17;
            this.size[1] = 17;
        }
    }

    void tree_fall(Unit_object vehicle, List<Fallen_object> fallens) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = vehicle.x;
        double y2 = vehicle.y;

        double gip = Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), 0.5);
        double pi = Math.PI;
        double i_angle;
        if (x2 - x1 >= 0) {
            i_angle = 2 * pi - Math.acos((y2 - y1) / gip);
        } else {
            i_angle = Math.acos((y2 - y1) / gip);
        }

        Fallen_object fallen_tree = new Fallen_object(this.x, this.y, i_angle);
        if (this.pic == "data\\objects\\green_tree.png") {
            fallen_tree.setType("green_tree_fall");
        } else if (this.pic == "data\\objects\\birch.png") {
            fallen_tree.setType("birch_fall");
        } else if (this.pic == "data\\objects\\bush.png") {
            fallen_tree.setType("bush_fall");
        } else if (this.pic == "data\\objects\\green_small_tree.png") {
            fallen_tree.setType("green_small_tree_fall");
        }
        fallens.add(fallen_tree);
    }

    void fence_fall(Unit_object vehicle, List<Fallen_object> fallens) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = vehicle.x;
        double y2 = vehicle.y;

        double i_angle;
        if (this.angle == 0) {
            if (y2 > y1) {
                i_angle = 0;
            } else {
                i_angle = Math.PI;
            }
        } else {
            if (x2 > x1) {
                i_angle = 3 * Math.PI / 2;
            } else {
                i_angle = Math.PI / 2;
            }
        }

        Fallen_object fallen_fence = new Fallen_object(this.x, this.y, i_angle);
        if (this.pic == "data\\objects\\wooden_fence.png") {
            fallen_fence.setType("wooden_fence_fall");
        } else if (this.pic == "data\\objects\\fence.png") {
            fallen_fence.setType("fence_fall");
        } else if (this.pic == "data\\objects\\gate.png") {
            fallen_fence.setType("gate_fall");
        } else if (this.pic == "data\\objects\\concrete_wall.png") {
            fallen_fence.setType("concrete_wall_fall");
        }
        fallens.add(fallen_fence);
    }
}
