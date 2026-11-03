import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.awt.*;

class DrawingPanel extends JPanel{
	
    public void paintComponent(Graphics g, int x, int y) {
        g.fillOval(x, y, 6, 6);
    }
    
    public void clean(Graphics g) {
    	super.paint(g);
    }
}

public class GUI {
	JFrame mainFrame;
	JPanel slidersPanel;
	DrawingPanel drawingPanel;
	JSlider nInput_slider; 
	JTextField kInput_textField; 
	JButton kAcceptButton;
	JLabel nInfo;
	JLabel kInfo;
	
	JPanel inputPanel;
	
	int n = 15;
	float k = 1;
	float x = 0;
	
	private Map<Integer, Double> normalise(Map<Integer, Double> data) {
		double max = 0;
		
		for (double val : data.values()) 
			if (val > max) max = val;
				
		for (int key : data.keySet())
			data.put(key, data.get(key)/max*2*0.9);
		
		return data;	
	}
	
	private void drawDrawing() {
		drawingPanel.clean(drawingPanel.getGraphics());
		int spacing=2;
		int pointsCount = drawingPanel.getSize().width / spacing;
		double xMin = -k * Math.PI;
		double xMax = k * Math.PI;
		
		double distance = Math.abs(xMin - xMax);
		double step = distance/pointsCount;
		
		double x = xMin;
		int draw_x = 0;
		int y_offset = drawingPanel.getHeight()/2;
		Map<Integer, Double> pointsMap = new HashMap<>();
		
		for (int i=0;i<pointsCount;i++) {
			double y = 0;
			for (double j=1;j<=n;j++) 
				y += ((-2 * Math.pow(-1, j)/j) * Math.sin(j*x) );		
			//x, y
			pointsMap.put(draw_x*spacing, y);			
			draw_x += 1;
			x+=step;
		}
		pointsMap = normalise(pointsMap);
		
		for (Integer x_cord : pointsMap.keySet()) 
			drawingPanel.paintComponent(drawingPanel.getGraphics(), x_cord, (int) (drawingPanel.getHeight()*pointsMap.get(x_cord)/4)+y_offset);
	}
	
	private void start() {
		mainFrame = new JFrame("Zadanie 9 - Wykres sumy sinus");
		mainFrame.setSize(600, 400);
		mainFrame.setBackground(Color.white);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBackground(Color.lightGray);
		inputPanel.setSize(300, 300);
		inputPanel.setMinimumSize(new Dimension(300,300));
		inputPanel.setPreferredSize(new Dimension(300,300));
		mainFrame.add(inputPanel, BorderLayout.WEST);
		
		drawingPanel = new DrawingPanel();
		mainFrame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		        drawDrawing();
		    }
		});
		mainFrame.add(drawingPanel);
			
		int nMin = 1;
		int nMax = 25;
		int nBase = 15;
		nInput_slider = new JSlider(JSlider.VERTICAL, nMin, nMax, nBase);
		nInput_slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				n = nInput_slider.getValue();	
				nInfo.setText("Wartość n: " + n);
				drawDrawing();
			}			
		});
		
		nInfo = new JLabel("Wartosc n: " + n);
		nInfo.setSize(100, 50);
		nInfo.setBackground(Color.red);
		
		kInput_textField = new JTextField("1");
		kInput_textField.setColumns(8);
		kInput_textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					float new_k = Float.parseFloat(kInput_textField.getText());
					if (new_k <=10 && new_k >=1 )
					{
						k = new_k;
						kInfo.setText("Watość k: " + k);
						drawDrawing();
					}	
					else
						kInfo.setText("Watość k: błąd");
				}
				catch(Exception ex){
					kInfo.setText("Watość k: błąd");
				}			
			}
		});
		
		kInfo = new JLabel("Wartosc k: " + k);
		nInfo.setSize(100, 50);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		inputPanel.add(nInput_slider, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		inputPanel.add(nInfo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		inputPanel.add(kInput_textField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		inputPanel.add(kInfo, gbc);

		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.start();
	}	
}
