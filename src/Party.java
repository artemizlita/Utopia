import java.lang.Math;
import java.util.List;
import java.util.HashMap;

class Party_object {
    String fraction;
    double t_x;
    double t_y;

    Party_object (String fraction, double t_x, double t_y) {
        this.fraction = fraction;
        this.t_x = t_x;
        this.t_y = t_y;
    }

    {
        HashMap<String, String> units_exp = new HashMap<>();
        units_exp.put("elf_leather_bow", "0");
        units_exp.put("elf_iron_bow", "0");
        units_exp.put("elf_mythril_bow", "0");

        units_exp.put("skeleton_archer", "0");

        units_exp.put("elf_swordman", "0");
        units_exp.put("skeleton_swordman", "0");

        units_exp.put("orc_leather_twohand", "0");
        units_exp.put("orc_leather_crossbow", "0");
    }
}