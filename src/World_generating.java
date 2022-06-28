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

        add_cross(true);
        for (int k = 0; k < 10; k++) {
            add_cross(false);
        }

        for (int i = 0; i < localities.size(); i++) {
            for (int j = i + 1; j < localities.size(); j++) {
                if (localities.get(j).distance_to_cener > localities.get(i).distance_to_cener) {
                    Collections.swap(localities, i, j);
                }
            }
        }

        for (int i = 0; i < localities.size() - 1; i++) {
            int path = localities.size() - 1;
            double path_gip = localities.get(i).distance_to_cener;
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
            if (path != localities.size() - 1) {
                localities.get(path).there_road = true;
            }
            //localities.get(i).there_road = true;
        }

        int cross_count = localities.size() - 1;

        for (int k = 0; k < 10; k++) {
            if (add_camp()) {
                int i = localities.size() - 1;
                int path = cross_count - 1;
                double path_gip = localities.get(i).distance_to_cener;
                for (int j = 0; j < cross_count; j++) {
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
                add_path(i, path);
            }
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
                int x = (coord_x - map_size_x / 2) * 27;
                int y = (coord_y - map_size_y / 2) * 27;
                int size = random.nextInt((map_size_x + map_size_y) / 12) + (map_size_x + map_size_y) / 12;
                add_forest(x, y, type, size, size);
            }
        }

        for (int i = 0; i < map_size_x; i++) {
            for (int j = 0; j < map_size_y; j++) {
                if (hexes[i][j].hex_type == "nothing") {
                    Land_object grass;
                    grass = new Land_object((i - map_size_x / 2) * 27, (j - map_size_y / 2) * 27, 0);
                    grass.setType("grass");
                    hexes[i][j].lands.add(grass);
                    hexes[i][j].hex_type = "grass";
                }
            }
        }
    }

    void add_cross(boolean start) {
        Random random = new Random();
        double x;
        double y;
        boolean city_is_near;
        int attempt = 100;
        int coord_x, coord_y;
        if (start) {
            x = 0;
            y = 0;
            coord_x = map_size_x / 2;
            coord_y = map_size_y / 2;
        } else {
            do {
                city_is_near = false;
                coord_x = random.nextInt(map_size_x / 5 - 1) * 5 + 5;
                coord_y = random.nextInt(map_size_y / 5 - 1) * 5 + 5;
                x = ((coord_x - map_size_x / 2) * 27);
                y = ((coord_y - map_size_y / 2) * 27);
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
        }
        if (attempt > 0) {
            double distance;
            if (start) {
                distance = 0;
                for(int i = -1; i <= 1; i ++) {
                    Unit_spawn("elf", x + i, y, 0);
                }
            } else {
                distance = Math.pow((localities.get(0).x - x) * (localities.get(0).x - x) +
                        (localities.get(0).y - y) * (localities.get(0).y - y), 0.5);
            }
            hexes[coord_x][coord_y].hex_type = "cross";
            Land_object road = new Land_object(x, y, 0);
            road.setType("road");
            hexes[coord_x][coord_y].lands.add(road);
            localities.add(new Locality_object(x, y, distance, "cross"));
        }
    }

    boolean add_camp() {
        Random random = new Random();
        double x;
        double y;
        boolean city_is_near;
        int attempt = 100;
        int coord_x, coord_y;
        do {
            city_is_near = false;
            coord_x = random.nextInt(map_size_x / 5 - 1) * 5 + 5;
            coord_y = random.nextInt(map_size_y / 5 - 1) * 5 + 5;
            x = ((coord_x - map_size_x / 2) * 27);
            y = ((coord_y - map_size_y / 2) * 27);
            for (int k = 0; k < localities.size(); k++) {
                double gip = Math.pow((x - localities.get(k).x) * (x - localities.get(k).x) +
                        (y - localities.get(k).y) * (y - localities.get(k).y), 0.5);
                int distance = 210;
                if (gip < distance) {
                    city_is_near = true;
                }
            }
            attempt -= 1;
        } while ((city_is_near || hexes[coord_x][coord_y].hex_type == "road") && attempt > 0);
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
            return true;
        } else {
            return false;
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
        for (int x = min + 27; x < max; x += 27) {
            if (hexes[x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].hex_type == "nothing") {
                Land_object road = new Land_object(x, other_coord_y, 0);
                road.setType("road");
                hexes[x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].lands.add(road);
                hexes[x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].hex_type = "road";
            }
        }
        min = (int) (Math.min(localities.get(i).y, localities.get(j).y));
        max = (int) (Math.max(localities.get(i).y, localities.get(j).y));
        if (r) {
            other_coord_x = (int) localities.get(i).x;
        } else {
            other_coord_x = (int) localities.get(j).x;
        }
        for (int y = min + 27; y < max; y += 27) {
            if (hexes[other_coord_x / 27 + map_size_x / 2][y / 27 + map_size_y / 2].hex_type == "nothing") {
                Land_object road = new Land_object(other_coord_x, y, 0);
                road.setType("road");
                hexes[other_coord_x / 27 + map_size_x / 2][y / 27 + map_size_y / 2].lands.add(road);
                hexes[other_coord_x / 27 + map_size_x / 2][y / 27 + map_size_y / 2].hex_type = "road";
            }
        }
        if (hexes[other_coord_x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].hex_type == "nothing") {
            Land_object road = new Land_object(other_coord_x, other_coord_y, 0);
            road.setType("road");
            hexes[other_coord_x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].lands.add(road);
            hexes[other_coord_x / 27 + map_size_x / 2][other_coord_y / 27 + map_size_y / 2].hex_type = "road";
        }
    }

    void add_forest(int x, int y, String type, int size, int max_size) {
        Random random = new Random();
        int hex_x = x / 27 + map_size_x / 2;
        int hex_y = y / 27 + map_size_y / 2;
        if (hexes[hex_x][hex_y].hex_type == "nothing") {
            hexes[hex_x][hex_y].hex_type = type;
            int x_in_heks = random.nextInt(21) - 10;
            int y_in_heks = random.nextInt(21) - 10;
            Barrier_object tree = new Barrier_object(x + x_in_heks, y + y_in_heks, 0);
            tree.setType(type);
            hexes[hex_x][hex_y].barriers.add(tree);
        }
        int chance;
        chance = random.nextInt(max_size);
        if ((x - 27) > - map_size_x / 2 * 27) {
            if (chance < size) {
                add_forest(x - 27, y, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((x + 27) < map_size_x / 2 * 27) {
            if (chance < size) {
                add_forest(x + 27, y, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((y - 27) > - map_size_y / 2 * 27) {
            if (chance < size) {
                add_forest(x, y - 27, type, size - 1, max_size);
            }
        }
        chance = random.nextInt(max_size);
        if ((y + 27) < map_size_y / 2 * 27) {
            if (chance < size) {
                add_forest(x, y + 27, type, size - 1, max_size);
            }
        }
    }

    void Unit_spawn(String type, double x, double y, double angle) {
        Unit_object unit = new Unit_object(x, y, angle);
        unit.setType(type);
        double hex_x = x / 27 + map_size_x / 2;
        double hex_y = y / 27 + map_size_y / 2;
        hexes[(int) hex_x][(int) hex_y].units.add(unit);
    }
}