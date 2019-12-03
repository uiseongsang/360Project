

public class Line {
	private String origStr;
	public Line nextLine;
	private boolean wrap, doubleSpaced, titleFont, column;

	private int indent, lineSize, align;

	public Line()
	{
		nextLine = null;
		column = false;
		origStr = "";
		align = 0;
		lineSize = 80;
		wrap = false;
		doubleSpaced = false;
		titleFont = false;
		indent = 0;
	}

	public String getLine(boolean bool)
	{
		if(column)
		{
			titleFont = false;
		}

		if(titleFont)
		{
			align = 1;
			wrap = false;
		}

		if(wrap)
		{
			if(column)
			{
				lineSize = 80;
			}
			align = 0;
		}

		boolean main = bool;
		String str = origStr;
		String front = "";
		String end = "";

		//System.out.println(main +" , " +origStr);

		if(nextLine != null && !titleFont)
		{
			//System.out.println(getStr() +" : " +nextLine.getStr());
			str += nextLine.getLine(false);
		}

		if(main)
		{
			if(origStr.equals("\n"))
				return origStr;
			if(column)
			{
				lineSize = 80;

				String temp = "";

				for(int i = 0; i < str.length(); i++)
				{
					if(str.charAt(i) == '\n' && i > 0)
					{
						String space = "";

						for(int j = i; j%35 != 0; j++)
						{
							space += " ";
						}

						str = str.substring(0, i) + space + str.substring(i+1);

						i += space.length() + 1;
					}
				}

				//divide and redistribute
				String one = str.substring(0, str.length()/2);
				String two = str.substring(str.length()/2);

				int curr = 0;

				while(curr < one.length())
				{
					String f = "";
					String b = "";

					for(int i = 0; i < 35; i++)
					{
						if(curr + i < one.length())
						{
							f += one.charAt(curr + i);
						}

						if(curr + i < two.length())
						{
							b += two.charAt(curr + i);
						}
					}

					String nl = "\n";
					if(doubleSpaced)
						nl += "\n";

					temp += f +"          " +b +nl;

					curr += 35;
				}

				//if(doubleSpaced)
				//temp += "\n";
				str = temp;
			}
			else if(!titleFont)
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

						String p = str.substring(i);
						if(p.equals("\n"))
							p = " ";

						str = str.substring(0, i) + nl + p;

						i += nl.length();
						chars = 0;
					}

					chars++;
				}

			}
		}

		//left
		if(align == 0)
		{
			for(int i = 0; i < indent; i++)
			{
				front += " ";
			}
		}
		//center
		else if(align == 1)
		{
			int empty = lineSize-str.length();
			for(int i = 0; i < empty; i++)
			{
				if(i < empty/2)
				{
					end += " ";
				}
				else
				{
					front += " ";
				}
			}
		}
		//right
		else if(align == 2)
		{
			int start = 0;

			for(int i = str.length()-1; i > -1; i--)
			{
				if(str.charAt(i) == '\n')
				{
					start = i+1;
					i = -1;
				}
			}

			String space = "";
			for(int i = 0; i < lineSize-(str.substring(start).length()); i++)
			{
				space += " ";
			}

			str = str.substring(0, start) + space + str.substring(start);
		}
		else if(align == 3)
		{
			int start = 0;

			for(int i = str.length()-1; i > -1; i--)
			{
				if(str.charAt(i) == '\n')
				{
					start = i+1;
					i = -1;
				}
			}

			String s = str.substring(start);
			String[] words = s.split("\\s");
			if(words.length > 0)
			{
				int spaces = (lineSize - s.replaceAll("\\s", "").length())/words.length;
				String space = "";

				for(int i = 0; i < spaces; i++)
				{
					space += " ";
				}

				String m = "";

				m += space;

				for(String each : words)
				{
					m += each + space;
				}

				str = str.substring(0, start) + m;
			}
		}

		str = front + str + end;// + getSpace();

		//title
		if(titleFont)
		{
			String s = "";
			
			for(int i = 0; i < str.length(); i++)
			{
				if(str.charAt(i) == ' ')
				{
					s += " ";
				}
				else
				{
					s += "-";
				}
			}
			
			str += "\n" + s;
			
			if(nextLine != null)
			{
				//System.out.println(getStr() +" : " +nextLine.getStr());
				str += nextLine.getLine(false);
			}
		}

		if(nextLine == null)
		{	
			String nl = "\n";
			if(doubleSpaced)
				nl += "\n";
			str += nl;
		}

		return str;
	}

	public String getSpace()
	{
		String s = "";

		s += "\n";

		if(doubleSpaced && origStr.length() > 0)
		{
			s += "\n";
		}
		//}

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

	public void setAttr(int l, int a, boolean w, boolean d, boolean c, int i, boolean t)
	{
		lineSize = l;
		align = a;
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

	public boolean getWrap()
	{
		return wrap;
	}
}
