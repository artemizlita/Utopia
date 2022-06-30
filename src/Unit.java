import java.lang.Math;

class Unit_object {
    int player;
    int localities;
    String fraction;
    String type;
    double x;
    double y;
    double angle;
    double t_x;
    double t_y;
    double old_x;
    double old_y;

    String weapon;
    double attack_distance = 90;
    int max_cooldown = 49;
    int max_hp = 100;
    double speed = 20;

    int cooldown = 0;
    String[] pics_move = new String[2];
    String[] pics_hit = new String[5];
    double size = 7;
    double pic_size = 21;
    int hp = 100;
    int wealth = 20000;

    Unit_object(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "elf_archer") {
            this.weapon = "bow";
            this.pics_move[0] = "data\\units\\elf_archer\\move0.png";
            this.pics_move[1] = "data\\units\\elf_archer\\move1.png";
            this.pics_hit[0] = "data\\units\\elf_archer\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf_archer\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf_archer\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf_archer\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf_archer\\shot1.png";
            this.fraction = "elf";
            this.speed = 30;
        }
        if (type == "elf_swordman") {
            this.weapon = "sword";
            this.pics_move[0] = "data\\units\\elf_swordman\\move0.png";
            this.pics_move[1] = "data\\units\\elf_swordman\\move1.png";
            this.pics_hit[0] = "data\\units\\elf_swordman\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf_swordman\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf_swordman\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf_swordman\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf_swordman\\shot1.png";
            this.fraction = "elf";
            this.speed = 30;
        }
        if (type == "skeleton_archer") {
            this.weapon = "bow";
            this.pics_move[0] = "data\\units\\skeleton_archer\\move0.png";
            this.pics_move[1] = "data\\units\\skeleton_archer\\move1.png";
            this.pics_hit[0] = "data\\units\\skeleton_archer\\shot0.png";
            this.pics_hit[1] = "data\\units\\skeleton_archer\\shot4.png";
            this.pics_hit[2] = "data\\units\\skeleton_archer\\shot3.png";
            this.pics_hit[3] = "data\\units\\skeleton_archer\\shot2.png";
            this.pics_hit[4] = "data\\units\\skeleton_archer\\shot1.png";
            this.fraction = "skeleton";
            this.speed = 15;
        }
        t_x = x;
        t_y = y;
        old_x = x;
        old_y = y;
        hp = max_hp;
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

    boolean sword_intersection(Unit_object unit) {
        double x2 = this.x;
        double y2 = this.y;
        double x1 = unit.x;
        double y1 = unit.y;

        double gip = Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), 0.5);
        double pi = Math.PI;
        double i_angle;
        if (x2 - x1 >= 0) {
            i_angle = 2 * pi - Math.acos((y2 - y1) / gip);
        } else {
            i_angle = Math.acos((y2 - y1) / gip);
        }

        double dif = this.angle - i_angle;

        System.out.println(dif);

        if ((- pi / 6 < dif) && (dif < pi / 6) || (2 * pi - pi / 6 < dif) || (-2 * pi + pi / 6 < dif)) {
            if (gip <= 12) {
                return true;
            }
        }
        return false;
    }
}