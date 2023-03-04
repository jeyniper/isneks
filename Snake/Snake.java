import javax.swing.JFrame;

public class Snake{

    public Snake() {
        
        JFrame frame = new JFrame();
        Board board = new Board();

        frame.add(board);
        frame.setResizable(false);
        frame.pack();
        frame.setTitle("Snake");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);  
    }

    
    public static void main(String[] args) {
        
        new Snake();

    }
}