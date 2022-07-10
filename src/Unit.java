import java.lang.Math;
import java.util.Random;

class Unit_object {
    int player;
    int party;
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
    String arrow_type;
    double damage;
    double attack_distance;
    int max_cooldown;
    double speed;
    double max_hp;
    int exp_to_up;
    String up1, up2;

    int cooldown = 0;
    String[] pics_move = new String[2];
    String[] pics_hit = new String[5];
    double size = 7;
    double pic_size = 35;
    double hp;
    int wealth = 20000;

    Unit_object(int party, double x, double y, double angle) {
        this.party = party;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setType(String type) {
        if (type == "elf_train_bow") {
            this.pics_move[0] = "data\\units\\elf\\elf_train_bow\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_train_bow\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_train_bow\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_train_bow\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_train_bow\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_train_bow\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_train_bow\\shot1.png";
            this.fraction = "elf";
            this.weapon = "bow";
            this.arrow_type = "train_arrow";
            this.attack_distance = 86;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 50;
        }
        if (type == "elf_serjant_bow") {
            this.pics_move[0] = "data\\units\\elf\\elf_serjant_bow\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_serjant_bow\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_serjant_bow\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_serjant_bow\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_serjant_bow\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_serjant_bow\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_serjant_bow\\shot1.png";
            this.fraction = "elf";
            this.weapon = "bow";
            this.arrow_type = "leather_arrow";
            this.attack_distance = 86;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 80;
        }
        if (type == "elf_leather_bow") {
            this.pics_move[0] = "data\\units\\elf\\elf_leather_bow\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_leather_bow\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_leather_bow\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_leather_bow\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_leather_bow\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_leather_bow\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_leather_bow\\shot1.png";
            this.fraction = "elf";
            this.weapon = "bow";
            this.arrow_type = "leather_arrow";
            this.attack_distance = 86;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 100;
        }
        if (type == "elf_iron_bow") {
            this.pics_move[0] = "data\\units\\elf\\elf_iron_bow\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_iron_bow\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_iron_bow\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_iron_bow\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_iron_bow\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_iron_bow\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_iron_bow\\shot1.png";
            this.fraction = "elf";
            this.weapon = "bow";
            this.arrow_type = "iron_arrow";
            this.attack_distance = 86;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 150;
        }
        if (type == "elf_mythril_bow") {
            this.pics_move[0] = "data\\units\\elf\\elf_mythril_bow\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_mythril_bow\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_mythril_bow\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_mythril_bow\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_mythril_bow\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_mythril_bow\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_mythril_bow\\shot1.png";
            this.fraction = "elf";
            this.weapon = "bow";
            this.arrow_type = "mythril_arrow";
            this.attack_distance = 86;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 200;
        }

        if (type == "elf_serjant_spear") {
            this.pics_move[0] = "data\\units\\elf\\elf_serjant_spear\\move0.png";
            this.pics_move[1] = "data\\units\\elf\\elf_serjant_spear\\move1.png";
            this.pics_hit[0] = "data\\units\\elf\\elf_serjant_spear\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf\\elf_serjant_spear\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf\\elf_serjant_spear\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf\\elf_serjant_spear\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf\\elf_serjant_spear\\shot1.png";
            this.fraction = "elf";
            this.weapon = "spear";
            this.damage = 15;
            this.attack_distance = 12;
            this.max_cooldown = 69;
            this.speed = 20;
            this.max_hp = 100;
        }



        if (type == "orc_leather_twohand") {
            this.pics_move[0] = "data\\units\\orc_leather_twohand\\move0.png";
            this.pics_move[1] = "data\\units\\orc_leather_twohand\\move1.png";
            this.pics_hit[0] = "data\\units\\orc_leather_twohand\\shot0.png";
            this.pics_hit[1] = "data\\units\\orc_leather_twohand\\shot4.png";
            this.pics_hit[2] = "data\\units\\orc_leather_twohand\\shot3.png";
            this.pics_hit[3] = "data\\units\\orc_leather_twohand\\shot2.png";
            this.pics_hit[4] = "data\\units\\orc_leather_twohand\\shot1.png";
            this.fraction = "orc";
            this.weapon = "twohand";
            this.damage = 30;
            this.attack_distance = 9;
            this.max_cooldown = 69;
            this.speed = 20;
            this.max_hp = 100;
        }
        if (type == "orc_leather_crossbow") {
            this.pics_move[0] = "data\\units\\orc_leather_crossbow\\move0.png";
            this.pics_move[1] = "data\\units\\orc_leather_crossbow\\move1.png";
            this.pics_hit[0] = "data\\units\\orc_leather_crossbow\\shot0.png";
            this.pics_hit[1] = "data\\units\\orc_leather_crossbow\\shot4.png";
            this.pics_hit[2] = "data\\units\\orc_leather_crossbow\\shot3.png";
            this.pics_hit[3] = "data\\units\\orc_leather_crossbow\\shot2.png";
            this.pics_hit[4] = "data\\units\\orc_leather_crossbow\\shot1.png";
            this.fraction = "orc";
            this.weapon = "crossbow";
            this.arrow_type = "leather_bolt";
            this.attack_distance = 86;
            this.max_cooldown = 199;
            this.speed = 20;
            this.max_hp = 100;
        }

        if (type == "goblin_mythril_sword") {
            this.pics_move[0] = "data\\units\\goblin_mythril_sword\\move0.png";
            this.pics_move[1] = "data\\units\\goblin_mythril_sword\\move1.png";
            this.pics_hit[0] = "data\\units\\goblin_mythril_sword\\shot0.png";
            this.pics_hit[1] = "data\\units\\goblin_mythril_sword\\shot4.png";
            this.pics_hit[2] = "data\\units\\goblin_mythril_sword\\shot3.png";
            this.pics_hit[3] = "data\\units\\goblin_mythril_sword\\shot2.png";
            this.pics_hit[4] = "data\\units\\goblin_mythril_sword\\shot1.png";
            this.fraction = "goblin";
            this.weapon = "sword";
            this.damage = 30;
            this.attack_distance = 9;
            this.max_cooldown = 49;
            this.speed = 50;
            this.max_hp = 300;
            this.size = 15;
        }

        if (type == "elf_swordman") {
            this.pics_move[0] = "data\\units\\elf_swordman\\move0.png";
            this.pics_move[1] = "data\\units\\elf_swordman\\move1.png";
            this.pics_hit[0] = "data\\units\\elf_swordman\\shot0.png";
            this.pics_hit[1] = "data\\units\\elf_swordman\\shot4.png";
            this.pics_hit[2] = "data\\units\\elf_swordman\\shot3.png";
            this.pics_hit[3] = "data\\units\\elf_swordman\\shot2.png";
            this.pics_hit[4] = "data\\units\\elf_swordman\\shot1.png";
            this.fraction = "elf";
            this.weapon = "sword";
            this.damage = 20;
            this.attack_distance = 6;
            this.max_cooldown = 49;
            this.speed = 20;
            this.max_hp = 100;
        }

        if (type == "skeleton_archer") {
            this.pics_move[0] = "data\\units\\skeleton_archer\\move0.png";
            this.pics_move[1] = "data\\units\\skeleton_archer\\move1.png";
            this.pics_hit[0] = "data\\units\\skeleton_archer\\shot0.png";
            this.pics_hit[1] = "data\\units\\skeleton_archer\\shot4.png";
            this.pics_hit[2] = "data\\units\\skeleton_archer\\shot3.png";
            this.pics_hit[3] = "data\\units\\skeleton_archer\\shot2.png";
            this.pics_hit[4] = "data\\units\\skeleton_archer\\shot1.png";
            this.fraction = "skeleton";
            this.weapon = "bow";
            this.arrow_type = "leather_arrow";
            this.attack_distance = 90;
            this.max_cooldown = 99;
            this.speed = 20;
            this.max_hp = 100;
        }
        if (type == "skeleton_swordman") {
            this.pics_move[0] = "data\\units\\skeleton_swordman\\move0.png";
            this.pics_move[1] = "data\\units\\skeleton_swordman\\move1.png";
            this.pics_hit[0] = "data\\units\\skeleton_swordman\\shot0.png";
            this.pics_hit[1] = "data\\units\\skeleton_swordman\\shot4.png";
            this.pics_hit[2] = "data\\units\\skeleton_swordman\\shot3.png";
            this.pics_hit[3] = "data\\units\\skeleton_swordman\\shot2.png";
            this.pics_hit[4] = "data\\units\\skeleton_swordman\\shot1.png";
            this.fraction = "skeleton";
            this.weapon = "sword";
            this.damage = 20;
            this.attack_distance = 6;
            this.max_cooldown = 49;
            this.speed = 20;
            this.max_hp = 100;
        }

        Random random = new Random();
        this.type = type;
        this.t_x = this.x + random.nextInt(81) - 40;
        this.t_y = this.y + random.nextInt(81) - 40;
        this.old_x = this.x;
        this.old_y = this.y;
        this.hp = this.max_hp;
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
        double x2 = this.x + Math.sin(this.angle) * 4;
        double y2 = this.y - Math.cos(this.angle) * 4;
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

        if ((- pi / 7 < dif) && (dif < pi / 7) || (2 * pi - pi / 7 < dif) || (-2 * pi + pi / 7 < dif)) {
            if (gip <= unit.attack_distance) {
                return true;
            }
        }
        return false;
    }

    boolean spear_intersection(Unit_object unit) {
        for (int i = 4; i <= this.attack_distance + 4; ++i) {
            double x2 = this.x + Math.sin(this.angle) * i;
            double y2 = this.y - Math.cos(this.angle) * i;
            double x1 = unit.x;
            double y1 = unit.y;
            double gip = Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), 0.5);
            if (gip < unit.size) {
                return true;
            }
        }
        return false;
    }
}