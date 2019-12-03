
public class Error {
		
	public int code;
	public int lineNumber;
	
	//default constructor uses dummy, nonsensical values
	Error()
	{
		this.code = 0;
		this.lineNumber = 0;
	}
	
	//Standard constructor
	Error(int code, int lineNum)
	{
		this.code = code;
		this.lineNumber = lineNum;
	}
	
	//very standard setters
	void setCode(int code)
	{
		this.code = code;
	}
	void setLineNumber(int num)
	{
		this.lineNumber = num;
	}
	
	public String toString()
	{
		String returnString = "^^ Line: " +lineNumber +" ";
		
		if (this.code == 0)
		{returnString += "Default error created and never assigned a value";}
		else if(this.code == 1)
		{returnString += "Incorrect syntax. Wrong character after -";}
		else if(this.code == 2)
		{returnString +="Incorrect syntax. There is a number when you don't need one";}
		else if(this.code == 3) 
		{returnString +="Incorrect syntax. Invalid argument,value must be an integer between 0 and 2,147,483,647";}
		else if(this.code == 4) 
		{returnString +="Incorrect syntax. Only +/- after wrap instruct format";}
		else if(this.code == 5)
		{returnString +="Incorrect File type";}
		else if(this.code == 6)
		{returnString +="No more lines after command";}
		else if (this.code == 7)
		{returnString +="Invalid Number of columns, must be 1 or 2.";}
		else {returnString ="Invalid error code";}
		
		return returnString +"\n";
	}
}
