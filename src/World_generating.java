import java.util.*;

public class World_generating {
    int map_size_x, map_size_y;
    Hex[][] hexes;
    List<Locality_object> localities;

    World_generating(int map_size_x, int map_size_y, Hex[][] hexes, List<Locality_object> localities) {
        this.map_size_x = map_size_x;
        this.map_size_y = map_size_y;
        this.hexes = hexes;
        this.localities = localities;
    }

    Hex[][] getHexes() {
        return this.hexes;
    }

    List<Locality_object> getLocalities() {
        return this.localities;
    }

///////////////////////////////////////////////////////////MAIN/////////////////////////////////////////////////////////

    void Generate() {
        for (int i = 0; i < map_size_x; i++) {
            for (int j = 0; j < map_size_y; j++) {
                hexes[i][j].hex_type = "nothing";
            }
        }

        add_central_cross();
        for (int k = 0; k < 8; k++) {
            add_camp();
        }

        for (int i = 0; i < localities.size(); i++) {
            for (int j = i + 1; j < localities.size(); j++) {
                if (localities.get(j).distance_to_cross > localities.get(i).distance_to_cross) {
                    Collections.swap(localities, i, j);
                }
            }
        }

        for (int i = 0; i < localities.size(); i++) {
            int path = localities.size() - 1;
            double path_gip = localities.size() - 1;
            for (int j = i + 1; j < localities.size() - 1; j++) {
                if (!localities.get(j).there_road) {
                    double x1 = localities.get(i).x;
                    double x2 = localities.get(j).x;
                    double y1 = localities.get(i).y;
                    double y2 = localities.get(j).y;
                    double gip = Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 0.5);
                    if (gip < path_gip) {
                        path = j;
                        path_gip = gip;
                    }
                }
            }
            add_path(i, path);
            if (localities.get(path).type == "camp") {
                localities.get(path).there_road = true;
            }
            localities.get(i).there_road = true;
        }

        Random random = new Random();
        String type;
        int coord_x, coord_y;

        for (int i = 0; i < 8; i++) {
            if (random.nextBoolean()) {
                type = "birch";
            } else {
                type = "green_small_tree";
            }
            if (i == 0) {
                coord_x = map_size_x / 2;
                coord_y = map_size_y / 2;
            } else {
                coord_x = random.nextInt(map_size_x - 1);
                coord_y = random.nextInt(map_size_y - 1);
            }
            if (hexes[coord_x][coord_y].hex_type != "birch" &&
                    hexes[coord_x][coord_y].hex_type != "green_small_tree") {
                int x = (coord_x - map_size_x / 2) * 35;
                int y = (coord_y - map_size_y / 2) * 35;
                int size = random.nextInt((map_size_x + map_size_y) / 12) + (map_size_x + map_size_y) / 12;
                add_forest(x, y, type, size, size);
            }
        }
    }

    void add_central_cross() {
        double x = 0;
        double y = 0;
        int coord_x = map_size_x / 2;
        int coord_y = map_size_y / 2;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                hexes[coord_x + i][coord_y + j].hex_type = "central_cross";
            }
        }
        Land_object road = new Land_object(x, y, 0);
        road.setType("road");
        hexes[coord_x][coord_y].lands.add(road);
        localities.add(new Locality_object(x, y, 0, "central_cross"));
        for(int i = -1; i <= 1; i ++) {
            Unit_spawn("elf", x + i, y, 0);
        }
    }

    void add_camp() {
        Random random = new Random();
        double x;
        double y;
        boolean city_is_near;
        int attempt = 100;
        int coord_x, coord_y;
        do {
            city_is_near = false;
            coord_x = random.nextInt(map_size_x - 7) + 4;
            coord_y = random.nextInt(map_size_y - 7) + 4;
            x = ((coord_x - map_size_x / 2) * 35);
            y = ((coord_y - map_size_y / 2) * 35);
            for (int k = 0; k < localities.size(); k++) {
                double gip = Math.pow((x - localities.get(k).x) * (x - localities.get(k).x) +
                        (y - localities.get(k).y) * (y - localities.get(k).y), 0.5);
                int distance = 210;
                if (gip < distance) {
                    city_is_near = true;
                }
            }
            attempt -= 1;
        } while (city_is_near & attempt > 0);
        if (attempt > 0) {
            double distance = Math.pow((localities.get(0).x - x) * (localities.get(0).x - x) +
                    (localities.get(0).y - y) * (localities.get(0).y - y), 0.5);
            localities.add(new Locality_object(x, y, distance, "camp"));
            Land_object camp = new Land_object(x, y, 0);
            if (random.nextBoolean()) {
                camp.setType("camp_birch");
                for(int i = -1; i <= 1; i ++) {
                    Unit_spawn("elf", x + i, y, 0);
                }
            } else {
                camp.setType("camp_fir");
                for(int i = -1; i <= 1; i ++) {
                    Unit_spawn("skeleton", x + i, y, 0);
                }
            }
            hexes[coord_x][coord_y].lands.add(camp);
            hexes[coord_x][coord_y].hex_type = "camp";
        }
    }

    void add_path(int i, int j) {
        Random random = new Random();
        boolean r = random.nextBoolean();
        int min = ((int) Math.min(localities.get(i).x, localities.get(j).x));
        int max = ((int) Math.max(localities.get(i).x, localities.get(j).x));
        int other_coord_x, other_coord_y;
        if (r) {
            other_coord_y = (int) localities.get(j).y;
        } else {
            other_coord_y = (int) localities.get(i).y;
        }
        for (int x = min + 35; x < max; x += 35) {
            Land_object road = new Land_object(x, other_coord_y, 0);
            road.setType("road");
            hexes[x / 35 + map_size_x / 2][other_coord_y / 35 + map_size_y / 2].lands.add(road);
            hexes[x / 35 + map_size_x / 2][other_coord_y / 35 + map_size_y / 2].hex_type = "road";
        }
        min = (int) (Math.min(localities.get(i).y, localities.get(j).y));
        max = (int) (Math.max(localities.get(i).y, localities.get(j).y));
        if (r) {
            other_coord_x = (int) localities.get(i).x;
        } else {
            other_coord_x = (int) localities.get(j).x;
        }
        for (int y = min + 35; y < max; y += 35) {
            Land_object road = new Land_object(other_coord_x, y, 0);
            road.setType("road");
            hexes[other_coord_x / 35 + map_size_x / 2][y / 35 + map_size_y / 2].lands.add(road);
            hexes[other_coord_x / 35 + map_size_x / 2][y / 35 + map_size_y / 2].hex_type = "road";
        }
        Land_object road = new Land_object(other_coord_x, other_coord_y, 0);
        road.setType("road");
        hexes[other_coord_x / 35 + map_size_x / 2][other_coord_y / 35 + map_size_y / 2].lands.add(road);
        hexes[other_coord_x / 35 + map_size_x / 2][other_coord_y / 35 + map_size_y / 2].hex_type = "road";
    }

    void add_forest(int x, int y, String type, int size, int max_size) {
        Random random = new Random();
        int hex_x = x / 35 + map_size_x / 2;
        int hex_y = y / 35 + map_size_y / 2;
        if (hexes[hex_x][hex_y].hex_type == "nothing") {
            hexes[hex_x][hex_y].hex_type = type;
            int x_in_heks = random.nextInt(33) - 17;
            int y_in_heks = random.nextInt(33) - 17;
            Barrier_object tree = new Barrier_object(x + x_in_heks, y + y_in_heks, 0);
            tree.setType(type);
            hexes[hex_x][hex_y].barriers.add(tree);
        }
        int chance;
        chance = random.nextInt(max_size);
        if ((x - 35) > - map_size_x / 2 * 35) {
            if (chance < size) {
                add_forest(x - 35, y, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((x + 35) < map_size_x / 2 * 35) {
            if (chance < size) {
                add_forest(x + 35, y, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((y - 35) > - map_size_y / 2 * 35) {
            if (chance < size) {
                add_forest(x, y - 35, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((y + 35) < map_size_y / 2 * 35) {
            if (chance < size) {
                add_forest(x, y + 35, type, size - 1, max_size);
            }
        }
    }

    void Unit_spawn(String type, double x, double y, double angle) {
        Unit_object unit = new Unit_object(x, y, angle);
        unit.setType(type);
        double hex_x = x / 35 + map_size_x / 2;
        double hex_y = y / 35 + map_size_y / 2;
        hexes[(int) hex_x][(int) hex_y].units.add(unit);
    }
}