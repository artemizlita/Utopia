import java.lang.Math;

class Unit_object {
    int player;
    String fraction;
    double x;
    double y;
    double angle;
    double t_x;
    double t_y;
    double old_x;
    double old_y;

    int cooldown = 0;
    int max_cooldown = 49;
    String[] pics_move = new String[2];
    String[] pics_hit = new String[5];
    double size = 7;
    double pic_size = 21;
    double speed = 20;
    int hp = 100;
    int max_hp = 100;
    int wealth = 20000;

    Unit_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "elf") {
            this.pics_move[0] = "data\\elf\\move0.png";
            this.pics_move[1] = "data\\elf\\move1.png";
            this.pics_hit[0] = "data\\elf\\shot0.png";
            this.pics_hit[1] = "data\\elf\\shot4.png";
            this.pics_hit[2] = "data\\elf\\shot3.png";
            this.pics_hit[3] = "data\\elf\\shot2.png";
            this.pics_hit[4] = "data\\elf\\shot1.png";
            this.fraction = "elf";
            this.speed = 20;
        }
        if (type == "skeleton") {
            this.pics_move[0] = "data\\skeleton\\move0.png";
            this.pics_move[1] = "data\\skeleton\\move1.png";
            this.pics_hit[0] = "data\\skeleton\\shot0.png";
            this.pics_hit[1] = "data\\skeleton\\shot4.png";
            this.pics_hit[2] = "data\\skeleton\\shot3.png";
            this.pics_hit[3] = "data\\skeleton\\shot2.png";
            this.pics_hit[4] = "data\\skeleton\\shot1.png";
            this.fraction = "skeleton";
            this.speed = 15;
        }
        t_x = x;
        t_y = y;
        old_x = x;
        old_y = y;
    }

    public void set_target(double target_x, double target_y) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = target_x;
        double y2 = target_y;

        double gip = Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), 0.5);
        double pi = Math.PI;
        double i_angle;
        if (x2 - x1 >= 0) {
            i_angle = 2 * pi - Math.acos((y2 - y1) / gip);
        } else {
            i_angle = Math.acos((y2 - y1) / gip);
        }

        this.angle = pi + i_angle;

        if (this.player == 1) {
            this.t_x = x1 + Math.sin(this.angle) * Math.max(10, Math.min(100, gip));
            this.t_y = y1 - Math.cos(this.angle) * Math.max(10, Math.min(100, gip));
        }
    }

    boolean dot_in_rect(double dot_x, double dot_y, double x1, double y1, double x2, double y2,
                        double x3, double y3, double x4, double y4) {
        double[] p21 = {x2 - x1, y2 - y1};
        double[] p41 = {x4 - x1, y4 - y1};
        double p21magnitude_squared = p21[0]*p21[0] + p21[1]*p21[1];
        double p41magnitude_squared = p41[0]*p41[0] + p41[1]*p41[1];
        double[] p = {dot_x - x1, dot_y - y1};
        double p21_compare = p[0] * p21[0] + p[1] * p21[1];
        double p41_compare = p[0] * p41[0] + p[1] * p41[1];
        if ((0 <= p21_compare) & (p21_compare <= p21magnitude_squared)) {
            if ((0 <= p41_compare) & (p41_compare <= p41magnitude_squared)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

//    boolean unit_barrier_intersection(Barrier_object barrier) {
//        double x1 = this.x;
//        double y1 = this.y;
//        double gip1 = Math.pow((this.size[0]/2)*(this.size[0]/2) + (this.size[1]/2)*(this.size[1]/2), 0.5);
//
//        double x2 = barrier.x;
//        double y2 = barrier.y;
//        double gip2 = Math.pow((barrier.size[0]/2)*(barrier.size[0]/2) + (barrier.size[1]/2)*(barrier.size[1]/2), 0.5);
//
//        if ((gip1 + gip2) > Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 0.5)) {
//            double ax1 = gip1 * Math.cos(-this.angle + Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double ay1 = -gip1 * Math.sin(-this.angle + Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double bx1 = gip1 * Math.cos(-this.angle - Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double by1 = -gip1 * Math.sin(-this.angle - Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double cx1 = gip1 * Math.cos(-this.angle + Math.PI + Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double cy1 = -gip1 * Math.sin(-this.angle + Math.PI + Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double dx1 = gip1 * Math.cos(-this.angle + Math.PI - Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double dy1 = -gip1 * Math.sin(-this.angle + Math.PI - Math.asin(this.size[1] / 2 / gip1)) + y1;
//
//            double ax2 = gip2 * Math.cos(-barrier.angle + Math.asin(barrier.size[1] / 2 / gip2)) + x2;
//            double ay2 = -gip2 * Math.sin(-barrier.angle + Math.asin(barrier.size[1] / 2 / gip2)) + y2;
//            double bx2 = gip2 * Math.cos(-barrier.angle - Math.asin(barrier.size[1] / 2 / gip2)) + x2;
//            double by2 = -gip2 * Math.sin(-barrier.angle - Math.asin(barrier.size[1] / 2 / gip2)) + y2;
//            double cx2 = gip2 * Math.cos(-barrier.angle + Math.PI + Math.asin(barrier.size[1] / 2 / gip2)) + x2;
//            double cy2 = -gip2 * Math.sin(-barrier.angle + Math.PI + Math.asin(barrier.size[1] / 2 / gip2)) + y2;
//            double dx2 = gip2 * Math.cos(-barrier.angle + Math.PI - Math.asin(barrier.size[1] / 2 / gip2)) + x2;
//            double dy2 = -gip2 * Math.sin(-barrier.angle + Math.PI - Math.asin(barrier.size[1] / 2 / gip2)) + y2;
//
//            if ((dot_in_rect(ax1, ay1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(bx1, by1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(cx1, cy1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(dx1, dy1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(ax2, ay2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(bx2, by2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(cx2, cy2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(dx2, dy2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1))) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
//
//    boolean rect_intersection(Unit_object unit) {
//        double x1 = this.x;
//        double y1 = this.y;
//        double gip1 = Math.pow((this.size[0]/2)*(this.size[0]/2) + (this.size[1]/2)*(this.size[1]/2), 0.5);
//
//        double x2 = unit.x;
//        double y2 = unit.y;
//        double gip2 = Math.pow((unit.size[0]/2)*(unit.size[0]/2) + (unit.size[1]/2)*(unit.size[1]/2), 0.5);
//
//        if ((gip1 + gip2) > Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 0.5)) {
//            double ax1 = gip1 * Math.cos(-this.angle + Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double ay1 = -gip1 * Math.sin(-this.angle + Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double bx1 = gip1 * Math.cos(-this.angle - Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double by1 = -gip1 * Math.sin(-this.angle - Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double cx1 = gip1 * Math.cos(-this.angle + Math.PI + Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double cy1 = -gip1 * Math.sin(-this.angle + Math.PI + Math.asin(this.size[1] / 2 / gip1)) + y1;
//            double dx1 = gip1 * Math.cos(-this.angle + Math.PI - Math.asin(this.size[1] / 2 / gip1)) + x1;
//            double dy1 = -gip1 * Math.sin(-this.angle + Math.PI - Math.asin(this.size[1] / 2 / gip1)) + y1;
//
//            double ax2 = gip2 * Math.cos(-unit.angle + Math.asin(unit.size[1] / 2 / gip2)) + x2;
//            double ay2 = -gip2 * Math.sin(-unit.angle + Math.asin(unit.size[1] / 2 / gip2)) + y2;
//            double bx2 = gip2 * Math.cos(-unit.angle - Math.asin(unit.size[1] / 2 / gip2)) + x2;
//            double by2 = -gip2 * Math.sin(-unit.angle - Math.asin(unit.size[1] / 2 / gip2)) + y2;
//            double cx2 = gip2 * Math.cos(-unit.angle + Math.PI + Math.asin(unit.size[1] / 2 / gip2)) + x2;
//            double cy2 = -gip2 * Math.sin(-unit.angle + Math.PI + Math.asin(unit.size[1] / 2 / gip2)) + y2;
//            double dx2 = gip2 * Math.cos(-unit.angle + Math.PI - Math.asin(unit.size[1] / 2 / gip2)) + x2;
//            double dy2 = -gip2 * Math.sin(-unit.angle + Math.PI - Math.asin(unit.size[1] / 2 / gip2)) + y2;
//
//            if ((dot_in_rect(ax1, ay1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(bx1, by1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(cx1, cy1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(dx1, dy1, ax2, ay2, bx2, by2, cx2, cy2, dx2, dy2)) || (
//                    dot_in_rect(ax2, ay2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(bx2, by2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(cx2, cy2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1)) || (
//                    dot_in_rect(dx2, dy2, ax1, ay1, bx1, by1, cx1, cy1, dx1, dy1))) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
//
//    boolean circle_intersection(Unit_object unit) {
//        double x1 = this.x;
//        double y1 = this.y;
//        double gip1 = Math.pow((this.size[0]/2)*(this.size[0]/2) + (this.size[1]/2)*(this.size[1]/2), 0.5);
//
//        double x2 = unit.x;
//        double y2 = unit.y;
//        double gip2 = Math.pow((unit.size[0]/2)*(unit.size[0]/2) + (unit.size[1]/2)*(unit.size[1]/2), 0.5);
//
//        if ((gip1 + gip2) > Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 0.5)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}