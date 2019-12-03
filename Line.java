

public class Line {
	private String origStr;
	public Line nextLine;
	private boolean equalAlign, wrap, doubleSpaced, titleFont, column;

	private int indent, lineSize, align;

	public Line()
	{
		nextLine = null;
		column = false;
		origStr = "";
		align = 0;
		lineSize = 80;
		equalAlign = false;
		wrap = false;
		doubleSpaced = false;
		titleFont = false;
		indent = 0;
	}

	public String getLine(boolean main)
	{
		if(origStr.contentEquals("indent"))
			System.out.println(indent);
		String str = origStr;
		String front = "";
		String end = "";

		if(align == 0)
		{
			for(int i = 0; i < indent; i++)
			{
				front += " ";
			}
		}

		str = front + str + end + getSpace();

		if(nextLine != null)
		{
			//System.out.println(getStr() +" : " +nextLine.getStr());
			str += nextLine.getLine(false);
		}
		if(main)
		{
			//System.out.println("before: " +str);
			int chars = 0;
			

			//System.out.print(lineSize);
			for(int i = 0; i < str.length(); i++)
			{
				//System.out.print(chars);
				if(chars == lineSize)
				{
					String nl = "\n";
					if(doubleSpaced)
						nl += "\n";

					str = str.substring(0, i) + nl + str.substring(i);

					i += nl.length();
					chars = 0;
				}

				chars++;
			}
			//System.out.println("after: " +str);
		}

		return str;
	}

	public String getSpace()
	{
		String s = "";
		if(origStr.equals("\n"))
		{

		}
		else
		{
			if(wrap)
			{
				s += " ";
			}
			else
			{
				s += "\n";

				if(doubleSpaced && origStr.length() > 0)
				{
					s += "\n";
				}
			}
		}
		return s;
	}

	public void setStr(String str)
	{
		origStr = str;
	}

	public void append(Line l)
	{
		Line next = this;

		while(next.nextLine != null)
		{
			next = next.nextLine;
		}

		next.setNextLine(l);
	}

	public void setNextLine(Line l)
	{
		nextLine = l;
	}

	public void setAttr(int l, int a, boolean e, boolean w, boolean d, boolean c, int i, boolean t)
	{
		lineSize = l;
		align = a;
		equalAlign = e;
		wrap = w;
		doubleSpaced = d;
		column = c;
		indent = i;
		titleFont = t;
	}

	public String getStr()
	{
		return origStr;
	}
}
