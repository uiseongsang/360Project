import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Runner extends JFrame {


	public Runner() {


	}

	public static void main(String[] args)
	{
		// net
		JFrame frame = new JFrame("Group 17 Text Processor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		JButton button = new JButton("Press");

		//editor
		JPanel pnMain;

		JPanel pnTop;
		JButton btOpen;
		JButton btFormat;
		JTextField tfStatus;
		JTabbedPane tbpBottom;

		JPanel pnError;
		JTextArea taError;

		JPanel pnOutput;
		JTextArea taOutput;

		pnMain = new JPanel();
		GridBagLayout gbMain = new GridBagLayout();
		GridBagConstraints gbcMain = new GridBagConstraints();
		pnMain.setLayout( gbMain );

		pnTop = new JPanel();
		GridBagLayout gbTop = new GridBagLayout();
		GridBagConstraints gbcTop = new GridBagConstraints();
		pnTop.setLayout( gbTop );

		btOpen = new JButton( "Open"  );
		btOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = null;
				String path = ".png";
				//System.out.print(path.substring(path.length()-4));
				//while()
				{
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());
					//new File(System.getProperty("user.home")));
					int result = fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = fileChooser.getSelectedFile();
						path = selectedFile.getAbsolutePath();
					}
					
					if(!path.substring(path.length()-4).equals(".txt"))
					{
						JFrame f = new JFrame("Error 4");
						JLabel l = new JLabel("Please select a .txt file");
						JButton b = new JButton("OK");
						JPanel p = new JPanel();

						p.add(l);
						p.add(b);
						f.add(p);
						f.setSize(300,100);
						f.setVisible(true);
					}
				}
			}

		});
		gbcTop.gridx = 1;
		gbcTop.gridy = 1;
		gbcTop.gridwidth = 5;
		gbcTop.gridheight = 2;
		gbcTop.fill = GridBagConstraints.BOTH;
		gbcTop.weightx = 1;
		gbcTop.weighty = 0;
		gbcTop.anchor = GridBagConstraints.NORTH;
		gbTop.setConstraints( btOpen, gbcTop );
		pnTop.add( btOpen );

		btFormat = new JButton( "Format"  );
		btFormat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame("Enter filename");
				JTextField t = new JTextField(16);
				JLabel l = new JLabel(".txt");
				JButton b = new JButton("Enter");
				JPanel p = new JPanel();

				p.add(t);
				p.add(l);
				p.add(b);
				f.add(p);
				f.setSize(300,100);
				f.setVisible(true);
			}

		});
		gbcTop.gridx = 7;
		gbcTop.gridy = 1;
		gbcTop.gridwidth = 5;
		gbcTop.gridheight = 2;
		gbcTop.fill = GridBagConstraints.BOTH;
		gbcTop.weightx = 1;
		gbcTop.weighty = 0;
		gbcTop.anchor = GridBagConstraints.NORTH;
		gbTop.setConstraints( btFormat, gbcTop );
		pnTop.add( btFormat );

		tfStatus = new JTextField( );
		tfStatus.setBackground( new Color( 240,240,240 ) );
		tfStatus.setEditable( false );
		tfStatus.setText( "All Done!" );
		gbcTop.gridx = 13;
		gbcTop.gridy = 1;
		gbcTop.gridwidth = 6;
		gbcTop.gridheight = 2;
		gbcTop.fill = GridBagConstraints.BOTH;
		gbcTop.weightx = 1;
		gbcTop.weighty = 0;
		gbcTop.anchor = GridBagConstraints.NORTH;
		gbTop.setConstraints( tfStatus, gbcTop );
		pnTop.add( tfStatus );
		gbcMain.gridx = 0;
		gbcMain.gridy = 0;
		gbcMain.gridwidth = 20;
		gbcMain.gridheight = 4;
		gbcMain.fill = GridBagConstraints.BOTH;
		gbcMain.weightx = 1;
		gbcMain.weighty = 0;
		gbcMain.anchor = GridBagConstraints.NORTH;
		gbMain.setConstraints( pnTop, gbcMain );
		pnMain.add( pnTop );

		tbpBottom = new JTabbedPane( );

		pnError = new JPanel();
		GridBagLayout gbError = new GridBagLayout();
		GridBagConstraints gbcError = new GridBagConstraints();
		pnError.setLayout( gbError );

		taError = new JTextArea(2,10);
		taError.setText("This panel will show the raw input file with error lines inserted in it.");
		gbcError.gridx = 0;
		gbcError.gridy = 0;
		gbcError.gridwidth = 20;
		gbcError.gridheight = 16;
		gbcError.fill = GridBagConstraints.BOTH;
		gbcError.weightx = 1;
		gbcError.weighty = 1;
		gbcError.anchor = GridBagConstraints.NORTH;
		gbError.setConstraints( taError, gbcError );
		pnError.add( taError );
		tbpBottom.addTab("filename.txt",pnError);//Error Log

		pnOutput = new JPanel();
		GridBagLayout gbOutput = new GridBagLayout();
		GridBagConstraints gbcOutput = new GridBagConstraints();
		pnOutput.setLayout( gbOutput );

		taOutput = new JTextArea(2,10);
		taOutput.setText("Output panel will show what the output text looks like.");
		gbcOutput.gridx = 0;
		gbcOutput.gridy = 0;
		gbcOutput.gridwidth = 20;
		gbcOutput.gridheight = 16;
		gbcOutput.fill = GridBagConstraints.BOTH;
		gbcOutput.weightx = 1;
		gbcOutput.weighty = 1;
		gbcOutput.anchor = GridBagConstraints.NORTH;
		gbOutput.setConstraints( taOutput, gbcOutput );
		pnOutput.add( taOutput );
		tbpBottom.addTab("output.txt",pnOutput);
		gbcMain.gridx = 0;
		gbcMain.gridy = 4;
		gbcMain.gridwidth = 20;
		gbcMain.gridheight = 16;
		gbcMain.fill = GridBagConstraints.BOTH;
		gbcMain.weightx = 1;
		gbcMain.weighty = 1;
		gbcMain.anchor = GridBagConstraints.NORTH;
		gbMain.setConstraints( tbpBottom, gbcMain );
		pnMain.add( tbpBottom );


		//add

		frame.getContentPane().add(pnMain); // Adds Button to content pane of frame
		frame.setVisible(true);

	}
}
