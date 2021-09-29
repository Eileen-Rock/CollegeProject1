/**
 * Created by Eileen on 9/26/2016.
 */
import com.sun.prism.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.StringBuilder;
public class draw {


    public static void main(String[] args){
        MyPanel edward = new MyPanel();
        edward.setArray();
        final Component myComponent = new JFrame("RNA Graphical Input Interface");
        myComponent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e)
            {
                myComponent.repaint();

            }
        });


        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("RNA Graphical Input Interface");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(edward);
        f.pack();
        f.setVisible(true);
        edward.genetor();

    }

}

class MyPanel extends JPanel{
    private int ex =0;
    private int why = 0;
    private Ellipse2D choiceA = new Ellipse2D.Double(1200, 600, 9, 9);
    private Ellipse2D choiceU = new Ellipse2D.Double(1200, 612, 9, 9);
    private Ellipse2D choiceC = new Ellipse2D.Double(1200, 624, 9, 9);
    private Ellipse2D choiceG = new Ellipse2D.Double(1200, 636, 9, 9);
    private Ellipse2D choiceP = new Ellipse2D.Double(1200, 648, 9, 9);
    private Ellipse2D relatLine = new Ellipse2D.Double(1200, 660, 12, 7);
    private Ellipse2D reGen = new Ellipse2D.Double(1200, 550, 12, 12);
    private Color curCol = Color.BLACK;
    private char colour= 'u';
    private boolean rePaint = false;
    private int hol = 0;
    private int hold = 0;
    private int cont =0;
    private StringBuilder startpt = new StringBuilder();
    private boolean relat, liner = false;
    private String nucleos = "Working with Nucleotide ";
    private String relations = "Working with relationship ties.";
    private FileMaker save = new FileMaker();


    private gridDot[][] grid = new gridDot[65][100];






    protected MyPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                if(choiceA.contains(me.getPoint())){
                    curCol = Color.BLUE;
                    colour = 'u';
                    drawTheString(getGraphics(),nucleos + " A");
                } else if(choiceC.contains(me.getPoint())){
                    curCol = Color.CYAN;
                    colour = 'y';
                    drawTheString(getGraphics(),nucleos + " C");
                }else if(choiceP.contains(me.getPoint())){
                    curCol = Color.WHITE;
                    colour = 'w';
                    drawTheString(getGraphics(),nucleos + " Unknown");
                }else if(choiceG.contains(me.getPoint())){
                    curCol = Color.MAGENTA;
                    colour = 'm';
                    drawTheString(getGraphics(),nucleos + " G");
                } else if(choiceU.contains(me.getPoint())){
                    curCol = Color.GREEN;
                    colour = 'g';
                    drawTheString(getGraphics(),nucleos + " U");
                } else if(relatLine.contains(me.getPoint())){
                    liner = true;
                } else if(reGen.contains(me.getPoint())){
                    genetor();
                    for (int i = 0; i < 65; i++) {
                        for (int j = 0; j < 100; j++) {
                            if(grid[i][j].isActive()){
                                save.writing(grid[i][j].toString());}
                        }
                    }
                } else {for (int i = 0; i < 65; i++) {
                    for (int j = 0; j < 100; j++) {
                        if (grid[i][j].dot.contains(me.getPoint())) {
                            if(!grid[i][j].isActive()){
                                reDraw(getGraphics(), i, j);
                                grid[i][j].setActive();
                                switch (colour){
                                    case 'm':
                                        grid[i][j].setNucl('G');
                                        break;
                                    case 'g':
                                        grid[i][j].setNucl('U');
                                        break;
                                    case 'y':
                                        grid[i][j].setNucl('C');
                                        break;
                                    case 'u':
                                        grid[i][j].setNucl('A');
                                        break;
                                    case 'w':
                                        grid[i][j].setNucl('*');
                                        break;

                                }
                            } else{
                                    Color holdCol = curCol;
                                    curCol = Color.BLACK;
                                    reDraw(getGraphics(),i,j);
                                    grid[i][j].setInActive();
                                    curCol = holdCol;
                                    grid[i][j].setNucl('e');
                                }
                            }

                        }
                    }
                }
                if(liner){
                for (int i = 0; i < 65; i++) {
                    for (int j = 0; j < 100; j++) {
                        if (grid[i][j].dot.contains(me.getPoint())) {
                            if(grid[i][j].isActive()){
                                if(!relat){
                                    hol = (int) grid[i][j].getNx();
                                    hold = (int) grid[i][j].getNy();
                                    relat = true;
                                } else{
                                    drawLine(getGraphics(), hol,hold,(int)grid[i][j].getNx(),(int)grid[i][j].getNy());
                                    grid[i][j].hasPartner();
                                    grid[hol][hold].hasPartner();
                                    relat = false;
                                    liner = false;
                                    }
                                }
                            }
                        }
                    }
                }

            }

                }
        );
    }
    protected void setArray(){
        for(int i = 0; i < 65; i++){
            for(int j = 0; j < 100; j++){
                gridDot iver = new gridDot(ex, why);
                grid[i][j] = iver;
                ex += 12;
            }
            ex = 0;
            why += 10;
        }

            }
    protected void genetor(){
        //get a string input from the user - JOptionPane?
        //parse it into individual characters
        //switch-case statement for six letters - A, U, C, G, *, and none of the above which means user done fucked up
        //record # of letters input this way
        //make an octagon with center in center grid with radius equal to some proportion of # of letters entered
        if(!rePaint) {
            String holder = JOptionPane.showInputDialog(null, "Input initial RNA sequence; unknown nucleotides should be represented by *");
            startpt.append(holder);
            startpt.trimToSize();
            cont = startpt.capacity();
            JOptionPane.showMessageDialog(null, cont);
            for (int i = 0; i < cont; i++) {
                if (startpt.charAt(i) == 'a' || startpt.charAt(i) == 'g' || startpt.charAt(i) == 'u' || startpt.charAt(i) == 'c' || startpt.charAt(i) == '*'
                        || startpt.charAt(i) == 'A' || startpt.charAt(i) == 'G' || startpt.charAt(i) == 'U' || startpt.charAt(i) == 'C') {
                    rePaint = true;
                } else if(cont == 0){
                    rePaint = true;
                    }else {
                    rePaint = false;
                    startpt.delete(0,cont);
                    break;
                }
            }
            if (rePaint) {
                JOptionPane.showMessageDialog(null, "This input contains acceptable nucleotides.");
            } else {
                JOptionPane.showMessageDialog(null, "This input is unacceptable; try again.");
                this.genetor();
            }
        }
        for(int i = 0; i < cont ; i++){
            int tem = i;
            switch(startpt.charAt(i)){
                case 'a':
                    grid[tem][tem].setActive();
                    grid[tem][tem].setNucl('A');
                    curCol = Color.BLUE;
                    reDraw(getGraphics(),tem,tem);
                    break;
                case 'c':
                    grid[tem][tem].setActive();
                    grid[tem][tem].setNucl('C');
                    curCol = Color.CYAN;
                    reDraw(getGraphics(),tem,tem);
                    break;
                case 'u':
                    grid[tem][tem].setActive();
                    grid[tem][tem].setNucl('U');
                    curCol = Color.GREEN;
                    reDraw(getGraphics(),tem,tem);
                    break;
                case 'g':
                    grid[tem][tem].setActive();
                    grid[tem][tem].setNucl('G');
                    curCol = Color.MAGENTA;
                    reDraw(getGraphics(),tem,tem);
                    break;
                case '*':
                    grid[tem][tem].setActive();
                    grid[tem][tem].setNucl('*');
                    curCol = Color.WHITE;
                    reDraw(getGraphics(),tem,tem);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "How the hell did you get here?");
                    break;
            }
        }

    }


    protected void reDraw(Graphics g, int a, int b){
        Graphics2D g2 = (Graphics2D) g;
        grid[a][b].setSize(10);
        g2.setColor(curCol);
        g2.fill(grid[a][b].dot);
        g2.setColor(Color.black);
        g2.draw(grid[a][b].dot);
        g2.drawString(startpt.toString(),1200 , 500);

    }
    protected void drawLine(Graphics g, int a, int b, int c, int d){
        g.drawLine(a,b,c,d);
    }
    protected void drawTheString(Graphics g, String s){
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(1200,400,50,15);
        g2.drawString(s, 1200, 400);
    }


    public Dimension getPreferredSize() {return new Dimension(1800,1000);
    }


    protected void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        for(int i = 0; i < 65; i++){
            for(int j = 0; j < 100; j++){
                g2.draw(grid[i][j].dot);
                g2.fill(grid[i][j].dot);
                if(grid[i][j].isActive()){
                    System.out.println("Got to switch");
                    switch(grid[i][j].getNucl()){
                        case 'A':
                            curCol = Color.BLUE;
                            reDraw(getGraphics(),i,j);
                            JOptionPane.showMessageDialog(null,"Got to A");
                            break;
                        case 'C':
                            curCol = Color.CYAN;
                            reDraw(getGraphics(),i,j);
                            //   JOptionPane.showMessageDialog(null,"Got to C");
                            break;
                        case 'U':
                            curCol = Color.GREEN;
                            reDraw(getGraphics(),i,j);
                            //   JOptionPane.showMessageDialog(null,"Got to U");
                            break;
                        case 'G':
                            curCol = Color.MAGENTA;
                            reDraw(getGraphics(),i,j);
                            //   JOptionPane.showMessageDialog(null,"Got to G");
                            break;
                        case '*':
                            curCol = Color.WHITE;
                            reDraw(getGraphics(),i,j);
                            //  JOptionPane.showMessageDialog(null,"Got to *");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "How the hell did you get here?");
                            break;
                    }
                }
            }
        }


        g2.setColor(Color.BLUE);
        g2.fill(choiceA);
        g2.setColor(Color.CYAN);
        g2.fill(choiceC);
        g2.setColor(Color.MAGENTA);
        g2.fill(choiceG);
        g2.setColor(Color.GREEN);
        g2.fill(choiceU);
        g2.setColor(Color.WHITE);
        g2.fill(choiceP);
        g2.setColor(Color.BLACK);
        g2.draw(choiceA);
        g2.drawString("Nucleotide A",1210 , 608);
        g2.draw(choiceP);
        g2.drawString("Nucleotide Unknown",1210 , 657);
        g2.draw(choiceC);
        g2.drawString("Nucleotide C",1210 , 633);
        g2.draw(choiceG);
        g2.drawString("Nucleotide G",1210 , 621);
        g2.draw(choiceU);
        g2.draw(reGen);
        g2.drawString("Regenerate", 1210, 560);
        g2.drawString("Nucleotide U",1210 , 645);
        g2.draw(relatLine);
        g2.drawString("Partner Line",1210 , 668);
        g2.drawString(startpt.toString(),1200 , 500);
        //add some line selections
        //make them heavier than just the standard 1px line; easier to click
        //click on click off?
    }



}

class FileMaker{
    //this'll hopefully be the class what dumps the input out in flat .txt files
    protected String s = "mac";
    //get a string name from user for the saving file
    protected void writing(String s){
        s=JOptionPane.showInputDialog(null,"Input the name of the file");

        try{
            File rnaInfo = new File("C:/Users/Eileen/Documents/" + s + ".txt");
            if(!rnaInfo.exists()){
                rnaInfo.createNewFile();
            } else{
            FileWriter ymca = new FileWriter(rnaInfo);
            BufferedWriter hemm = new BufferedWriter(ymca);
                ymca.write(s);
                }


            } catch (IOException e){
            JOptionPane.showMessageDialog(null,"error; something went wrong","error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

class gridDot {
    private double nx = 0; //the x-coord of the particular ellipse
    private double ny = 0; //y coord of the ellipse
    private int size = 6; //the size of the dot (currently v small to achieve desired resolution)
    private boolean suc1; //whether or not the nucleotide has a successor
    private boolean pred; //whether or not the nucleotide has a predecessor
    private boolean partn1; //whether or not the nucleotide is pointing to a partner
    private boolean partn2; //whether or not the nucleotide is pointing to a partner
    private boolean active; //whether or not that particular grid dot is a nucleotide of any kind
    protected Ellipse2D dot; //the grid dot itself
    private char nucl; //The nucleotide contained within the grid dot; default parameter to show there is no nucleotide is e



    protected gridDot(){
        nx = 0;
        ny = 0;
        dot = new Ellipse2D.Double(nx, ny, size, size);
        suc1 = false;
        pred = false;
        partn1 = false;
        partn2 = false;
        active = false;
        nucl = 'e';
    }
    protected gridDot(double a, double b){ //constructor; creates the ellipse; sets all variables for the ellipse to one of an inactive gridpoint
        nx = a;
        ny = b;
        dot = new Ellipse2D.Double(nx, ny, size, size);
        suc1 = false;
        pred = false;
        partn1 = false;
        partn2 = false;
        active = false;
        nucl = 'e';
    }

    protected gridDot(gridDot A){
        this.ny = A.ny;
        this.nx = A.nx;
        this.dot = A.dot;
        this.suc1 = A.pred;
        this.partn1 = A.partn2;
        this.active = A.active;
        this.nucl = A.nucl;
    }


    protected boolean isActive(){ //is that particular grid dot active? That is, is there a nucleotide on this dot?
        boolean mx = active;
        return mx;
    }

    protected void setActive(){
        active = true;//sets the grid dot to be active; Yes, there is a nucleotide here
    }
    protected void setInActive(){ //My mistake; there is not a nucleotide here
        active = false;
    }
    protected boolean hasSuccessor(){ //does the nucleotide have a successor?
        boolean mx = suc1;
        return mx;
    }

    protected boolean hasPredecessor(){ //does the nucleotide have a predeccessor?
        boolean mx = pred;
        return mx;
    }

    protected boolean hasPartner(){//Does the nucleotide point to a partner?
        boolean mx = partn1;
        return mx;
    }

    protected boolean hasPartner2(){ //Does the nucleotide's partner point to it?
        boolean mx = partn2;
        return mx;
    }
    protected void setSize(int a){ //Change the internal size of the ellipse
        size = a;

    }

    protected double getNx(){ //Where is the dot in the x direction
        double dX = nx;
        return dX;
    }

    protected double getNy(){ //Where is the dot in the Y direction
        double dY = ny;
        return dY;
    }

    protected void addSuccessor(){
        suc1 = true;//If the nucleotide has a successor, then by all means tell us so
    }
    protected void addPredecessor(){
        pred = true;//If the nucleotide has a predecessor, then by all means tell us so
    }
    protected void addPartner(){
        partn1 = true;//If the nucleotide has a Partner they point at, then by all means tell us so
    }
    protected void addPartner2(){
        partn2 = true;//If the nucleotide has a Partner that points to them, then by all means tell us so
    }
    protected void remPred(){//If, later, the partner is removed, make sure
        pred = false;
    }
    protected void remPart1(){
        partn1 = false;
    }
    protected void remPart2(){
        partn2 = false;
    }
    protected void remSucc(){
        suc1 = false;
    }
    protected char getNucl(){ char fett = nucl; return fett;}
    protected void setNucl(char aft){nucl = aft;}

    public String toString(){
        String s = "" + nx + "," + ny + "," + suc1 + "," + pred + "," + partn1 + "," +partn2 + "," +nucl;
        return s;
    }

}