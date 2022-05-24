package CatchADrop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CatchADrop extends JFrame {

    private static CatchADrop catch_a_drop;
    private static long last_frame_time;
    private static Image fon;
    private static Image game;
    private static Image crug;
    private static float crug_left = 200;
    private static float crug_top = -100;
    private static float crug_v = 200;
    private static int score;


    public static void main(String[] args) throws IOException {
        fon = ImageIO.read(CatchADrop.class.getResourceAsStream("fon.png"));
        game = ImageIO.read(CatchADrop.class.getResourceAsStream("game.png"));
        crug = ImageIO.read(CatchADrop.class.getResourceAsStream("crug.png"));
        catch_a_drop = new CatchADrop();
        catch_a_drop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        catch_a_drop.setLocation(200, 100);
        catch_a_drop.setSize(900, 500);
        catch_a_drop.setResizable(true);
        last_frame_time = System.nanoTime();
        GameField game_Field = new GameField();
        game_Field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float crug_right = crug_left + crug.getWidth(null);
                float crug_bottom = crug_top + crug.getHeight(null);
                boolean is_crug = x >= crug_left && x <= crug_right && y >= crug_top && y <= crug_bottom;
                if (is_crug){
                    crug_top = -100;
                    crug_left = (int)(Math.random() * (game_Field.getWidth() - crug.getWidth(null)));
                    crug_v = crug_v + 10;
                    score++;
                    catch_a_drop.setTitle("Score: " + score);
                }
            }
        });
        catch_a_drop.add(game_Field);
        catch_a_drop.setVisible(true);
    }

    private static void onRepaint (Graphics g){
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        crug_top = crug_top + crug_v * delta_time;
        g.drawImage(fon, 0,0,null);
        g.drawImage(crug, (int)crug_left,(int)crug_top,null);
        if (crug_top>catch_a_drop.getHeight()) g.drawImage(game, 250,50,null);
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();

        }
    }
}
