import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;
import java.util.List;

import java.lang.Math;

public class Main extends JPanel implements ActionListener{
    Timer timer = new Timer(30, this);
    JFrame frame;

    int center_x;
    int center_y;
    int map_size_x = 20;
    int map_size_y = 20;
    double player_x = 0;
    double player_y = 0;
    double cursorx, cursory;
    short scale = 4;
    int time = 0;

    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isHit = false;
    private boolean minimap = false;

    Hex[][] hexes = new Hex[map_size_x][map_size_y];
    List<Locality_object> localities = new ArrayList<Locality_object>();

//////////////////////////////////////////////////////ADD UNITS/////////////////////////////////////////////////////////

    void Add_units() {
        Random random = new Random();
        Unit_spawn("elf", 0, 10, 0, true);

//        for(int i = 20; i < 30; i += 10) {
//            Unit_spawn("elf", i, random.nextInt(10) - 20, 0, false);
//        }
//
//        for(int i = 20; i < 40; i += 10) {
//            Unit_spawn("skeleton", i, random.nextInt(10) - 120, Math.PI, false);
//        }
    }

    void Unit_spawn(String type, double x, double y, double angle, boolean player) {
        Unit_object unit = new Unit_object(x, y, angle);
        unit.setType(type);
        double hex_x = x / 35 + map_size_x / 2;
        double hex_y = y / 35 + map_size_y / 2;
        if (player) {
            player_x = x;
            player_y = y;
            unit.player = 1;
            hexes[(int) hex_x][(int) hex_y].units.add(0, unit);
        } else {
            hexes[(int) hex_x][(int) hex_y].units.add(unit);
        }
    }

/////////////////////////////////////////////////////////////MAIN///////////////////////////////////////////////////////

    public Main(JFrame frame) {
        for (int i = 0; i < map_size_x; i++) {
            for (int j = 0; j < map_size_y; j++) {
                hexes[i][j] = new Hex(new ArrayList<Land_object>(),
                        new ArrayList<Unit_object>(),
                        new ArrayList<Shot_object>(),
                        new ArrayList<Fallen_object>(),
                        new ArrayList<Barrier_object>());
            }
        }

        World_generating new_world = new World_generating(map_size_x, map_size_y, hexes, localities);
        new_world.Generate();
        hexes = new_world.getHexes();
        localities = new_world.getLocalities();

        Add_units();

        center_x = 720;
        center_y = 450;
        this.frame = frame;
        timer.start();

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                isHit = true;
            }

            public void mouseReleased(MouseEvent e) {
                isHit = false;
            }
        });

//        frame.addMouseWheelListener(new MouseAdapter() {
//            public void mouseWheelMoved(MouseWheelEvent e) {
//                if (e.getWheelRotation() < 0) {
//                    if (scale < 4) {
//                        scale *= 2;
//                    }
//                } else if (e.getWheelRotation() > 0) {
//                    if (scale > 1) {
//                        scale /= 2;
//                    }
//                }
//            }
//        });

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) isUp = true;
                if (e.getKeyCode() == KeyEvent.VK_S) isDown = true;
                if (e.getKeyCode() == KeyEvent.VK_A) isLeft = true;
                if (e.getKeyCode() == KeyEvent.VK_D) isRight = true;
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
                if (e.getKeyCode() == KeyEvent.VK_M) minimap = true;
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) isLeft = false;
                if (e.getKeyCode() == KeyEvent.VK_D) isRight = false;
                if (e.getKeyCode() == KeyEvent.VK_W) isUp = false;
                if (e.getKeyCode() == KeyEvent.VK_S) isDown = false;
                if (e.getKeyCode() == KeyEvent.VK_M) minimap = false;
            }
        });
    }

/////////////////////////////////////////////////////////PAINTING///////////////////////////////////////////////////////

    public void paint(Graphics g) {
        if (minimap) {

            Color grass = new Color(239, 228, 176);
            g.setColor(grass);
            g.fillRect(0, 0, 1440, 900);

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {

                }
            }

        } else {

            Point location = MouseInfo.getPointerInfo().getLocation();
            cursorx = (-center_x + location.getX()) / scale;
            cursory = (-center_y + location.getY()) / scale;

            Color floor = new Color(181, 230, 29);
            g.setColor(floor);
            g.fillRect(0, 0, 1440, 900);

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {
                    for (int k = 0; k < hexes[i][j].lands.size(); k++) {
                        if ((35 * i - map_size_x * 35 < player_x + 2 * center_x) &
                                (35 * i - map_size_x * 35 > player_x - 2 * center_x) &
                                (35 * j - map_size_y * 35 < player_y + 2 * center_y) &
                                (35 * j - map_size_y * 35 > player_y - 2 * center_y)) {
                            Land_object land = hexes[i][j].lands.get(k);
                            paint_Sprite(g, new ImageIcon(land.pic).getImage(), land.angle, false,
                                    (int) (center_x + (land.x - land.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (land.y - land.pic_size / 2 - player_y) * scale));
                        }
                    }
                }
            }

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {
                    for (int k = 0; k < hexes[i][j].fallens.size(); k++) {
                        if ((35 * i - map_size_x * 35 < player_x + 2 * center_x) &
                                (35 * i - map_size_x * 35 > player_x - 2 * center_x) &
                                (35 * j - map_size_y * 35 < player_y + 2 * center_y) &
                                (35 * j - map_size_y * 35 > player_y - 2 * center_y)) {
                            Fallen_object fallen = hexes[i][j].fallens.get(k);
                            paint_Sprite(g, new ImageIcon(fallen.pic).getImage(), fallen.angle, false,
                                    (int) (center_x + (fallen.x - fallen.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (fallen.y - fallen.pic_size / 2 - player_y) * scale));
                        }
                    }
                }
            }

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {
                    for (int k = 0; k < hexes[i][j].units.size(); k++) {
                        if ((35 * (i - map_size_x) < player_x + 2 * center_x) &
                                (35 * (i - map_size_x) > player_x - 2 * center_x) &
                                (35 * (j - map_size_y) < player_y + 2 * center_y) &
                                (35 * (j - map_size_y) > player_y - 2 * center_y)) {
                            Unit_object unit = hexes[i][j].units.get(k);
                            paint_Sprite(g, new ImageIcon(unit.pics_move[(unit.wealth / 20) % 2]).getImage(),
                                    unit.angle, false,
                                    (int) (center_x + (unit.x - unit.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (unit.y - unit.pic_size / 2 - player_y) * scale));
                            paint_Sprite(g, new ImageIcon(unit.pics_hit[unit.cooldown / 10]).getImage(),
                                    unit.angle, false,
                                    (int) (center_x + (unit.x - unit.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (unit.y - unit.pic_size / 2 - player_y) * scale));
                        }
                    }
                }
            }

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {
                    for (int k = 0; k < hexes[i][j].shots.size(); k++) {
                        if ((35 * (i - map_size_x) < player_x + 2 * center_x) &
                                (35 * (i - map_size_x) > player_x - 2 * center_x) &
                                (35 * (j - map_size_y) < player_y + 2 * center_y) &
                                (35 * (j - map_size_y) > player_y - 2 * center_y)) {
                            Shot_object shot = hexes[i][j].shots.get(k);
                            paint_Sprite(g, new ImageIcon(shot.pic).getImage(), shot.angle, false,
                                    (int) (center_x + (shot.x - shot.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (shot.y - shot.pic_size / 2 - player_y) * scale));
                        }
                    }
                }
            }

            for (int i = 0; i < map_size_x; i++) {
                for (int j = 0; j < map_size_y; j++) {
                    for (int k = 0; k < hexes[i][j].barriers.size(); k++) {
                        if ((35 * (i - map_size_x) < player_x + 2 * center_x) &
                                (35 * (i - map_size_x) > player_x - 2 * center_x) &
                                (35 * (j - map_size_y) < player_y + 2 * center_y) &
                                (35 * (j - map_size_y) > player_y - 2 * center_y)) {
                            Barrier_object barrier = hexes[i][j].barriers.get(k);
                            paint_Sprite(g, new ImageIcon(barrier.pic).getImage(), barrier.angle, false,
                                    (int) (center_x + (barrier.x - barrier.pic_size / 2 - player_x) * scale),
                                    (int) (center_y + (barrier.y - barrier.pic_size / 2 - player_y) * scale));
                        }
                    }
                }
            }

            Image cursor = new ImageIcon("data\\target.png").getImage();
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform affine = new AffineTransform();
            int x = (int) (center_x + cursorx * scale - 7);
            int y = (int) (center_y + cursory * scale - 7);
            affine.rotate(0, x + cursor.getWidth(null) * scale / 2,
                    y + cursor.getHeight(null) * scale / 2);
            g2d.setTransform(affine);
            g2d.drawImage(cursor, x, y, 14, 14, null);
        }
    }

    void paint_Sprite(Graphics g, Image sprite, double angle, boolean minimap, int x, int y) {
        int this_scale;
        if (minimap) {
            this_scale = 1;
        } else {
            this_scale = scale;
        }
        Graphics2D g2d2 = (Graphics2D) g;
        g2d2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform affine2 = new AffineTransform();
        affine2.rotate(angle, x + sprite.getWidth(null) * this_scale / 2,
                y + sprite.getHeight(null) * this_scale / 2);
        g2d2.setTransform(affine2);
        g2d2.drawImage(sprite, x, y, (int) sprite.getWidth(null) * this_scale,
                (int) sprite.getHeight(null) * this_scale, null);
    }

//////////////////////////////////////////////////////////////CONTROL///////////////////////////////////////////////////

    public void actionPerformed(ActionEvent e) {
        long start = System.currentTimeMillis();

        double hex_x, hex_y;

        for (int i = 1; i < map_size_x; i++) {
            for (int j = 1; j < map_size_y; j++) {
                for (int k = 0; k < hexes[i][j].units.size(); k++) {
                    Unit_object unit = hexes[i][j].units.get(k);

                    if (unit.player == 1) {
                        if (unit.hp < 0) {
                            System.exit(0);
                        }
                        player_move(isUp, isDown, isLeft, isRight);
                    } else {
                        if (unit.hp < 0) {
                            Fallen_object body = new Fallen_object(Math.round(unit.x), Math.round(unit.y), unit.angle);
                            body.setType(unit.type);
                            hexes[i][j].fallens.add(body);
                            hexes[i][j].units.remove(k);
                        }
                        AI_move(unit);
                    }

                    if (unit.cooldown > 0) {
                        unit.cooldown -= 1;
                        if (unit.cooldown == 19) {
                            double shot_x = unit.x + Math.sin(unit.angle) * 10;
                            double shot_y = unit.y - Math.cos(unit.angle) * 10;
                            Shot_object arrow = new Shot_object(shot_x, shot_y, unit.angle);
                            arrow.setType("arrow");
                            hex_x = shot_x / 35 + map_size_x / 2;
                            hex_y = shot_x / 35 + map_size_x / 2;
                            hexes[(int) hex_x][(int) hex_y].shots.add(arrow);
                        }
                    }

                    for (int bi = -1; bi <= 1; bi++) {
                        for (int bj = -1; bj <= 1; bj++) {
                            for (int bk = 0; bk < hexes[i + bi][j + bj].units.size(); bk++) {
                                Unit_object other_unit = hexes[i + bi][j + bj].units.get(bk);
                                if (!unit.equals(other_unit)) {
                                    double gip = Math.pow((unit.x - other_unit.x) * (unit.x - other_unit.x) +
                                            (unit.y - other_unit.y) * (unit.y - other_unit.y), 0.5);
                                    if (gip < (unit.size + other_unit.size) / 2) {
                                        double dif = ((unit.size + other_unit.size) / 2 - gip) / 2;
                                        double i_angle;
                                        if ((other_unit.x - unit.x) >= 0) {
                                            i_angle = 2 * Math.PI - Math.acos((other_unit.y - unit.y) / gip);
                                        } else {
                                            i_angle = Math.acos((other_unit.y - unit.y) / gip);
                                        }
                                        unit.x += Math.sin(i_angle) * dif;
                                        unit.y -= Math.cos(i_angle) * dif;
                                        if (unit.player == 1) {
                                            player_x += Math.sin(i_angle) * dif;
                                            player_y -= Math.cos(i_angle) * dif;
                                        } else {
                                            unit.t_x = unit.x;
                                            unit.t_y = unit.y;
                                        }
                                        other_unit.x -= Math.sin(i_angle) * dif;
                                        other_unit.y += Math.cos(i_angle) * dif;
                                        if (other_unit.player == 1) {
                                            player_x -= Math.sin(i_angle) * dif;
                                            player_y += Math.cos(i_angle) * dif;
                                        } else {
                                            unit.t_x = unit.x;
                                            unit.t_y = unit.y;
                                        }
                                    }
                                }
                            }
                        }
                    }

//                    for (int bi = -1; bi <= 1; bi++) {
//                        for (int bj = -1; bj <= 1; bj++) {
//                            for (int bk = 0; bk < hexes[i + bi][j + bj].barriers.size(); bk++) {
//                                Barrier_object barrier = hexes[i + bi][j + bj].barriers.get(bk);
//                                if (unit.unit_barrier_intersection(barrier)) {
//                                    if (barrier.pic == "data\\objects\\green_tree.png" ||
//                                            barrier.pic == "data\\objects\\green_small_tree.png" ||
//                                            barrier.pic == "data\\objects\\bush.png" ||
//                                            barrier.pic == "data\\objects\\birch.png") {
//                                        barrier.tree_fall(unit, hexes[i + bi][j + bj].fallens);
//                                        hexes[i + bi][j + bj].barriers.remove(bk);
//                                    } else if (barrier.pic == "data\\objects\\fence.png" ||
//                                            barrier.pic == "data\\objects\\wooden_fence.png" ||
//                                            barrier.pic == "data\\objects\\gate.png" ||
//                                            barrier.pic == "data\\objects\\concrete_wall.png") {
//                                        barrier.fence_fall(unit, hexes[i + bi][j + bj].fallens);
//                                        hexes[i + bi][j + bj].barriers.remove(bk);
//                                    }
//                                }
//                            }
//                        }
//                    }

                }

                for (int k = 0; k < hexes[i][j].shots.size(); k++) {
                    Shot_object shot = hexes[i][j].shots.get(k);
                    if (shot.distance > 80) {
                        hexes[i][j].shots.remove(k);
                    } else {
                        shot.x += Math.sin(shot.angle) * shot.speed / 50;
                        shot.y -= Math.cos(shot.angle) * shot.speed / 50;
                        shot.distance += shot.speed / 50;
                        for (int ni = -1; ni <= 1; ni++) {
                            for (int nj = -1; nj <= 1; nj++) {
                                for (int nk = 0; nk < hexes[i + ni][j + nj].units.size(); nk++) {
                                    Unit_object unit = hexes[i + ni][j + nj].units.get(nk);
                                    double gip = Math.pow((unit.x - shot.x) * (unit.x - shot.x) +
                                            (unit.y - shot.y) * (unit.y - shot.y), 0.5);
                                    if (gip < unit.size / 2) {
                                        unit.hp -= 20;
                                        hexes[i][j].shots.remove(k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < map_size_x; i++) {
            for (int j = 0; j < map_size_y; j++) {
                for (int k = 0; k < hexes[i][j].units.size(); k++) {
                    Unit_object unit = hexes[i][j].units.get(k);
                    hex_x = unit.x / 35 + map_size_x / 2;
                    hex_y = unit.y / 35 + map_size_y / 2;
                    if (hex_x != i || hex_y != j) {
                        hexes[i][j].units.remove(k);
                        if (unit.player == 1) {
                            hexes[(int) hex_x][(int) hex_y].units.add(0, unit);
                        } else {
                            hexes[(int) hex_x][(int) hex_y].units.add(unit);
                        }
                    }
                }

                for (int k = 0; k < hexes[i][j].shots.size(); k++) {
                    Shot_object shot = hexes[i][j].shots.get(k);
                    hex_x = shot.x / 35 + map_size_x / 2;
                    hex_y = shot.y / 35 + map_size_y / 2;
                    if (hex_x != i || hex_y != j) {
                        hexes[i][j].shots.remove(k);
                        hexes[(int) hex_x][(int) hex_y].shots.add(shot);
                    }
                }
            }
        }

        time += 1;
        repaint();
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println(elapsed);
    }

    void player_move(boolean isUp, boolean isDown, boolean isLeft, boolean isRight) {
        double hex_x = player_x / 35 + map_size_x / 2;
        double hex_y = player_y / 35 + map_size_y / 2;
        Unit_object player_unit = hexes[(int) hex_x][(int) hex_y].units.get(0);

        player_unit.old_x = player_unit.x;
        player_unit.old_y = player_unit.y;

        player_unit.set_target(cursorx + player_x, cursory + player_y);
        if (isHit) {
            if (player_unit.cooldown == 0) {
                player_unit.cooldown = player_unit.max_cooldown;
            }
        } else {
            player_unit.cooldown = 0;
        }

        double speed = player_unit.speed / 50;
        if (isUp & !isDown & !isLeft & !isRight) {
            player_unit.x += Math.sin(player_unit.angle) * speed;
            player_unit.y -= Math.cos(player_unit.angle) * speed;
            player_x += Math.sin(player_unit.angle) * speed;
            player_y -= Math.cos(player_unit.angle) * speed;
            player_unit.wealth -= 2;
        }
        if (!isUp & isDown & !isLeft & !isRight) {
            player_unit.x -= Math.sin(player_unit.angle) * speed / 2;
            player_unit.y += Math.cos(player_unit.angle) * speed / 2;
            player_x -= Math.sin(player_unit.angle) * speed / 2;
            player_y += Math.cos(player_unit.angle) * speed / 2;
            player_unit.wealth -= 1;
        }
        if (isLeft & !isRight & !isUp & !isDown) {
            player_unit.x -= Math.cos(player_unit.angle) * speed / 2;
            player_unit.y -= Math.sin(player_unit.angle) * speed / 2;
            player_x -= Math.cos(player_unit.angle) * speed / 2;
            player_y -= Math.sin(player_unit.angle) * speed / 2;
            player_unit.wealth -= 1;
        }
        if (!isLeft & isRight & !isUp & !isDown) {
            player_unit.x += Math.cos(player_unit.angle) * speed / 2;
            player_unit.y += Math.sin(player_unit.angle) * speed / 2;
            player_x += Math.cos(player_unit.angle) * speed / 2;
            player_y += Math.sin(player_unit.angle) * speed / 2;
            player_unit.wealth -= 1;
        }
    }

    void AI_move(Unit_object unit) {
        int hex_x = (int) (unit.x / 35 + map_size_x / 2);
        int hex_y = (int) (unit.y / 35 + map_size_y / 2);

        unit.old_x = unit.x;
        unit.old_y = unit.y;

        double mingip = 105;
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = 0; k < hexes[hex_x + i][hex_y + j].units.size(); k++) {
                    Unit_object other_unit = hexes[hex_x + i][hex_y + j].units.get(k);
                    double gip = Math.pow((unit.x - other_unit.x) * (unit.x - other_unit.x) +
                            (unit.y - other_unit.y) * (unit.y - other_unit.y), 0.5);
                    if (gip < mingip && unit.fraction != other_unit.fraction) {
                        unit.t_x = other_unit.x;
                        unit.t_y = other_unit.y;
                        mingip = gip;
                    }
                }
            }
        }

        if (mingip == 105) {
            Random random = new Random();
            double gip = Math.pow((unit.x - unit.t_x) * (unit.x - unit.t_x) +
                    (unit.y - unit.t_y) * (unit.y - unit.t_y), 0.5);
            if (gip < unit.speed / 25) {
                do {
                    unit.t_x = unit.x + random.nextInt(71) - 35;
                    unit.t_y = unit.y + random.nextInt(71) - 35;
                } while (unit.t_x < -map_size_x / 2 * 35 + 105 || unit.t_x > map_size_x / 2 * 35 - 105 ||
                        unit.t_y < -map_size_y / 2 * 35 + 105 || unit.t_y > map_size_y / 2 * 35 - 105);
            }
        }

        unit.set_target(unit.t_x, unit.t_y);

        if (mingip < 90) {
            if (unit.cooldown == 0) {
                unit.cooldown = 49;
            }
        } else {
            double speed = unit.speed / 50;
            unit.x += Math.sin(unit.angle) * speed;
            unit.y -= Math.cos(unit.angle) * speed;
            unit.wealth -= 2;
        }
    }
}