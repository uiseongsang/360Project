import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Formatter extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static File selectedFile = null;
	public static File outputFile = null;

	JFrame frame;
	JPanel pnMain;

	JPanel pnTop;
	JButton btOpen;
	JButton btFormat;
	JTextField tfStatus;
	JTabbedPane tbpBottom;

	JPanel pnError;
	JTextArea taError;
	JScrollPane errScr;

	JPanel pnOutput;
	JTextArea taOutput;
	JScrollPane outScr;

	String statusText;
	String ipText;
	String opText;

	ArrayList<String> inputList;
	ArrayList<Line> outputList;

	boolean fileOpened;
	boolean fileFormatted;

	boolean TEST = false;

	enum Command
	{
		zero, lineLen, justL, justR, justC, eqSpc, wrap, sp1, sp2, title,
		para, blank, column;
	}

	public Formatter()
	{

		inputList = new ArrayList<String>();
		outputList = new ArrayList<Line>();

		fileOpened = false;
		fileFormatted = false;

		frame = new JFrame("Group 17 Text Processor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);

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
			//input file
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = ".png";

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

				int result = fileChooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();

				}

				if(!path.endsWith(".txt"))
				{
					selectedFile = null;
					JFrame f = new JFrame("Error 5");
					JLabel l = new JLabel("Please select a .txt file");
					JPanel p = new JPanel();

					p.add(l);
					f.add(p);
					f.setSize(300,100);
					f.setVisible(true);
				}
				else
				{
					try {
						Scanner scan = new Scanner(selectedFile);
						ipText = "";
						ipText += scan.useDelimiter("//Z").next();
						scan.close();
					}
					catch(FileNotFoundException ex)
					{
						ex.printStackTrace();
					}

					statusText = "Click Format";
					updateText();

					fileOpened = true;
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
			//output file
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileOpened)
				{
					JFrame f = new JFrame("Enter filename");
					JTextField t = new JTextField(16);
					JLabel l = new JLabel(".txt");
					JButton b = new JButton("Enter");
					JPanel p = new JPanel();

					b.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(!t.getText().isEmpty())
							{
								outputFile = new File(t.getText() + ".txt");
								f.setVisible(false);
								f.dispose();

								statusText = "Done!";
								updateText();
								run();
							}
						}
					});

					p.add(t);
					p.add(l);
					p.add(b);
					f.add(p);
					f.setSize(300,100);
					f.setVisible(true);
				}
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

		statusText = "Open Input File";
		tfStatus = new JTextField( );
		tfStatus.setBackground( new Color( 240,240,240 ) );
		tfStatus.setEditable( false );
		tfStatus.setText(statusText);
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

		Font lucida = new Font("Lucida Console", Font.PLAIN, 12);

		ipText = "Select input file!";
		taError = new JTextArea(2,10);
		taError.setFont(lucida);
		taError.setText(ipText);
		taError.setEditable(false);
		errScr = new JScrollPane( taError );
		gbcError.gridx = 0;
		gbcError.gridy = 0;
		gbcError.gridwidth = 20;
		gbcError.gridheight = 16;
		gbcError.fill = GridBagConstraints.BOTH;
		gbcError.weightx = 1;
		gbcError.weighty = 1;
		gbcError.anchor = GridBagConstraints.NORTH;
		gbError.setConstraints( errScr, gbcError );
		pnError.add( errScr );
		tbpBottom.addTab("Input/Errors",pnError);//Error Log

		pnOutput = new JPanel();
		GridBagLayout gbOutput = new GridBagLayout();
		GridBagConstraints gbcOutput = new GridBagConstraints();
		pnOutput.setLayout( gbOutput );

		opText = "Select input file and hit Format!";
		taOutput = new JTextArea(2,10);
		taOutput.setFont(lucida);
		taOutput.setText(opText);
		taOutput.setEditable(false);
		outScr = new JScrollPane(taOutput);
		gbcOutput.gridx = 0;
		gbcOutput.gridy = 0;
		gbcOutput.gridwidth = 20;
		gbcOutput.gridheight = 16;
		gbcOutput.fill = GridBagConstraints.BOTH;
		gbcOutput.weightx = 1;
		gbcOutput.weighty = 1;
		gbcOutput.anchor = GridBagConstraints.NORTH;
		gbOutput.setConstraints( outScr, gbcOutput );
		pnOutput.add( outScr );
		tbpBottom.addTab("Output",pnOutput);
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

		frame.getContentPane().add(pnMain); // Adds Button to content pane of frame
		frame.setVisible(true);

		if(TEST)
		{
			selectedFile = new File("inputText1.txt");
			outputFile = new File("a.txt");
			run();
		}
	}

	public void run()
	{
		inputList = new ArrayList<String>();
		outputList = new ArrayList<Line>();

		try
		{
			Scanner scan = new Scanner(selectedFile);
			ipText = "";
			ipText += scan.useDelimiter("//Z").next();
			updateText();
			scan.close();

			scan = new Scanner(selectedFile);
			while(scan.hasNext())
			{
				inputList.add(scan.nextLine());
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Command command;
		int lineCount = 1;
		Line currLine = new Line();
		boolean dash = false;
		ArrayList<Error> errorList = new ArrayList<Error>();

		int lineSize = 80;
		int align = 0;
		boolean wrap = false;
		boolean dSpace = false;
		boolean col = false;
		int indent = 0;
		boolean title = false;

		for(String text : inputList)
		{

			//check if command
			String noSpace = text.replaceAll("\\s", "");
			dash = false;

			if(noSpace.length() > 0)
			{
				if(noSpace.charAt(0) == '-')
				{
					if(noSpace.length()>1)
					{
						dash = true;	
					}
					else
					{
						errorList.add(new Error(1, lineCount));
					}
				}
				else
					dash = false;
			}

			if(dash)
			{
				text = noSpace.substring(1);
				command = Command.zero;
				String num = "";

				for(int i = 0; i < text.length(); i++)
				{
					char c = text.charAt(i);

					if(c == 'n')
					{
						command = Command.lineLen;
						
						if(i == text.length()-1)
						{
							errorList.add(new Error(1, lineCount));
						}
					}
					else if(command == Command.lineLen || command == Command.para || command == Command.blank)
					{
						if(Character.isDigit(c))
						{
							num += c;
						}
						else
						{
							errorList.add(new Error(8, lineCount));
						}
					}
					else if(c == 'l')
					{
						align = 0;
					}
					else if(c == 'c')
					{
						align = 1;
					}
					else if(c == 'r')
					{
						align = 2;
					}
					else if(c == 'e')
					{
						align = 3;
					}
					else if(c == 'w')
					{
						command = Command.wrap;
						
						if(i == text.length()-1)
						{
							errorList.add(new Error(1, lineCount));
						}
					}
					else if(command == Command.wrap)
					{
						if(c == '+')
						{
							wrap = true;
						}
						else if(c == '-')
						{
							wrap = false;
						}
						else 
						{
							errorList.add(new Error(4, lineCount));
						}
					}
					else if(c == 's')
					{
						dSpace = false;
					}
					else if(c == 'd')
					{
						dSpace = true;
					}
					else if(c == 't')
					{
						
						title = true;
					}
					else if(c == 'p')
					{
						command = Command.para;
						
						if(i == text.length()-1)
						{
							errorList.add(new Error(1, lineCount));
						}
					}
					else if(c == 'b')
					{
						command = Command.blank;
						
						if(i == text.length()-1)
						{
							errorList.add(new Error(1, lineCount));
						}
					}
					else if(c == 'a')
					{
						command = Command.column;
						
						if(i == text.length()-1)
						{
							errorList.add(new Error(1, lineCount));
						}
					}
					else if(command == Command.column)
					{
						if(c == '1')
						{
							if(currLine.getStr().length() == 0 && currLine.nextLine != null)
							{
								currLine.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
								outputList.add(currLine);
								currLine = new Line();
							}
							col = false;
						}
						else if(c == '2')
						{
							if((currLine.getStr().length() > 0 || currLine.nextLine != null))
							{
								if(lineCount == 17) System.out.println(dSpace);
								currLine.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
								outputList.add(currLine);
								currLine = new Line();
							}
							col = true;
						}
						else
						{
							errorList.add(new Error(7, lineCount));
						}
					}
					else
					{
						//System.out.println("char: " +c);
						// should be error 1new Error
						
						errorList.add(new Error(1, lineCount));
					}
				}

				int number = 0;
				if(num.length() > 0)
				{
					number = Integer.parseInt(num);

					if(number > 0)
					{
						if(command == Command.lineLen)
						{
							lineSize = number;
						}
						else if(command == Command.para)
						{
							indent = number;
						}
						else if(command == Command.blank)
						{
							for(int i = 0; i < number; i++)
							{
								Line l = new Line();
								l.setStr("\n");
								//currLine.append(l);
								outputList.add(l);
							}
						}
					}
					else
					{
						if(command == Command.lineLen || command == Command.para)
						{
							errorList.add(new Error(3, lineCount));
						}
					}
				}
			}
			else
			{
				if(col || (wrap && !title))
				{
					Line l = new Line();
					l.setStr(text);
					l.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
					currLine.append(l);
				}
				else
				{
					//for blank lines
					if(currLine.getStr().length() == 0 && currLine.nextLine != null)
					{
						currLine.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
						outputList.add(currLine);
						currLine = new Line();
					}

					currLine.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
					currLine.setStr(text);
					outputList.add(currLine);
					currLine = new Line();
				}

				indent = 0;
				title = false;
			}

			lineCount++;
		}

		if(currLine.getStr().length() == 0 && currLine.nextLine != null)
		{
			currLine.setAttr(lineSize, align, wrap, dSpace, col, indent, title);
			outputList.add(currLine);
		}

		opText = "";
		for(Line line : outputList)
		{
			opText += line.getLine(true);
		}

		ipText = "";
		for(int i = 0; i < inputList.size(); i++)
		{
			ipText += inputList.get(i) + "\n";
			boolean b = true;
			for(int j = 0; j < errorList.size() && b; j++)
			{
				if(errorList.get(j).lineNumber == i+1)
				{
					ipText += errorList.get(j).toString();
					b = false;
				}
			}
		}

		updateText();

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			out.write(opText);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateText() {
		tfStatus.setText(statusText);
		taError.setText(ipText);
		taOutput.setText(opText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


}