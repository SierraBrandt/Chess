package chess;

/**
 *
 * @author Conner
 */
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;

public class Chess extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;
    static Button randomize = new Button("Randomize");
    
    public static void main(String[] args) {
        Chess frame = new Chess();
        
        
        //Create button to change board to brown
        //buttons may cause screen to go white in the build version or in the editor IF THIS HAPPENS just comment out this chunk of code starting here ending at frame.getContentPane( ).add(buttonPanel,BorderLayout.SOUTH);
        //white screen is cause by the window.getwidth2() return the wrong nnumber because the buttons mess with it for some reason?
        Button brown = new Button("Brown");
        brown.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BROWN);
            //JOptionPane.showMessageDialog(frame, "You've clicked OK button");
        });
        // Create button to change board to black
        Button black = new Button("Black");
        black.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BLACK);
            //JOptionPane.showMessageDialog(frame,"You've clicked Cancel button");
        });
        randomize.addActionListener((ActionEvent e) -> {
            Board.RandomizeBackRow();
            //JOptionPane.showMessageDialog(frame, "You've clicked OK button");
        });
        // Add buttons to a panel
        //black.disable(); .disable()makes the button not be pushable
        JPanel buttonPanel = new JPanel( );
        buttonPanel.add(brown);
        buttonPanel.add(black);
        buttonPanel.add(randomize);
        frame.getContentPane( ).add(buttonPanel,BorderLayout.NORTH);
        
        
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    /* ignore these methods for now i was thinking of maybe adding in a menu that can be opened and closed but for now just leave them
    public static void ShowButtons(){
        frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
    }
    public static void HidButtons(){
        frame.getContentPane().remove(buttonPanel);
    }
    */
    public Chess() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton() ) {
                    int x = e.getX() - Window.getX(0);
                    int y = e.getY() - Window.getY(0);
                    Board.PickPiece(x,y);
                }
                if (e.BUTTON3 == e.getButton()) {
                    int x = e.getX() - Window.getX(0);
                    int y = e.getY() - Window.getY(0);
                    reset();
                }
                if (e.BUTTON2 == e.getButton()) {
                    //reset();
                    //Board.RandomizeBackRow();
                }
                repaint();
            }
        });
            

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        
        g.setColor(Color.black);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.black);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }    
        Board.Draw(g);
        
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .1;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
    
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        Chess.randomize.enable();
        Player.Reset();
        Board.Reset();
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }

            reset();

        }

        
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}