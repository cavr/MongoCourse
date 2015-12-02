/*
You can split BIG files into .666xx files for the open manager and vice versa with this tool.
 */

package opensplit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFileChooser;


/**
 *
 * @author Fejwin
 */
public class Main  extends Frame implements Runnable  {

    Thread th;
	protected boolean canFilterIndexColorModel = true;
	public String confile="settings.txt";
	public String str="";
        public String inputVerzStr=".";


    private BufferedWriter writer;
    boolean stopt,stopm;
    boolean opened=false;
    String resp1="";
    String resp2="";
    String fls[]= new String[10000];
        String rp[]= new String[10000];
        String rpby[]= new String[10000];
    Double flsg[]= new Double[10000];
    Double bigs[]= new Double[10000];
    String row[]= new String[10000];
    int interarea[], screenx, screeny,titlebar,flcnt,cunt,reg,ri,rpcnt;
    int target=1;
    int run=0;
    int offset=0;
    int proc=0;
    Point winpos,mouspos,mouspos1,mouspos2,mouspos3;
    Insets insets;
    	Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        PointerInfo info;
    Double difproz,diferro;
    Double fourgb = 4294967296.0;
    int onegb = 1073741824;
    boolean bt1p=false;
    boolean bt2p=false;
    boolean bt3p=false;
    boolean play=false;
        boolean mousedown=false;
    Sequence sequence;
Sequencer sequencer;
        File f;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
     		Main wnd = new Main();
     		wnd.startAnimation();
    }

public Main()
   {
      super("OpenSplit v1.2");

      setSize(getToolkit().getScreenSize());
      //WindowListener
      addWindowListener(
         new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event)
            {
               System.exit(0);
            }
         }
      );
      setBackground(Color.black);
	setExtendedState(Frame.MAXIMIZED_BOTH);

      screenx = getSize().width;
      screeny = getSize().height;

	setVisible(true);
        insets=getInsets();
        titlebar=insets.top;
      setLocation(0,0);
      setResizable(false);
    //addKeyListener(new MyKeyListener());
    addMouseListener(new MyMouseListener());
        try {
        // From file
        sequence = MidiSystem.getSequence(new File("fanfare.mid"));

        // Create a sequencer for the sequence
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(sequence);

    } catch (IOException e) {
    } catch (MidiUnavailableException e) {
    } catch (InvalidMidiDataException e) {
    }
   }


        private void startAnimation() {
            th = new Thread(this);
            th.start();
        }

        //the program loop. waiting for mouseclick
    public void run() {
        try {
            prepare(); //sturtup
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        while(true){ //loop
        if (stopm==false && mousedown==false) { //mouse pressed
                try {
                    if (play){
                        sequencer.stop();
                        sequencer.setMicrosecondPosition(0);
                        play=false;
                        target=1;
                    }
                    buttons();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                stopm=true;
        }else if (mousedown){
                repaint();
                mousedown=false;
            }
         try {
            Thread.sleep(180);  //sleep before next loop
         } catch (InterruptedException e) {}
        }
    }

//loads the settings.txt on sturtup (whether to delete used files)
       public void prepare() throws Exception {
                f = new File(confile);
		boolean exists = f.exists();
		if (exists) {
			Reader reader = new FileReader(confile);
			int data = reader.read();
			while(data != -1) {
			  str=str+""+((char) data);
			  data = reader.read();
			}
                        if (!str.equals("n")){str="y";}
		} else {
                    str="y";
		}
                repaint();
                stopm=true;
   }

//listens to mous actions
        class MyMouseListener
        extends MouseAdapter
        {
        @Override
           public void mousePressed(MouseEvent event)
           {
            if (target>1 && target<4){
            }else if (bt1p==false && bt2p==false && bt3p==false){
                    info = MouseInfo.getPointerInfo();
                    mouspos= info.getLocation();
            if (mouspos.x>20 && mouspos.x<20+screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                bt1p=true;
            }else if (mouspos.x>40+screenx/5 && mouspos.x<40+2*screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                bt2p=true;
            }else if (mouspos.x>60+2*screenx/5 && mouspos.x<60+4*screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                bt3p=true;
            }
                mousedown=true;
            }
           }
        @Override
           public void mouseReleased(MouseEvent event)
           {
            //get mouse position on mouse release
            //and activate a "button pressed" event through stopm=false;
            
            if (target>1 && target<4){
            }else{

                    bt1p=false;
                    bt2p=false;
                    bt3p=false;
                    mousedown=true;
                    
                if (stopm==true) {
                    info = MouseInfo.getPointerInfo();
                    mouspos= info.getLocation();
                    stopm=false;
                }
            }
           }
        }


// paint visuals according to program status
    @Override
   public void paint(Graphics g)
   {
        if (!bt1p){
            g.setColor(Color.gray);
        }else{
            g.setColor(Color.darkGray);
        }
        g.fillRect(20,titlebar*3/2,screenx/5,screeny/20);
        if (!bt1p){
            g.setColor(Color.darkGray);
        }else{
            g.setColor(Color.lightGray);
        }
        for (int coo=0;coo<20;coo++){
            if (coo<6){
                g.fillRect(20,titlebar*3/2+screeny/20-coo,screenx/5-2*coo,1);
            }
            g.fillRect(20+coo,titlebar*3/2+coo/2,1,screeny/20-coo/2);
        }
        if (!bt1p){
            g.setColor(Color.lightGray);
        }else{
            g.setColor(Color.gray);
        }
        g.fillRect(30,titlebar*3/2+5,screenx/5-20,screeny/20-10);

        if (!bt2p){
            g.setColor(Color.gray);
        }else{
            g.setColor(Color.darkGray);
        }
        g.fillRect(40+screenx/5,titlebar*3/2,screenx/5,screeny/20);
        if (!bt2p){
            g.setColor(Color.darkGray);
        }else{
            g.setColor(Color.lightGray);
        }
        for (int coo=0;coo<20;coo++){
            if (coo<6){
                g.fillRect(40+screenx/5,titlebar*3/2+screeny/20-coo,screenx/5-2*coo,1);
            }
            g.fillRect(40+screenx/5+coo,titlebar*3/2+coo/2,1,screeny/20-coo/2);
        }
        if (!bt2p){
            g.setColor(Color.lightGray);
        }else{
            g.setColor(Color.gray);
        }
        g.fillRect(50+screenx/5,titlebar*3/2+5,screenx/5-20,screeny/20-10);

        if (!bt3p){
            g.setColor(Color.gray);
        }else{
            g.setColor(Color.darkGray);
        }
        g.fillRect(60+2*screenx/5,titlebar*3/2,2*screenx/5,screeny/20);
        if (!bt3p){
            g.setColor(Color.darkGray);
        }else{
            g.setColor(Color.lightGray);
        }
        for (int coo=0;coo<20;coo++){
            if (coo<6){
                g.fillRect(60+2*screenx/5,titlebar*3/2+screeny/20-coo,2*screenx/5-2*coo,1);
            }
            g.fillRect(60+2*screenx/5+coo,titlebar*3/2+coo/2,1,screeny/20-coo/2);
        }
        if (!bt3p){
            g.setColor(Color.lightGray);
        }else{
            g.setColor(Color.gray);
        }
        g.fillRect(70+2*screenx/5,titlebar*3/2+5,2*screenx/5-20,screeny/20-10);

        g.setColor(Color.white);
        g.drawString("OpenSplit v1.2",75+4*screenx/5,15+titlebar*3/2);
        g.drawString("by Fejwin",75+4*screenx/5,35+titlebar*3/2);
        if (target>1 && target<4){g.setColor(Color.white);}else{g.setColor(Color.black);}
        g.drawString("(->) Split BIG files to .666xx",20+screenx/25,titlebar*3/2+screeny*9/300);
        g.drawString("(<-) Merge .666xx files to BIG",40+screenx*1/5+screenx*9/250,titlebar*3/2+screeny*9/300);
        if (str.equals("n")){
            g.drawString("[X ] Don't delete splitted BIG / merged .666xx files",60+2*screenx/5+screenx*9/100,titlebar*3/2+screeny*9/300);
        }else if (str.equals("y")){
            g.drawString("[   ] Don't delete splitted BIG / merged .666xx files",60+2*screenx/5+screenx*9/100,titlebar*3/2+screeny*9/300);
        }
        g.setColor(Color.white);
        if (inputVerzStr.equals(".")){
            g.drawString("Click on one option and select the game directory please. All subfolders are searched automatically.",20,20+titlebar*3/2+screeny/20);
        }else {
            g.drawString("Game directory: "+inputVerzStr,20,20+titlebar*3/2+screeny/20);
        }
        if (target==2){
            g.drawString("Read: "+resp1,20,40+titlebar*3/2+screeny/20);
        }else if (target>2){
            int hum=run+1;
            if (ri==1){
                g.drawString("Found "+(reg+1)+" BIG file(s) to split.",20,40+titlebar*3/2+screeny/20);
            }else if (ri==2){
                g.drawString("Found "+flcnt+" small .666xx files to merge into "+(reg+1)+" different BIG file(s).",20,40+titlebar*3/2+screeny/20);
            }
            if (flcnt>0){
                Double sice= (flsg[run]/(1024*1024*1024));
            g.drawString("Processing file  "+hum+" of "+flcnt+"  ("+sice+" GB) :      "+fls[run],20,60+titlebar*3/2+screeny/20);
            g.drawString(proc+"%",20,85+titlebar*3/2+screeny/20);
            g.setColor(Color.gray);
            g.fillRect(20,90+titlebar*3/2+screeny/20,screenx-40,titlebar);
            g.setColor(Color.green);
            g.fillRect(20,90+titlebar*3/2+screeny/20, (int) (proc * (screenx - 40) / 100),titlebar);
            }
            if (target==4){
                offset=0;
                if (reg!=-1){
                    for (int i=0;i<rpcnt;i++){
                        g.setColor(Color.white);
                        g.drawString("Renamed: "+rp[i]+"   --->   "+rpby[i],20,130+offset+titlebar*5/2+screeny/20);
                        offset=offset+20;
                    }
                }
                if (ri==1 && reg!=-1){
                    if (str.equals("n")){
                        g.setColor(Color.red);
                        g.drawString("Splitted BIG file(s) were not deleted! Please delete manually before transferring game to fat32 external HDD!",20,110+titlebar*5/2+screeny/20);
                    }else{
                        g.setColor(Color.white);
                        g.drawString("Splitted BIG file(s) have been deleted. You can now transfer the game to fat32 external HDD.",20,110+titlebar*5/2+screeny/20);
                    }
                }else if (ri==2 && reg!=-1){
                    if (str.equals("n")){
                        g.setColor(Color.red);
                        g.drawString("Merged .666xx file(s) were not deleted! Please delete manually to retrieve that memory!",20,110+titlebar*5/2+screeny/20);
                    }else{
                        g.setColor(Color.white);
                        g.drawString("Merged .666xx file(s) have been deleted. Your game is now in original state.",20,110+titlebar*5/2+screeny/20);
                    }
                }
                g.setColor(Color.white);
                g.drawString("Done with all jobs!",20,130+offset+titlebar*5/2+screeny/20);
                if (!play && reg+1>0){
                        sequencer.start();
                        play=true;
                }
            }
        }
   }

    //process button press
    public void buttons() throws Exception{
        if (target>1 && target<4){
        }else{
            //BIG to small
            if (mouspos.x>20 && mouspos.x<20+screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                this.oppen();
                if (opened==true){
                target=2;
                ri=1;
                alldat();
                target=3;
                repaint();
                split();
                target=4;
                if (str.equals("y")){
                    delate();
                }
                rename();
                repaint();
                opened=false;
                }
            }
            //small to BIG
            else if (mouspos.x>40+screenx/5 && mouspos.x<40+2*screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                this.oppen();
                if (opened==true){
                target=2;
                ri=2;
                alldat();
                target=3;
                repaint();
                merge();
                target=4;
                if (str.equals("y")){
                    delate();
                }
                rename();
                repaint();
                opened=false;
                }
            }
            //toggle delete / dont delete files option
            else if (mouspos.x>60+2*screenx/5 && mouspos.x<60+4*screenx/5 && mouspos.y>titlebar*3/2 && mouspos.y<titlebar*3/2+screeny/20){
                if (str.equals("y")){
                            str="n";
                        } else if (str.equals("n")){
                            str="y";
                        }
                        f = new File(confile);
                        writer = new BufferedWriter(new FileWriter(f));
                        writer.write(str);
                        writer.close();
                        f.createNewFile();
                        target=1;
                        repaint();
            }
            
        }
    }
        private void delate() throws IOException {
            for (int i=0;i<flcnt;i++){
                File fi = new File(fls[i]);
                fi.delete();
            }
        }
    private void split() throws IOException {
        for (int i=0;i<flcnt;i++){
            run=i;
            proc=0;
                BufferedInputStream in = null;
                BufferedOutputStream out = null;
        try {
            DecimalFormat df = new DecimalFormat("00");
            int part=0;
            in = new BufferedInputStream(new FileInputStream(fls[run]));
            out = new BufferedOutputStream(new FileOutputStream(fls[run]+".666"+df.format(part)));
            int c;
            int cnt=0;
            Double cnt2=0.0;
            while ((c = in.read()) != -1) {
                if (cnt==onegb){
                    out.flush();
                    out.close();
                    part++;
                    out = new BufferedOutputStream(new FileOutputStream(fls[run]+".666"+df.format(part)));
                    cnt=0;
                }
                out.write(c);
                cnt++;
                cnt2=cnt2+1;
                if ((int)(100*cnt2/flsg[run])-proc>=1){
                    //System.out.println(cnt2/flsg[run]);
                    proc=(int) (100 * cnt2 / flsg[run]);
                    repaint();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        }
    }
private void merge() throws IOException {
        int regn=0;
        proc=0;
        Double cnt2=0.0;
                BufferedInputStream in = null;
                BufferedOutputStream out = null;
        try {

        for (int i=0;i<flcnt;i++){
            run=i;
            in = new BufferedInputStream(new FileInputStream(fls[run]));
            if (run>0){
            if (!fls[run].substring(0, fls[run].length() - 6).equals(fls[run - 1].substring(0, fls[run - 1].length() - 6))){
                out.flush();
                out.close();
                out = new BufferedOutputStream(new FileOutputStream(fls[run].substring(0,fls[run].length()-6)));
                regn++;
                proc=0;
                cnt2=0.0;
            }}else{
                out = new BufferedOutputStream(new FileOutputStream(fls[run].substring(0,fls[run].length()-6)));
                
            }
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
                cnt2=cnt2+1;
                if ((int)(100*cnt2/bigs[regn])-proc>=1){
                    //System.out.println(cnt2/flsg[run]);
                    proc=(int) (100 * cnt2 / bigs[regn]);
                    repaint();
                }
            }
            in.close();
        }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        
    }
    //
private void rename(){
    String stre="";
    rpcnt=0;
        for (int u=0;u<flcnt;u++){
        String stru=fls[u];
        String[] stra=stru.split("/PS3_GAME/");
        if (!stra[0].equals(stre)){
            stre=stra[0];
        String[] stri=stra[0].split("/");
        int knt=0;
        boolean go=true;
        while (go){
            try {
                stri[knt]=stri[knt];
                knt++;
            } catch (ArrayIndexOutOfBoundsException e) {
                go=false;
                knt--;
            }
        }
        boolean renamedd=false;
        if (ri==1 && stri[knt].startsWith("_")==false){
            stri[knt]="_"+stri[knt];
            renamedd=true;
        }else if(ri==2 && stri[knt].startsWith("_")==true){
            stri[knt]=stri[knt].substring(1,stri[knt].length());
            renamedd=true;
        }
        stru=stri[0];
        for (int i=1;i<=knt;i++){stru=stru+"/"+stri[i];}
        if (renamedd){
            rp[rpcnt]=stra[0];
            rpby[rpcnt]=stru;
            rpcnt++;
            File file = new File(stra[0]);
            File file2 = new File(stru);
            file.renameTo(file2);
        }
        }
        }
    }
    //locate BIG (ri=1) or .666xx (ri=2) files inside a GAME folder
    private void alldat(){
        row[0]=inputVerzStr;
        cunt=1;
        for (int u=0;u<10;u++){
            bigs[u]=0.0;
        }
        reg=-1;
        flcnt=0;
        resp1="hm";
        for (int a=0; a<cunt; a++){
        File myDir = new File(row[a]);
        if( myDir.exists() && myDir.isDirectory()){
            File[] files = myDir.listFiles();
            for(int i=0; i < Integer.valueOf(files.length); i++){
                File file = files[i];
                if (file.isDirectory()){
                    row[cunt]=file.getPath().replace("\\","/");
                    cunt++;
                }else{
                    String sur=file.getPath().replace("\\","/");
                    if (ri==1){
                        if (file.length()>fourgb){
                            fls[flcnt]=sur;
                            flsg[flcnt]=Double.valueOf(file.length());
                            flcnt++;
                            reg++;
                        }
                    }else if (ri==2){
                        String[] tokens = sur.split(".666");
                        if (!tokens[0].equals(sur) && tokens[1].length()==2){
                            if (!sur.substring(0,sur.length()-2).equals(resp1.substring(0,resp1.length()-2))){
                                reg++;
                            }
                            fls[flcnt]=sur;
                            flsg[flcnt]=Double.valueOf(file.length());
                            bigs[reg]=bigs[reg]+flsg[flcnt];
                            flcnt++;
                        }
                    }
                    resp1=sur;
                    repaint();
                }
            }
        }
        }
    }

    //Open a "select game path" dialog
    private void oppen() {

        if (!inputVerzStr.equals(".")){
            boolean go=true;
            while (go){
                boolean exists = (new File(inputVerzStr)).exists();
                if (exists==false){
                    String[] parts=inputVerzStr.split("/");
                    int br=0;
                    boolean goo=true;
                    while (goo){
                        try{
                            parts[br]=parts[br];
                            br++;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            goo=false;
                            br--;
                        }
                    }
                    inputVerzStr="";
                    for (int i=0;i<br;i++){
                        inputVerzStr=inputVerzStr+"/"+parts[i];
                    }
                }else{
                    go=false;
                }
            }
        }

        JFileChooser chooser = new JFileChooser("Choose");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileSelectionMode(1);
        chooser.setDialogTitle("Choose the game directory please.");
        File file = new File(inputVerzStr);
        chooser.setCurrentDirectory(file);
        chooser.setVisible(true);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputVerzFile = chooser.getSelectedFile();
            inputVerzStr = inputVerzFile.getPath().replace("\\","/");
            opened=true;
        }
        chooser.setVisible(false);
    }
}